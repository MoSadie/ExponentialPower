package io.github.mosadie.ExponentialPower.TileEntitys;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import szewek.mcflux.api.ex.EX;
import szewek.mcflux.api.ex.IEnergy;

public class EnderStorageTE extends TileEntity implements IEnergy, ITickable {
	
	private long energy = 0;
	
	public EnderStorageTE() {
		
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing f) {
		return cap == EX.CAP_ENERGY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing f) {
		return cap == EX.CAP_ENERGY ? (T) this : null;
	}
	
	@Override
	public boolean canInputEnergy() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canOutputEnergy() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public long inputEnergy(long amount, boolean sim) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long outputEnergy(long amount, boolean sim) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		
	}

}
