package io.github.mosadie.ExponentialPower.TileEntitys;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import szewek.mcflux.api.MCFluxAPI;
import szewek.fl.*;
import szewek.fl.energy.*;

public class EnderStorageTE extends TileEntity implements IEnergy, ITickable {
	
	private long energy = 0;
	
	public EnderStorageTE() {
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("energy", energy);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energy = nbt.getLong("energy");
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing f) {
		return cap == FL.ENERGY_CAP;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing f) {
		return cap == FL.ENERGY_CAP ? (T) this : null;
	}
	
	@Override
	public boolean canInputEnergy() {
		return true;
	}

	@Override
	public boolean canOutputEnergy() {
		return true;
	}

	@Override
	public long inputEnergy(long amount, boolean sim) {
		if (energy == Long.MAX_VALUE) {
			return 0;
		}
		if ((energy + amount) < energy ) {
			long tmpGained = Long.MAX_VALUE - energy;
			energy = Long.MAX_VALUE;
			this.markDirty();
			return tmpGained;
		} else {
			energy += amount;
			this.markDirty();
			return amount;
		}
	}

	@Override
	public long outputEnergy(long amount, boolean sim) {
		if (energy <= amount) {
			long temp = energy;
			if (!sim) energy = 0;
			return temp;
		}
		else {
			long temp = energy - amount;
			if (!sim) energy -= amount;
			return temp;
		}
	}

	@Override
	public long getEnergy() {
		// TODO Auto-generated method stub
		return energy;
	}

	@Override
	public long getEnergyCapacity() {
		// TODO Auto-generated method stub
		return Long.MAX_VALUE;
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
				BlockPos targetBlock = getPos().add(dir.getDirectionVec());

				TileEntity tile = world.getTileEntity(targetBlock);
				if (tile != null) {
					IEnergy receiver = MCFluxAPI.getEnergySafely(tile, dir.getOpposite());
					if (receiver != null) {
						if (receiver.canInputEnergy()) {
							long tosend = outputEnergy(this.energy, true);
							long used = receiver.inputEnergy(tosend, false);
							outputEnergy(used, false);
							if (used > 0) {
								this.markDirty();
							}
						}
					}
				}
			}
		}
	}
}
