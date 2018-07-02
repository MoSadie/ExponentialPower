package io.github.mosadie.ExponentialPower.Items;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemManager {
	public static Item enderCell;
	
	public static final CreativeTabs CreativeTab = new CreativeTabs("exponentialpower") {
	    @Override public ItemStack getTabIconItem() {
	        return new ItemStack(enderCell);
	    }
	};
	public static void createItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(enderCell = new EnderCell().setUnlocalizedName("endercell").setRegistryName("endercell"));
		event.getRegistry().registerAll(BlockManager.itemEnderGenerator, BlockManager.itemEnderStorage, BlockManager.itemAdvancedEnderGenerator, BlockManager.itemAdvancedEnderStorage);
    }
	
	public static void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(enderCell, 0, new ModelResourceLocation(enderCell.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(BlockManager.itemEnderGenerator, 0, new ModelResourceLocation( BlockManager.enderGenerator.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(BlockManager.itemEnderStorage, 0, new ModelResourceLocation(BlockManager.enderStorage.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(BlockManager.itemAdvancedEnderGenerator, 0, new ModelResourceLocation(BlockManager.advancedEnderGenerator.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(BlockManager.itemAdvancedEnderStorage, 0, new ModelResourceLocation(BlockManager.advancedEnderStorage.getRegistryName(), "inventory"));
	}
}
