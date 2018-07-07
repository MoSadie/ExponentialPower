package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.Items.EnderStorageItem;
import io.github.mosadie.ExponentialPower.TileEntitys.*;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.StorageTE.StorageTier;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;
	public static Item itemEnderGenerator;
	public static Block advancedEnderGenerator;
	public static Item itemAdvancedEnderGenerator;
	public static Block enderStorage;
	public static Item itemEnderStorage;
	public static Block advancedEnderStorage;
	public static Item itemAdvancedEnderStorage;

	public static final Material CommonMaterial = new Material(MapColor.BLUE);

	public static void createBlocks(RegistryEvent.Register<Block> event) {
		enderGenerator = new EnderGenerator();
		advancedEnderGenerator = new AdvancedEnderGenerator();
		enderStorage = new EnderStorage();
		advancedEnderStorage = new AdvancedEnderStorage();
		event.getRegistry().register(enderGenerator.setRegistryName("endergenerator"));
		event.getRegistry().register(advancedEnderGenerator.setRegistryName("advancedendergenerator"));
		event.getRegistry().register(enderStorage.setRegistryName("enderStorage")); //TODO Make lower case
		event.getRegistry().register(advancedEnderStorage.setRegistryName("advancedenderstorage"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, new ResourceLocation(ExponentialPower.MODID + ":endergenerator_tile_entity"));
		GameRegistry.registerTileEntity(AdvancedEnderGeneratorTE.class, new ResourceLocation(ExponentialPower.MODID + ":advancedendergenerator_tile_entity"));
		GameRegistry.registerTileEntity(EnderStorageTE.class, new ResourceLocation(ExponentialPower.MODID + ":enderstorage_tile_entity"));
		GameRegistry.registerTileEntity(AdvancedEnderStorageTE.class, new ResourceLocation(ExponentialPower.MODID + ":advancedenderstorage_tile_entity"));
		itemEnderGenerator = new ItemBlock(enderGenerator).setRegistryName(enderGenerator.getRegistryName());
		itemAdvancedEnderGenerator = new ItemBlock(advancedEnderGenerator).setRegistryName(advancedEnderGenerator.getRegistryName());
		itemEnderStorage = new EnderStorageItem(enderStorage, StorageTier.REGULAR).setRegistryName(enderStorage.getRegistryName());
		itemAdvancedEnderStorage = new EnderStorageItem(advancedEnderStorage, StorageTier.ADVANCED).setRegistryName(advancedEnderStorage.getRegistryName());
	}
}
