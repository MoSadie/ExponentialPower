package io.github.mosadie.ExponentialPower;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExponentialPower.MODID, name = ExponentialPower.MODNAME, version = ExponentialPower.VERSION, updateJSON=ExponentialPower.UPDATEJSON, useMetadata=true)
public class ExponentialPower {
	public static final String MODID = "exponentialpower";
	public static final String MODNAME = "Exponential Power";
	public static final String VERSION = "1.3.0.0";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/MoSadie/ExponentialPower/master/update.json";

	@Instance
	public static ExponentialPower instance;
	
	public static Logger LOGGER;
	
	@SidedProxy(clientSide="io.github.mosadie.ExponentialPower.ClientProxy", serverSide="io.github.mosadie.ExponentialPower.ServerProxy")
	public static CommonProxy proxy;

	public ExponentialPower() {
		MinecraftForge.EVENT_BUS.register(new EventReceiver());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER = e.getModLog();
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
