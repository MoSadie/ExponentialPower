package io.github.mosadie.ExponentialPower.Items;

import java.util.List;

import javax.annotation.Nullable;

import io.github.mosadie.ExponentialPower.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderStorageItem extends ItemBlock {

	public EnderStorageItem(Block block) {
		super(block);

		this.setCreativeTab(ItemManager.CreativeTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		long energy = 0;

		if (stack.hasTagCompound()) {
			NBTTagCompound blockEntityTag = stack.getSubCompound("BlockEntityTag");
			if (blockEntityTag.hasKey("energy")) {
				energy = blockEntityTag.getLong("energy");
			}
		}
		
		tooltip.add("Current Energy Stored:");
		tooltip.add(energy + "/" + (ConfigHandler.ender_storage.MAXENERGYPERCENT * Long.MAX_VALUE));
		double percent = ((int)((double)energy/(double)(ConfigHandler.ender_storage.MAXENERGYPERCENT * Long.MAX_VALUE) * 10000.00)) / 100.00;
		tooltip.add("(" + percent + "%)");
	}
}