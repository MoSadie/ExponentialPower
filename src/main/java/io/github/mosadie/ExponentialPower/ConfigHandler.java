package io.github.mosadie.ExponentialPower;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {

	//Configuration Catagories
	public static final String 		CONFIG_ENDER_GENERATOR = "EnderGenerator";
	public static final String 		CONFIG_ADVANCED_ENDER_GENERATOR = "AdvancedEnderGenerator";
	public static final String 		CONFIG_ENDER_STORAGE = "EnderStorage";
	
	//Config
	private static Configuration	config;
	
	//Advanced Ender Generator Config Values
	public static double			ADVANCED_BASE;
	public static int				ADVANCED_MAXSTACK;
	
	//Ender Generator Config Values
	public static double			REGULAR_BASE;
	public static int				REGULAR_MAXSTACK;
	
	//Ender Storage Config Values
	public static long				STORAGE_MAXENERGY;

	//Advanced Ender Storage Config Values
	public static double			ADVANCED_STORAGE_MAXENERGY;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);
		config.load();
		
		//Setup Config Variables
		
		//Advanced Ender Generator
		ADVANCED_BASE = getConfigProp(ExponentialPower.CONFIG_ADVANCED_ENDER_GENERATOR,"Base", "Controls the rate of change of the power output. Remember Base^MaxStack must be less than Double.MAX_VALUE for things to work correctly.", Double.toString(2.0),Double.MIN_VALUE,Double.MAX_VALUE).getDouble();
		ADVANCED_MAXSTACK = getConfigProp(ExponentialPower.CONFIG_ADVANCED_ENDER_GENERATOR, "MaxStack", "Controls the number of Ender Cells required to reach the maximum power output. Min: 1 Max: 64 (inclusive)", Integer.toString(64), 1, 64).getInt();
		
		//Ender Generator
		REGULAR_BASE = getConfigProp(ExponentialPower.CONFIG_ENDER_GENERATOR, "Base", "Controls the rate of change of the power output. Remember Base^63 must be less than Long.MAX_VALUE for things to work correctly.", Double.toString(2.0), Long.MIN_VALUE, Long.MAX_VALUE).getDouble();
		REGULAR_MAXSTACK = getConfigProp(ExponentialPower.CONFIG_ENDER_GENERATOR, "MaxStack", "Controls the number of Ender Cells required to reach the maximum power output. Min: 1 Max: 64 (inclusive)", Integer.toString(64), 1, 64).getInt();
		//Ender Storage
		STORAGE_MAXENERGY = getConfigProp(ExponentialPower.CONFIG_ENDER_STORAGE, "EnderStorageMaximum", "The maximum amount of power that can be stored in a single Ender Storage block. Min: 1 Max: 9223372036854775806 (inclusive)", "9223372036854775806", 1.0, 9223372036854775806.0).getLong();

		//Advanced Ender Storage
		ADVANCED_STORAGE_MAXENERGY = getConfigProp(ExponentialPower.CONFIG_ADVANCED_ENDER_STORAGE, "EnderStorageMaximum", "The maximum amount of power that can be stored in a single Advanced Ender Storage block. Min: 1 Max: " + String.format("%f", Double.MAX_VALUE) + " (inclusive)", String.format("%f", Double.MAX_VALUE), 1.0, Double.MAX_VALUE).getDouble();
	}
	
	private static Property getConfigProp(String category, String key, String comment, String defaultValue, double minValue, double maxValue) {
		Property prop = config.get(category, key, defaultValue, comment);
		if (prop.isDefault() || prop.getDouble(minValue) < minValue || prop.getDouble(maxValue) > maxValue) {
			ExponentialPower.LOGGER.info("Setting default value of " + category + " " + key + " to " + defaultValue);
			prop.setValue(defaultValue);
			config.save();
		}
		return prop;
	}
}
