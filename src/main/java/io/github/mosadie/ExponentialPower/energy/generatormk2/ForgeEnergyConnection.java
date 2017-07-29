package io.github.mosadie.ExponentialPower.energy.generatormk2;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorMk2TE;
import net.minecraftforge.energy.*;

public class ForgeEnergyConnection implements IEnergyStorage{
	
	private EnderGeneratorMk2TE owner;
	private final boolean canExtract;
	private final boolean canReceive;
	
	public ForgeEnergyConnection(EnderGeneratorMk2TE owner, boolean canExtract, boolean canReceive) {
		this.owner = owner;
		this.canExtract = canExtract;
		this.canReceive = canReceive;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
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
		return (int) (owner.currentOutput > Integer.MAX_VALUE ? Integer.MAX_VALUE : owner.currentOutput);
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
