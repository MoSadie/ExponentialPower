package io.github.mosadie.exponentialpower.tiles.BaseClasses;

import io.github.mosadie.exponentialpower.Config;
import io.github.mosadie.exponentialpower.energy.storage.ForgeEnergyConnection;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.EnumMap;

public class StorageTE extends TileEntity implements ITickableTileEntity {

	public enum StorageTier {
		REGULAR,
		ADVANCED,
	}
	public final StorageTier tier;

	public double energy = 0;
	public EnumMap<Direction,Boolean> freezeExpend;

	private final EnumMap<Direction, ForgeEnergyConnection> fec;
	private final EnumMap<Direction, LazyOptional<ForgeEnergyConnection>> fecOptional;

	public StorageTE(StorageTier tier) {
		super(tier == StorageTier.ADVANCED ? Registration.ADV_ENDER_STORAGE_TE.get() : Registration.ENDER_STORAGE_TE.get());
		this.tier = tier;
		freezeExpend = new EnumMap<>(Direction.class);
		fec = new EnumMap<>(Direction.class);
		for(Direction dir : Direction.values()) {
			fec.put(dir, new ForgeEnergyConnection(this, true, true, dir));
		}

		fecOptional = new EnumMap<>(Direction.class);
		for(Direction dir : Direction.values()) {
			fecOptional.put(dir, LazyOptional.of(() -> fec.get(dir)));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);

		nbt.putDouble("energy", energy);
		return nbt;
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		energy = nbt.getDouble("energy");
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction dir) {
		if (cap == CapabilityEnergy.ENERGY) return fecOptional.get((dir != null) ? dir : Direction.UP).cast();
		return super.getCapability(cap, dir);
	}

	@Override
	public void tick() {
		if (energy > 0) handleSendingEnergy();
	}

	private void handleSendingEnergy() {
		if (!world.isRemote) {
			if (energy <= 0) {
				return;
			} 
			for (Direction dir : Direction.values()) {
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
					else {
						tile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite()).ifPresent((cap) -> {
							if (cap.canReceive()) {
								int change = cap.receiveEnergy((int) (energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : energy), false);
								if (change > 0) {
									energy -= change;
									freezeExpend.put(dir, true);
								}
							}
						});
					}
				}
			}
		}
	}

	public double getMaxEnergy() {
		switch (tier) {
		case REGULAR:
			return Config.ENDER_STORAGE_MAX_ENERGY.get();

		case ADVANCED:
			return Config.ADV_ENDER_STORAGE_MAX_ENERGY.get();

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
