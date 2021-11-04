package io.github.mosadie.exponentialpower.energy.storage;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgeEnergyConnection implements IEnergyStorage{
	
	private final StorageTE owner;
	private final boolean canExtract;
	private final boolean canReceive;
	private final Direction direction;
	
	public ForgeEnergyConnection(StorageTE owner, boolean canExtract, boolean canReceive, Direction dir) {
		this.owner = owner;
		this.canExtract = canExtract;
		this.canReceive = canReceive;
		direction = dir;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (owner.energy >= owner.getMaxEnergy()) {
			return 0;
		}
		if ((owner.energy + maxReceive) < owner.energy ) {
			int tmpGained = (int) (Double.MAX_VALUE - owner.energy);
			owner.energy = Double.MAX_VALUE;
			owner.markDirty();
			owner.freezeExpend.put(direction, true);
			return tmpGained;
		} else {
			owner.energy += maxReceive;
			owner.freezeExpend.put(direction, true);
			owner.markDirty();
			return maxReceive;
		}
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (owner.energy <= 0) return 0;
		if (owner.energy <= maxExtract) {
			int temp = (int) owner.energy;
			if (!simulate) owner.energy = 0;
			owner.markDirty();
			return temp;
		}
		else {
			if (!simulate) owner.energy -= maxExtract;
			owner.markDirty();
			return maxExtract;
		}

	}

	@Override
	public int getEnergyStored() {
		return (int) (owner.energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : owner.energy);
	}

	@Override
	public int getMaxEnergyStored() {
		return (owner.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)owner.getMaxEnergy());
	}

	@Override
	public boolean canExtract() {
		return canExtract;
	}

	@Override
	public boolean canReceive() {
		return canReceive;
	}
}
