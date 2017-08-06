package io.github.mosadie.ExponentialPower;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.network.GUIHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(ExponentialPower.instance, new GUIHandler());
    }

    public void init(FMLInitializationEvent e) {
    	GameRegistry.addRecipe(new ItemStack(ItemManager.enderCell), new Object[] {"DED", "EIE", "DED", 'D' ,Blocks.DIAMOND_BLOCK,'E',Items.ENDER_PEARL,'I',Items.ENDER_EYE});
    	GameRegistry.addRecipe(new ItemStack(BlockManager.enderStorage), new Object[] {"DOD", "OCO","DOD", 'D', Blocks.DIAMOND_BLOCK,'O',Blocks.OBSIDIAN,'C',ItemManager.enderCell});
    	GameRegistry.addRecipe(new ItemStack(BlockManager.enderGenerator), new Object[] {"IOI", "OCO", "IOI", 'I' ,Blocks.IRON_BLOCK,'O',Blocks.OBSIDIAN,'C',ItemManager.enderCell});
    	GameRegistry.addRecipe(new ItemStack(BlockManager.advancedEnderGenerator), new Object[] {"DOD","OGO","DOD",'D',Blocks.DIAMOND_BLOCK,'O',Blocks.OBSIDIAN,'G',BlockManager.enderGenerator});
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
    
    public void registerBlocks(RegistryEvent.Register<Block> event) {
    	System.out.println("RegBlock");
    	BlockManager.createBlocks(event);
    }
    
    public void registerItems(RegistryEvent.Register<Item> event) {
    	System.out.println("RegItem");
    	ItemManager.createItems(event);
    }
    
    public void registerModels(ModelRegistryEvent event) {
    	System.out.println("RegModel");
    	ItemManager.registerModels(event);
    }
}
