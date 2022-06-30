package io.github.mosadie.exponentialpower.container;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorBE;
import net.minecraft.world.inventory.Slot;

public class CustomStackLimitSlot extends Slot {

	private final int stackLimit;
	
	public CustomStackLimitSlot(int stackLimit, GeneratorBE generator, int index, int xPosition, int yPosition) {
		super(generator, index, xPosition, yPosition);
		this.stackLimit = stackLimit;
	}

	@Override
	public int getMaxStackSize() {
		return stackLimit;
	}
}
