package io.github.mosadie.ExponentialPower;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.network.GUIHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		ItemManager.createItems();
		BlockManager.createBlocks();
		NetworkRegistry.INSTANCE.registerGuiHandler(ExponentialPower.instance, new GUIHandler());
    }

    public void init(FMLInitializationEvent e) {
    	GameRegistry.addRecipe(new ItemStack(ItemManager.enderCell), new Object[] {"DED", "EIE", "DED", 'D' ,Blocks.DIAMOND_BLOCK,'E',Items.ENDER_PEARL,'I',Items.ENDER_EYE});
    	GameRegistry.addRecipe(new ItemStack(BlockManager.enderStorage), new Object[] {"DOD", "OCO","DOD", 'D', Blocks.DIAMOND_BLOCK,'O',Blocks.OBSIDIAN,'C',ItemManager.enderCell});
    	GameRegistry.addRecipe(new ItemStack(BlockManager.enderGenerator), new Object[] {"IOI", "OCO", "IOI", 'I' ,Blocks.IRON_BLOCK,'O',Blocks.OBSIDIAN,'C',ItemManager.enderCell});
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
