package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;
	public static Item itemEnderGenerator;
	public static Block advancedEnderGenerator;
	public static Item itemAdvancedEnderGenerator;
	public static Block enderStorage;
	public static Item itemEnderStorage;

	public static void createBlocks(RegistryEvent.Register<Block> event) {
		enderGenerator = new EnderGenerator();
		advancedEnderGenerator = new AdvancedEnderGenerator();
		enderStorage = new EnderStorage();
		event.getRegistry().register(enderGenerator.setRegistryName("endergenerator"));
		event.getRegistry().register(advancedEnderGenerator.setRegistryName("advancedendergenerator"));
		event.getRegistry().register(enderStorage.setRegistryName("enderStorage"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
		GameRegistry.registerTileEntity(AdvancedEnderGeneratorTE.class, "exponentialpower_advancedendergenerator_tile_entity");
		GameRegistry.registerTileEntity(EnderStorageTE.class, "exponentialpower_enderstorage_tile_entity");
		itemEnderGenerator = new ItemBlock(enderGenerator).setRegistryName(enderGenerator.getRegistryName());
		itemAdvancedEnderGenerator = new ItemBlock(advancedEnderGenerator).setRegistryName(advancedEnderGenerator.getRegistryName());
		itemEnderStorage = new ItemBlock(enderStorage).setRegistryName(enderStorage.getRegistryName());
	}
}
