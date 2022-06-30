package io.github.mosadie.exponentialpower.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockManager {

	public static final Material BLOCK_MATERIAL = new Material(MaterialColor.COLOR_BLUE, false, true, true, true, false, false, PushReaction.DESTROY);
	public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(BLOCK_MATERIAL)
			.strength(2.5f, 15.0f);

}
