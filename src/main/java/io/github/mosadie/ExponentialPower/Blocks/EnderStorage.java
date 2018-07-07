package io.github.mosadie.ExponentialPower.Blocks;

import java.util.HashMap;

import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.StorageTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EnderStorage extends Block implements ITileEntityProvider {

	public static HashMap<String, Double> tmp_energy_map; 

	public EnderStorage() {
		super(BlockManager.CommonMaterial);
		this.setUnlocalizedName("enderstorage");
		this.setCreativeTab(ItemManager.CreativeTab);
		this.setHardness(2.5F);
		this.setResistance(15f);
		this.hasTileEntity = true;

		tmp_energy_map = new HashMap<String, Double>(); //Key Syntax: {X}{Y}{Z}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new StorageTE(StorageTE.StorageTier.REGULAR);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			StorageTE te = (StorageTE) worldIn.getTileEntity(pos);
			double percent = ((int)(te.energy/te.getMaxEnergy() * 10000.00)) / 100.00;
			playerIn.sendMessage(new TextComponentString("Current Energy Stored: " + te.energy + " RF / " + te.getMaxEnergy() + " RF. (" + percent + "%)"));
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		StorageTE te = (StorageTE) world.getTileEntity(pos);
		String key = "{" + pos.getX() + "}{" + pos.getY() + "}{" + pos.getZ() + "}";
		if (tmp_energy_map.containsKey(key)) {
			if (tmp_energy_map.get(key) < te.energy) {
				tmp_energy_map.put(key, te.energy);
			}
		} else {
			tmp_energy_map.put(key, te.energy);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ItemStack drop = new ItemStack(BlockManager.enderStorage);

		double energy = 0;
		String key = "{" + pos.getX() + "}{" + pos.getY() + "}{" + pos.getZ() + "}";
		if (tmp_energy_map.containsKey(key)) {
			energy = tmp_energy_map.get(key);
			tmp_energy_map.remove(key);
		}

		//This is messy, but it works. so... (getCompoundTag doesn't add the newly created tag to the map)
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound subTag = tag.getCompoundTag("tag");
		NBTTagCompound subSubTag = subTag.getCompoundTag("BlockEntityTag");
		subSubTag.setDouble("energy", energy);
		subTag.setTag("BlockEntityTag", subSubTag);
		tag.setTag("tag", subTag);
		
		drop.deserializeNBT(drop.writeToNBT(tag));
		drops.add(drop);
	}
}
