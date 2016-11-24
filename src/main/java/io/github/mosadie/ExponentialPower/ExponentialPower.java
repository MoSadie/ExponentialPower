package io.github.mosadie.ExponentialPower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExponentialPower.MODID, name = ExponentialPower.MODNAME, version = ExponentialPower.VERSION)
public class ExponentialPower {
	public static final String MODID = "exponentialpower";
	public static final String MODNAME = "Exponential Power";
	public static final String VERSION = "0.0.0.1";

	@Instance
	public static ExponentialPower instance = new ExponentialPower();
	
	@SidedProxy(clientSide="io.github.mosadie.ExponentialPower.ClientProxy", serverSide="io.github.mosadie.ExponentialPower.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
		//Create Blocks and Items
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		//Crafting
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		//Don't Think I need this...
	}
}
