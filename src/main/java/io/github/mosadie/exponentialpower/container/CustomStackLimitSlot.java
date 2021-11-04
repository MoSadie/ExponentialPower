package io.github.mosadie.exponentialpower.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class CustomStackLimitSlot extends Slot {

	private final int stackLimit;
	
	public CustomStackLimitSlot(int stackLimit, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.stackLimit = stackLimit;
	}

	@Override
	public int getSlotStackLimit() {
		return stackLimit;
		
	}
}
