package io.github.mosadie.exponentialpower.entities;

import io.github.mosadie.exponentialpower.entities.BaseClasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderStorageBE extends StorageBE {
    public EnderStorageBE(BlockPos pos, BlockState state) {
        super(StorageBE.StorageTier.REGULAR, pos, state);
    }
}