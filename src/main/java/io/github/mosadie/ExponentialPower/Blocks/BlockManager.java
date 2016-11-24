package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block enderGenerator;

	public static void createBlocks() {
		System.out.println("BlockManager Was Called. TACOS");
		enderGenerator = new EnderGenerator();
		GameRegistry.registerBlock(enderGenerator.setRegistryName("endergenerator"));
		GameRegistry.registerTileEntity(EnderGeneratorTE.class, "exponentialpower_endergenerator_tile_entity");
	}
}
