package io.github.mosadie.exponentialpower.items;

import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemManager {

	public static final ItemGroup ITEM_GROUP = new ItemGroup("exponentialpower") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.ENDER_CELL.get());
		}
	};

//	public static Item enderCell;
//
//	public static final CreativeTabs CreativeTab = new CreativeTabs("exponentialpower") {
//	    @Override public ItemStack getTabIconItem() {
//	        return new ItemStack(enderCell);
//	    }
//	};
//	public static void createItems(RegistryEvent.Register<Item> event) {
//		event.getRegistry().register(enderCell = new EnderCell().setUnlocalizedName("endercell").setRegistryName("endercell"));
//		event.getRegistry().registerAll(BlockManager.itemEnderGenerator, BlockManager.itemEnderStorage, BlockManager.itemAdvancedEnderGenerator, BlockManager.itemAdvancedEnderStorage);
//    }
//
//	public static void registerModels(ModelRegistryEvent event) {
//		ModelLoader.setCustomModelResourceLocation(enderCell, 0, new ModelResourceLocation(enderCell.getRegistryName(), "inventory"));
//		ModelLoader.setCustomModelResourceLocation(BlockManager.itemEnderGenerator, 0, new ModelResourceLocation(BlockManager.enderGenerator.getRegistryName(), "inventory"));
//		ModelLoader.setCustomModelResourceLocation(BlockManager.itemEnderStorage, 0, new ModelResourceLocation(BlockManager.enderStorage.getRegistryName(), "inventory"));
//		ModelLoader.setCustomModelResourceLocation(BlockManager.itemAdvancedEnderGenerator, 0, new ModelResourceLocation(BlockManager.advancedEnderGenerator.getRegistryName(), "inventory"));
//		ModelLoader.setCustomModelResourceLocation(BlockManager.itemAdvancedEnderStorage, 0, new ModelResourceLocation(BlockManager.advancedEnderStorage.getRegistryName(), "inventory"));
//	}
}
