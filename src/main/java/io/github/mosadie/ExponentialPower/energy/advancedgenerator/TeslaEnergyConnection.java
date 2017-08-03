package io.github.mosadie.ExponentialPower.energy.advancedgenerator;

import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderGeneratorTE;
import net.darkhax.tesla.api.ITeslaProducer;

public class TeslaEnergyConnection implements ITeslaProducer{

	AdvancedEnderGeneratorTE owner;
	
	public TeslaEnergyConnection(AdvancedEnderGeneratorTE owner) {
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
