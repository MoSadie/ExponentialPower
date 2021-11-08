package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.client.gui.GUIEnderGeneratorTE;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.AdvancedEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class AdvancedEnderGenerator extends Block {

	public AdvancedEnderGenerator() {
		super(BlockManager.BLOCK_PROPERTIES);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AdvancedEnderGeneratorTE();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof AdvancedEnderGeneratorTE) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return ((GeneratorTE) tileEntity).getTitle();
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ContainerEnderGeneratorTE(i, playerInventory, (GeneratorTE) tileEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
            throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof GeneratorTE)
                ((GeneratorTE) te).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof GeneratorTE)
            InventoryHelper.dropInventoryItems(worldIn, pos, (GeneratorTE) te);

	    super.onBlockHarvested(worldIn, pos, state, player);
    }
}
