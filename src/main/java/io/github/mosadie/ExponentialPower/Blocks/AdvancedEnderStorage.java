package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.tiles.AdvancedEnderStorageTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AdvancedEnderStorage extends Block {

//	public static HashMap<String, Double> tmp_energy_map;

	public AdvancedEnderStorage() {
		super(BlockManager.BLOCK_PROPERTIES);

//		tmp_energy_map = new HashMap<String, Double>(); //Key Syntax: {X}{Y}{Z}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new AdvancedEnderStorageTE();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			StorageTE te = (StorageTE) worldIn.getTileEntity(pos);
			double percent = ((int)(te.energy/te.getMaxEnergy() * 10000.00)) / 100.00;
			player.sendStatusMessage(new TranslationTextComponent("screen.exponentialpower.storage_total").append(new StringTextComponent(" " + te.energy + " / " + te.getMaxEnergy() + " RF (" + percent + "%)")), true);
		}
		return ActionResultType.SUCCESS;
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
