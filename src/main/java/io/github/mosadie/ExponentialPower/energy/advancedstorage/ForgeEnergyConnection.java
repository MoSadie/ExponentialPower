package io.github.mosadie.ExponentialPower.energy.advancedstorage;

import io.github.mosadie.ExponentialPower.ConfigHandler;
import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderStorageTE;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.*;

public class ForgeEnergyConnection implements IEnergyStorage{
	
	private AdvancedEnderStorageTE owner;
	private final boolean canExtract;
	private final boolean canReceive;
	private final EnumFacing direction;
	
	public ForgeEnergyConnection(AdvancedEnderStorageTE owner, boolean canExtract, boolean canReceive, EnumFacing dir) {
		this.owner = owner;
		this.canExtract = canExtract;
		this.canReceive = canReceive;
		direction = dir;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (owner.energy >= owner.maxEnergy) {
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
		return (ConfigHandler.ADVANCED_STORAGE_MAXENERGY > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)ConfigHandler.ADVANCED_STORAGE_MAXENERGY);
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
