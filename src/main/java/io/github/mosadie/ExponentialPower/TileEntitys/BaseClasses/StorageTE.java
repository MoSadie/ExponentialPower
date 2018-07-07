package io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses;

import java.util.EnumMap;
import javax.annotation.Nullable;
import io.github.mosadie.ExponentialPower.ConfigHandler;
import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.energy.storage.ForgeEnergyConnection;
import io.github.mosadie.ExponentialPower.energy.storage.TeslaEnergyConnection;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Loader;

public class StorageTE extends TileEntity implements ITickable {

	public static enum StorageTier {
		REGULAR,
		ADVANCED,
	}
	public final StorageTier tier;

	public double energy = 0;
	public EnumMap<EnumFacing,Boolean> freezeExpend;

	private EnumMap<EnumFacing,ForgeEnergyConnection> fec;
	private EnumMap<EnumFacing,TeslaEnergyConnection> tec;

	public StorageTE(StorageTier storageTier) {
		tier = storageTier;
		freezeExpend = new EnumMap<EnumFacing,Boolean>(EnumFacing.class);
		fec = new EnumMap<EnumFacing,ForgeEnergyConnection>(EnumFacing.class);
		tec = new EnumMap<EnumFacing,TeslaEnergyConnection>(EnumFacing.class);
		for(EnumFacing dir : EnumFacing.values()) {
			fec.put(dir, new ForgeEnergyConnection(this, true, true, dir));
			if (Loader.isModLoaded("tesla")) {
				tec.put(dir, new TeslaEnergyConnection(this, dir));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		//super.writeToNBT recreated and modified here.
		ResourceLocation resourcelocation = new ResourceLocation(ExponentialPower.MODID + (tier == StorageTier.REGULAR ? ":enderstorage_tile_entity" : ":advancedenderstorage_tile_entity"));
		nbt.setString("id", resourcelocation.toString());
		nbt.setInteger("x", this.pos.getX());
		nbt.setInteger("y", this.pos.getY());
		nbt.setInteger("z", this.pos.getZ());
		nbt.setTag("ForgeData", this.getTileData());

		nbt.setDouble("energy", energy);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energy = nbt.getDouble("energy");
	}

	@Override
	public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing f) {
		if (!tec.isEmpty())
			return cap == CapabilityEnergy.ENERGY || cap == TeslaCapabilities.CAPABILITY_PRODUCER || cap == TeslaCapabilities.CAPABILITY_CONSUMER || cap == TeslaCapabilities.CAPABILITY_HOLDER;
		else
			return cap == CapabilityEnergy.ENERGY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing dir) {
		if (cap == CapabilityEnergy.ENERGY) return (T) fec.get((dir != null) ? dir : EnumFacing.UP);
		if (tec.containsKey(dir)) if (cap == TeslaCapabilities.CAPABILITY_PRODUCER || cap == TeslaCapabilities.CAPABILITY_CONSUMER || cap == TeslaCapabilities.CAPABILITY_HOLDER) return (T) tec.get((dir != null) ? dir : EnumFacing.UP);
		return null;
	}

	@Override
	public void update() {
		if (energy > 0) handleSendingEnergy();
	}

	private void handleSendingEnergy() {
		if (!world.isRemote) {
			if (energy <= 0) {
				return;
			} 
			for (EnumFacing dir : EnumFacing.values()) {
				if (!freezeExpend.containsKey(dir))
					freezeExpend.put(dir, false);

				if (freezeExpend.get(dir)) {
					freezeExpend.put(dir, false);
					continue;
				}
				BlockPos targetBlock = getPos().add(dir.getDirectionVec());
				TileEntity tile = world.getTileEntity(targetBlock);
				if (tile != null) {
					if (tile instanceof StorageTE) {
						StorageTE storage = (StorageTE) tile;
						double difference = storage.acceptEnergy(energy);
						energy -= difference;
						if (difference > 0) {
							freezeExpend.put(dir, true);
						}
					}
					else if (tec.get(dir) != null) {
						if (tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite())) {
							ITeslaConsumer other = tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite());
							long change = other.givePower((energy > Long.MAX_VALUE ? Long.MAX_VALUE : (long)energy), false);
							if (change > 0) {
								energy -= change;
								freezeExpend.put(dir, true);
							}
						}
						else {
							if (tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())) {
								IEnergyStorage other = tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite());
								if (other.canReceive()) {
									int change = other.receiveEnergy((int) (energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : energy), false);
									if (change > 0) {
										energy -= change;
										freezeExpend.put(dir, true);
									}
								}
							}
						}
					}
					else {
						if (tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())) {
							IEnergyStorage other = tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite());
							if (other.canReceive()) {
								int change = other.receiveEnergy((int) (energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : energy), false);
								if (change > 0) {
									energy -= change;
									freezeExpend.put(dir, true);
								}
							}
						}
					}
				}
			}
		}
	}

	public double getMaxEnergy() {
		switch (tier) {
		case REGULAR:
			return ConfigHandler.ender_storage.Regular.MAXENERGY;

		case ADVANCED:
			return ConfigHandler.ender_storage.Advanced.MAXENERGY;

		default:
			return Double.MAX_VALUE;
		}
	}

	public double acceptEnergy(double energyOffered) {
		if (energy >= getMaxEnergy() || energyOffered <= 0) {
			return 0;
		}

		if (energy + energyOffered > getMaxEnergy()) {
			double amountAccepted = getMaxEnergy() - energy;
			energy = getMaxEnergy();
			return amountAccepted;
		}

		if (energy + energyOffered < 0) {
			double amountAccepted = Double.MAX_VALUE - energy;
			energy = Double.MAX_VALUE;
			return amountAccepted;
		}

		energy += energyOffered;
		return energyOffered;
	}
}
