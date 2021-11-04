package io.github.mosadie.exponentialpower.items;

import io.github.mosadie.exponentialpower.Config;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.StorageTE;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EnderStorageItem extends BlockItem {

	private final static Item.Properties properties = new Item.Properties()
			.maxStackSize(1)
			.isImmuneToFire()
			.group(ItemManager.ITEM_GROUP);

	public EnderStorageItem(Block block, StorageTE.StorageTier tier) {
		super(block, properties);
		this.tier = tier;
	}

	private final StorageTE.StorageTier tier;

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		double energy = 0;

		if (stack.hasTag()) {
			CompoundNBT blockEntityTag = stack.getChildTag("BlockEntityTag");
			if (blockEntityTag.contains("energy")) {
				energy = blockEntityTag.getDouble("energy");
			}
		}
		
		tooltip.add(new StringTextComponent("Current Energy Stored:"));
		tooltip.add(new StringTextComponent(energy + "/" + getMaxEnergy()));
		double percent = ((int)(energy/getMaxEnergy() * 10000.00)) / 100.00;
		tooltip.add(new StringTextComponent("(" + percent + "%)"));
	}

	public double getMaxEnergy() {
		switch (tier) {
			case REGULAR:
				return Config.ENDER_STORAGE_MAX_ENERGY.get();

			case ADVANCED:
				return Config.ADV_ENDER_STORAGE_MAX_ENERGY.get();

			default:
				return Double.MAX_VALUE;
		}
	}
}