package io.github.mosadie.exponentialpower.entities;

import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderGeneratorBE extends GeneratorBE {
    public EnderGeneratorBE(BlockPos pos, BlockState state) {
        super(GeneratorBE.GeneratorTier.REGULAR, pos, state);
    }
}