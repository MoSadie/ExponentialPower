package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.AdvancedEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;
	public static Block advancedEnderGenerator;
	public static Block enderStorage;

	public static void createBlocks() {
		enderGenerator = new EnderGenerator();
		advancedEnderGenerator = new AdvancedEnderGenerator();
		enderStorage = new EnderStorage();
		GameRegistry.register(enderGenerator.setRegistryName("endergenerator"));
		GameRegistry.register(advancedEnderGenerator.setRegistryName("advancedendergenerator"));
		GameRegistry.register(enderStorage.setRegistryName("enderStorage"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
		GameRegistry.registerTileEntity(AdvancedEnderGeneratorTE.class, "exponentialpower_advancedendergenerator_tile_entity");
		GameRegistry.registerTileEntity(EnderStorageTE.class, "exponentialpower_enderstorage_tile_entity");
		GameRegistry.register(new ItemBlock(enderGenerator).setRegistryName(enderGenerator.getRegistryName()));
		GameRegistry.register(new ItemBlock(advancedEnderGenerator).setRegistryName(advancedEnderGenerator.getRegistryName()));
		GameRegistry.register(new ItemBlock(enderStorage).setRegistryName(enderStorage.getRegistryName()));
	}
}
