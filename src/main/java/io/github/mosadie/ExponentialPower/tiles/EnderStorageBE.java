package io.github.mosadie.exponentialpower.tiles;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderStorageBE extends StorageBE {
    public EnderStorageBE(BlockPos pos, BlockState state) {
        super(StorageBE.StorageTier.REGULAR, pos, state);
    }
}