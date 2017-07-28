package io.github.mosadie.ExponentialPower.energy.storage;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.ITeslaConsumer;

public class TeslaEnergyConnection implements ITeslaHolder, ITeslaProducer, ITeslaConsumer{

	EnderStorageTE owner;
	
	public TeslaEnergyConnection(EnderStorageTE owner) {
		this.owner = owner;
	}
	
	@Override
	public long takePower(long power, boolean simulated) {
		if (power == owner.energy) {
			if (!simulated) owner.energy = 0;
			return power;
		} else if (power > owner.energy) {
			long tmp = owner.energy;
			if (!simulated) owner.energy = 0;
			return tmp;
		} else if (power < owner.energy) {
			if (!simulated) owner.energy -= power;
			return power;
		}
		return 0;
	}

	@Override
	public long getStoredPower() {
		return owner.energy;
	}

	@Override
	public long getCapacity() {
		// TODO Auto-generated method stub
		return Long.MAX_VALUE;
	}

	@Override
	public long givePower(long power, boolean simulated) {
		if (owner.energy == Long.MAX_VALUE) {
			return 0;
		}
		if ((owner.energy + power) < owner.energy ) {
			long tmpGained = Long.MAX_VALUE - owner.energy;
			owner.energy = Long.MAX_VALUE;
			return tmpGained;
		} else {
			owner.energy += power;
			return power;
		}
	}

}
