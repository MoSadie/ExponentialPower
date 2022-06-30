package io.github.mosadie.exponentialpower.tiles;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderGeneratorTE extends GeneratorTE {
    public EnderGeneratorTE(BlockPos pos, BlockState state) {
        super(GeneratorTE.GeneratorTier.REGULAR, pos, state);
    }
}