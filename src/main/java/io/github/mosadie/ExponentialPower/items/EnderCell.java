package io.github.mosadie.exponentialpower.items;

import net.minecraft.world.item.Item;

public class EnderCell extends Item {

	private final static Properties properties = new Properties()
			.tab(ItemManager.ITEM_GROUP)
			.fireResistant()
			.stacksTo(64);

	public EnderCell() {
		super(properties);
	}
}
