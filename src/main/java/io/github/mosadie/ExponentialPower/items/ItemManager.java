package io.github.mosadie.exponentialpower.items;

import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemManager {

	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab("exponentialpower") {

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Registration.ENDER_CELL.get());
		}
	};
}
