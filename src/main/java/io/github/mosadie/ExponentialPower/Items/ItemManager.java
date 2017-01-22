package io.github.mosadie.ExponentialPower.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemManager {
	public static Item enderCell;
	
	public static final CreativeTabs CreativeTab = new CreativeTabs("exponentialpower") {
	    @Override public ItemStack getTabIconItem() {
	        return new ItemStack(enderCell);
	    }
	};
	public static void createItems() {
		GameRegistry.register(enderCell = new EnderCell().setUnlocalizedName("endercell").setRegistryName("endercell"));
		
    }
}
