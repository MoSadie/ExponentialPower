package io.github.mosadie.exponentialpower.items;

import net.minecraft.item.Item;

public class EnderCell extends Item {

	private final static Properties properties = new Properties()
			.group(ItemManager.ITEM_GROUP)
			.isImmuneToFire()
			.maxStackSize(64);

	public EnderCell() {
		super(properties);
	}
}
