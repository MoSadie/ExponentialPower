package io.github.mosadie.exponentialpower.tiles;

import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderStorageTE extends StorageTE {
    public EnderStorageTE(BlockPos pos, BlockState state) {
        super(StorageTE.StorageTier.REGULAR, pos, state);
    }
}