package io.github.mosadie.exponentialpower.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class BlockManager {

	public static final Material BLOCK_MATERIAL = new Material(MaterialColor.BLUE, false, true, true, true, false, false, PushReaction.DESTROY);
	public static final AbstractBlock.Properties BLOCK_PROPERTIES = AbstractBlock.Properties.create(BLOCK_MATERIAL)
			.hardnessAndResistance(2.5f, 15.0f);

}
