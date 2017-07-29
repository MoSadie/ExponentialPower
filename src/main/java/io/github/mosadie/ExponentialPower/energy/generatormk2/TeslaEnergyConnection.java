package io.github.mosadie.ExponentialPower.energy.generatormk2;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorMk2TE;
import net.darkhax.tesla.api.ITeslaProducer;

public class TeslaEnergyConnection implements ITeslaProducer{

	EnderGeneratorMk2TE owner;
	
	public TeslaEnergyConnection(EnderGeneratorMk2TE owner) {
		this.owner = owner;
	}
	
	@Override
	public long takePower(long power, boolean simulated) {
		if (power == owner.energy) {
			if (!simulated) owner.energy = 0;
			owner.markDirty();
			return power;
		} else if (power > owner.energy) {
			long tmp = (long) (owner.energy > Long.MAX_VALUE ? Long.MAX_VALUE : owner.energy);
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
