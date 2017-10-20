package io.github.mosadie.ExponentialPower.energy.storage;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.*;

public class ForgeEnergyConnection implements IEnergyStorage{
	
	private EnderStorageTE owner;
	private final boolean canExtract;
	private final boolean canReceive;
	private final EnumFacing direction;
	
	public ForgeEnergyConnection(EnderStorageTE owner, boolean canExtract, boolean canReceive, EnumFacing dir) {
		this.owner = owner;
		this.canExtract = canExtract;
		this.canReceive = canReceive;
		direction = dir;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (owner.energy == Long.MAX_VALUE) {
			return 0;
		}
		if ((owner.energy + maxReceive) < owner.energy ) {
			int tmpGained = (int) (Long.MAX_VALUE - owner.energy);
			owner.energy = Long.MAX_VALUE;
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
		return Integer.MAX_VALUE;
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
