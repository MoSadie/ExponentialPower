package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;

	public static void createBlocks() {
		enderGenerator = new EnderGenerator();
		GameRegistry.register(enderGenerator.setRegistryName("endergenerator"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
		GameRegistry.register(new ItemBlock(enderGenerator).setRegistryName(enderGenerator.getRegistryName()));
	}
}
