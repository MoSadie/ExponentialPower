package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;

	public static void createBlocks() {
		enderGenerator = new EnderGenerator();
		GameRegistry.registerBlock(enderGenerator.setRegistryName("endergenerator"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
	}
}
