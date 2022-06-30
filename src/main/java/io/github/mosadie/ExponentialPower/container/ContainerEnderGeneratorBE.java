package io.github.mosadie.exponentialpower.container;

import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerEnderGeneratorBE extends AbstractContainerMenu {

	private final GeneratorBE be;

	public ContainerEnderGeneratorBE(int windowId, Inventory playerInv, GeneratorBE be) {
		super(Registration.ENDER_GENERATOR_CONTAINER.get(), windowId);
		this.be = be;

		// Block Entity, Slot 0, Slot IDs 0
		this.addSlot(new CustomStackLimitSlot(be.getMaxStack(), be, 0, 80, 35));

		// Player Inventory, Slot 9-35, Slot IDs 1-24
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Player Inventory, Slot 0-8, Slot IDs 25-36
		for (int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromSlot);

		if (slot != null && slot.hasItem()) {
			ItemStack current = slot.getItem();
			previous = current.copy();

			if (fromSlot == 0) {
				// From BE Inventory to Player Inventory
				if (current.getCount() <= 0) return ItemStack.EMPTY;
				if (!this.moveItemStackTo(current, 1, 36, true))
					return ItemStack.EMPTY;
			} else {
				// From Player Inventory to BE Inventory
				if (!this.moveItemStackTo(current, 0, 1, false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
		}
		return previous;
	}



	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	public GeneratorBE getBlockEntity() {
		return be;
	}
}
