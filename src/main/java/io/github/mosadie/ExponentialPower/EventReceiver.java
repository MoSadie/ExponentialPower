package io.github.mosadie.ExponentialPower;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
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
}
