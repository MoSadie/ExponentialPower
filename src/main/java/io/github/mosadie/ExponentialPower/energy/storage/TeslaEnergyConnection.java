package io.github.mosadie.ExponentialPower.energy.storage;

import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.StorageTE;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.util.EnumFacing;
import net.darkhax.tesla.api.ITeslaConsumer;

public class TeslaEnergyConnection implements ITeslaHolder, ITeslaProducer, ITeslaConsumer{

	StorageTE owner;
	EnumFacing direction;
	
	public TeslaEnergyConnection(StorageTE owner, EnumFacing dir) {
		this.owner = owner;
		direction = dir;
	}
	
	@Override
	public long takePower(long power, boolean simulated) {
		if (power == owner.energy) {
			if (!simulated) owner.energy = 0;
			owner.freezeExpend.put(direction, true);
			return power;
		} else if (power > owner.energy) {
			long tmp = (long)owner.energy;
			if (!simulated) owner.energy = 0;
			owner.freezeExpend.put(direction, true);
			return tmp;
		} else if (power < owner.energy) {
			if (!simulated) owner.energy -= power;
			owner.freezeExpend.put(direction, true);
			return power;
		}
		return 0;
	}

	@Override
	public long getStoredPower() {
		return (owner.energy > Long.MAX_VALUE ? Long.MAX_VALUE : (long)owner.energy);
	}

	@Override
	public long getCapacity() {
		return (owner.getMaxEnergy() > Long.MAX_VALUE ? Long.MAX_VALUE : (long)owner.getMaxEnergy());
	}

	@Override
	public long givePower(long power, boolean simulated) {
		if (owner.energy >= owner.getMaxEnergy()) {
			return 0;
		}
		if ((owner.energy + power) < owner.energy ) {
			long tmpGained = (long)(Double.MAX_VALUE - owner.energy);
			owner.energy = Double.MAX_VALUE;
			return tmpGained;
		} else {
			owner.energy += power;
			return power;
		}
	}

}
