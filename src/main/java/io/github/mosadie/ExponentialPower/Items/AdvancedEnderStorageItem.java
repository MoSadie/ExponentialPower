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

public class AdvancedEnderStorageItem extends ItemBlock {

	public AdvancedEnderStorageItem(Block block) {
		super(block);

		this.setCreativeTab(ItemManager.CreativeTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		double energy = 0;

		if (stack.hasTagCompound()) {
			NBTTagCompound blockEntityTag = stack.getSubCompound("BlockEntityTag");
			if (blockEntityTag.hasKey("energy")) {
				energy = blockEntityTag.getDouble("energy");
			}
		}
		
		tooltip.add("Current Energy Stored:");
		tooltip.add(energy + "/" + ConfigHandler.ADVANCED_STORAGE_MAXENERGY);
		double percent = ((int)(energy/ConfigHandler.ADVANCED_STORAGE_MAXENERGY * 10000.00)) / 100.00;
		tooltip.add("(" + percent + "%)");
	}
}