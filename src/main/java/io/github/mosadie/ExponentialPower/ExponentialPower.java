package io.github.mosadie.ExponentialPower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExponentialPower.MODID, name = ExponentialPower.MODNAME, version = ExponentialPower.VERSION)
public class ExponentialPower {
	public static final String MODID = "ExponentialPower";
	public static final String MODNAME = "Exponential Power";
	public static final String VERSION = "0.0.1";

	@Instance
	public static ExponentialPower instance = new ExponentialPower();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//Create Blocks and Items
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		//Crafting
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		//Don't Think I need this...
	}
}
