package io.github.mosadie.exponentialpower.container;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CustomStackLimitSlot extends Slot {

	private final int stackLimit;
	
	public CustomStackLimitSlot(int stackLimit, GeneratorTE generator, int index, int xPosition, int yPosition) {
		super(generator, index, xPosition, yPosition);
		this.stackLimit = stackLimit;
	}

	@Override
	public int getMaxStackSize() {
		return stackLimit;
	}
}
