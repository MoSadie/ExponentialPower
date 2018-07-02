package io.github.mosadie.ExponentialPower;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import io.github.mosadie.ExponentialPower.network.GUIHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(ExponentialPower.instance, new GUIHandler());
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
    
    public void registerBlocks(RegistryEvent.Register<Block> event) {
    	BlockManager.createBlocks(event);
    }
    
    public void registerItems(RegistryEvent.Register<Item> event) {
    	ItemManager.createItems(event);
    }
    
    public void registerModels(ModelRegistryEvent event) {
      ItemManager.registerModels(event);
    }
}
