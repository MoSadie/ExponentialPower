package io.github.mosadie.ExponentialPower;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventReceiver {

	public EventReceiver() {
		System.out.println("Event Receiver Created!");
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("ALL HAIL THE BLOCK");
		ExponentialPower.proxy.registerBlocks(event);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		System.out.println("ALL HAIL THE ITEMS");
		ExponentialPower.proxy.registerItems(event);
	}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		System.out.println("BUT NOW YOU CAN SEE THEM");
		ExponentialPower.proxy.registerModels(event);
	}
}
