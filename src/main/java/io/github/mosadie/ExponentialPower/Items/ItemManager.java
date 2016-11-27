package io.github.mosadie.ExponentialPower.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemManager {
	public static Item enderCell;
	
	public static final CreativeTabs CreativeTab = new CreativeTabs("exponentialpower") {
	    @Override public Item getTabIconItem() {
	        return enderCell;
	    }
	};
	public static void createItems() {
		GameRegistry.registerItem(enderCell = new EnderCell().setUnlocalizedName("endercell"), "endercell");
		
    }
}
