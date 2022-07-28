package io.github.mosadie.exponentialpower.blocks;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.entities.AdvancedEnderStorageBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AdvancedEnderStorage extends Block implements EntityBlock {
	public AdvancedEnderStorage() {
		super(BlockManager.BLOCK_PROPERTIES);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedEnderStorageBE(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!level.isClientSide) {
			StorageBE te = (StorageBE) level.getBlockEntity(pos);
			double percent = ((int)(te.energy/te.getMaxEnergy() * 10000.00)) / 100.00;
			player.sendSystemMessage(Component.translatable("screen.exponentialpower.storage_total", te.energy, te.getMaxEnergy(), percent));
		}
		return InteractionResult.SUCCESS;
	}
}
