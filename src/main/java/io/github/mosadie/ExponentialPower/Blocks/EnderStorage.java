package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderStorageTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EnderStorage extends Block implements ITileEntityProvider {

	public EnderStorage() {
		super(Material.PISTON);
		this.setUnlocalizedName("enderstorage");
		this.setCreativeTab(ItemManager.CreativeTab);
		this.setHardness(2.5F);
		this.setResistance(15f);
		this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EnderStorageTE();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			EnderStorageTE te = (EnderStorageTE) worldIn.getTileEntity(pos);
			playerIn.sendMessage(new TextComponentString("Current Energy Stored: " + te.energy + " RF / " + Long.MAX_VALUE + " RF."));
		}
		return true;
	}
}
