package io.github.mosadie.exponentialpower.tiles;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedEnderStorageBE extends StorageBE {
    public AdvancedEnderStorageBE(BlockPos pos, BlockState state) {
        super(StorageBE.StorageTier.ADVANCED, pos, state);
    }
}