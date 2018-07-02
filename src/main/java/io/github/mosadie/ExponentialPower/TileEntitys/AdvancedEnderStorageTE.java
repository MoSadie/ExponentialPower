package io.github.mosadie.ExponentialPower.TileEntitys;

import java.util.EnumMap;
import javax.annotation.Nullable;
import io.github.mosadie.ExponentialPower.ConfigHandler;
import io.github.mosadie.ExponentialPower.energy.advancedstorage.ForgeEnergyConnection;
import io.github.mosadie.ExponentialPower.energy.advancedstorage.TeslaEnergyConnection;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Loader;

public class AdvancedEnderStorageTE extends TileEntity implements ITickable {

	public double energy = 0;
	public double maxEnergy;
	public EnumMap<EnumFacing,Boolean> freezeExpend;

	private EnumMap<EnumFacing,ForgeEnergyConnection> fec;
	private EnumMap<EnumFacing,TeslaEnergyConnection> tec;

	public AdvancedEnderStorageTE() {
		maxEnergy = ConfigHandler.ADVANCED_STORAGE_MAXENERGY;
		if (maxEnergy < 1) maxEnergy = 1;
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
		super.writeToNBT(nbt);
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
					if (tile instanceof EnderStorageTE) {
						EnderStorageTE storage = (EnderStorageTE) tile;
						if (storage.energy >= storage.maxEnergy) 
							continue;
						else if (storage.energy + 1 < energy) {
							Long transferAmount = ((long)energy-storage.energy)/2;
							storage.energy += transferAmount;
							energy -= transferAmount;
							freezeExpend.put(dir, true);
						}
					}
					else if (tile instanceof AdvancedEnderStorageTE) {
						AdvancedEnderStorageTE storage = (AdvancedEnderStorageTE) tile;
						if (storage.energy >= storage.maxEnergy) 
							continue;
						else if (storage.energy + 1 < energy) {
							double transferAmount = (energy-storage.energy)/2;
							storage.energy += transferAmount;
							energy -= transferAmount;
							freezeExpend.put(dir, true);
						}
					}
					else if (Loader.isModLoaded("tesla")) {
						if (tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite())) {
							ITeslaConsumer other = tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, dir.getOpposite());
							long change = other.givePower((energy > Long.MAX_VALUE ? Long.MAX_VALUE : (long)energy), false);
							if (change > 0) {
								energy -= change;
								freezeExpend.put(dir, true);
							}
						}
						else if (tile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())) {
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
}
