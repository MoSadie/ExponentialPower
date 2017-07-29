package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorMk2TE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;
	public static Block enderGeneratorMk2;
	public static Block enderStorage;

	public static void createBlocks() {
		enderGenerator = new EnderGenerator();
		enderGeneratorMk2 = new EnderGeneratorMk2();
		enderStorage = new EnderStorage();
		GameRegistry.register(enderGenerator.setRegistryName("endergenerator"));
		GameRegistry.register(enderGeneratorMk2.setRegistryName("endergeneratormk2"));
		GameRegistry.register(enderStorage.setRegistryName("enderStorage"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
		GameRegistry.registerTileEntity(EnderGeneratorMk2TE.class, "exponentialpower_endergeneratormk2_tile_entity");
		GameRegistry.registerTileEntity(EnderStorageTE.class, "exponentialpower_enderstorage_tile_entity");
		GameRegistry.register(new ItemBlock(enderGenerator).setRegistryName(enderGenerator.getRegistryName()));
		GameRegistry.register(new ItemBlock(enderGeneratorMk2).setRegistryName(enderGeneratorMk2.getRegistryName()));
		GameRegistry.register(new ItemBlock(enderStorage).setRegistryName(enderStorage.getRegistryName()));
	}
}
