package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.setup.Registration;
import io.github.mosadie.exponentialpower.tiles.AdvancedEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class AdvancedEnderGenerator extends Block implements EntityBlock {

	public AdvancedEnderGenerator() {
		super(BlockManager.BLOCK_PROPERTIES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AdvancedEnderGeneratorTE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == Registration.ADV_ENDER_GENERATOR_TE.get() ? GeneratorTE::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof AdvancedEnderGeneratorTE) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return ((GeneratorTE) tileEntity).getTitle();
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                        return new ContainerEnderGeneratorTE(i, playerInventory, (GeneratorTE) tileEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tileEntity.getBlockPos());
            } else {
            throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
	}



    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te != null && te instanceof GeneratorTE)
                ((GeneratorTE) te).setCustomName(stack.getDisplayName());
        }
    }

    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean bool) {
        if (!oldState.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(level, pos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(oldState, level, pos, newState, bool);
        }
    }
}
