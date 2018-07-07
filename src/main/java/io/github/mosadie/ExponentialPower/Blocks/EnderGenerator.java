package io.github.mosadie.ExponentialPower.Blocks;

import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.GeneratorTE;
import io.github.mosadie.ExponentialPower.network.GUIHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderGenerator extends Block implements ITileEntityProvider {
	public EnderGenerator() {
		super(BlockManager.CommonMaterial);
		this.setUnlocalizedName("endergenerator");
		this.setCreativeTab(ItemManager.CreativeTab);
		this.setHardness(2.5F);
		this.setResistance(15f);
		this.hasTileEntity = true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new GeneratorTE(GeneratorTE.GeneratorTier.REGULAR);
    }
    
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
	        playerIn.openGui(ExponentialPower.instance, GUIHandler.MOD_TILE_ENTITY_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            ((GeneratorTE) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	GeneratorTE te = (GeneratorTE) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, state);
    }
}
