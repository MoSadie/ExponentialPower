package io.github.mosadie.exponentialpower;

import io.github.mosadie.exponentialpower.setup.ClientSetup;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExponentialPower.MODID)
public class ExponentialPower {
	public static final String MODID = "exponentialpower";

	public static Logger LOGGER = LogManager.getLogger();


	public ExponentialPower() {

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

		Registration.init();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
	}
}
