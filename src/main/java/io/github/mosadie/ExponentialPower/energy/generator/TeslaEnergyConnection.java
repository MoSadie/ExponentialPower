package io.github.mosadie.ExponentialPower.energy.generator;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import net.darkhax.tesla.api.ITeslaProducer;

public class TeslaEnergyConnection implements ITeslaProducer{

	EnderGeneratorTE owner;
	
	public TeslaEnergyConnection(EnderGeneratorTE owner) {
		this.owner = owner;
	}
	
	@Override
	public long takePower(long power, boolean simulated) {
		if (power == owner.energy) {
			if (!simulated) owner.energy = 0;
			owner.markDirty();
			return power;
		} else if (power > owner.energy) {
			long tmp = owner.energy;
			if (!simulated) owner.energy = 0;
			owner.markDirty();
			return tmp;
		} else if (power < owner.energy) {
			if (!simulated) owner.energy -= power;
			owner.markDirty();
			return power;
		}
		return 0;
	}

}
