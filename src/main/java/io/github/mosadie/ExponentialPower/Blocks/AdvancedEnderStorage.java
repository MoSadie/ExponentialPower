package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.tiles.AdvancedEnderStorageTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
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

public class AdvancedEnderStorage extends Block implements EntityBlock {
	public AdvancedEnderStorage() {
		super(BlockManager.BLOCK_PROPERTIES);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedEnderStorageTE(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (!worldIn.isClientSide) {
			StorageTE te = (StorageTE) worldIn.getBlockEntity(pos);
			double percent = ((int)(te.energy/te.getMaxEnergy() * 10000.00)) / 100.00;
			player.sendMessage(new TranslatableComponent("screen.exponentialpower.storage_total").append(new TextComponent(" " + te.energy + " / " + te.getMaxEnergy() + " RF (" + percent + "%)")), player.getUUID());
		}
		return InteractionResult.SUCCESS;
	}

//	@Override
//	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
//		StorageTE te = (StorageTE) worldIn.getTileEntity(pos);
//		String key = "{" + pos.getX() + "}{" + pos.getY() + "}{" + pos.getZ() + "}";
//		if (tmp_energy_map.containsKey(key)) {
//			if (tmp_energy_map.get(key) < te.energy) {
//				tmp_energy_map.put(key, te.energy);
//			}
//		} else {
//			tmp_energy_map.put(key, te.energy);
//		}
//		super.onBlockHarvested(worldIn, pos, state, player);
//	}

//
//
//	@Override
//	public static List<ItemStack> getDrops(BlockState state, ServerWorld worldIn, BlockPos pos, @Nullable TileEntity tileEntityIn) {
//		ItemStack drop = new ItemStack(Registration.ADV_ENDER_STORAGE.get());
//		List<ItemStack> drops = new ArrayList<>();
//
//		Double energy = 0.0;
//		String key = "{" + pos.getX() + "}{" + pos.getY() + "}{" + pos.getZ() + "}";
//		if (tmp_energy_map.containsKey(key)) {
//			energy = tmp_energy_map.get(key);
//			tmp_energy_map.remove(key);
//		}
//
//		//This is messy, but it works. so... (getCompoundTag doesn't add the newly created tag to the map)
//		CompoundNBT tag = new CompoundNBT();
//		CompoundNBT subTag = tag.getCompound("tag");
//		CompoundNBT subSubTag = subTag.getCompound("BlockEntityTag");
//		subSubTag.putDouble("energy", energy);
//		subTag.put("BlockEntityTag", subSubTag);
//		tag.put("tag", subTag);
//
//		drop.deserializeNBT(drop.write(tag));
//		drops.add(drop);
//
//		return drops;
//	}
}
