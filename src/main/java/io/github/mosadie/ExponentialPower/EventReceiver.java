package io.github.mosadie.ExponentialPower;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventReceiver {

	public EventReceiver() {
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		ExponentialPower.proxy.registerBlocks(event);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		ExponentialPower.proxy.registerItems(event);
	}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		ExponentialPower.proxy.registerModels(event);
	}

	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ExponentialPower.MODID)) {
			ConfigManager.sync(ExponentialPower.MODID, Config.Type.INSTANCE);
		}
	}
}
