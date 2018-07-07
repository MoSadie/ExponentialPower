package io.github.mosadie.ExponentialPower.GUIContainer;

import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.GeneratorTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnderGeneratorTE extends Container {

	private GeneratorTE te;

	public ContainerEnderGeneratorTE(IInventory playerInv, GeneratorTE te) {
		this.te = te;

		// Tile Entity, Slot 0, Slot IDs 0 
		this.addSlotToContainer(new CustomStackLimitSlot(te.getMaxStack(), te, 0, 80, 35));

		// Player Inventory, Slot 9-35, Slot IDs 1-24
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Player Inventory, Slot 0-8, Slot IDs 25-36
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot == 0) {
				// From TE Inventory to Player Inventory
				if (current.getCount() <= 0) return ItemStack.EMPTY;
				if (!this.mergeItemStack(current, 1, 36, true))
					return ItemStack.EMPTY;
			} else {
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 1, false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
		}
		return previous;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.te.isUsableByPlayer(playerIn);
	}

}
