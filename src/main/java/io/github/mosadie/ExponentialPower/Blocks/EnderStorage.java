package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.entities.BaseClasses.StorageBE;
import io.github.mosadie.exponentialpower.entities.EnderStorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EnderStorage extends Block implements EntityBlock {

	public EnderStorage() {
		super(BlockManager.BLOCK_PROPERTIES);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EnderStorageBE(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!level.isClientSide) {
			StorageBE te = (StorageBE) level.getBlockEntity(pos);
			double percent = ((int)(te.energy/te.getMaxEnergy() * 10000.00)) / 100.00;
			player.sendMessage(new TranslatableComponent("screen.exponentialpower.storage_total").append(new TextComponent(" " + te.energy + " / " + te.getMaxEnergy() + " RF (" + percent + "%)")), player.getUUID());
		}
		return InteractionResult.SUCCESS;
	}
}
