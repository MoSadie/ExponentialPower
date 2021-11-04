package io.github.mosadie.exponentialpower;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

	public static final String CATEGORY_ENDER_GENERATOR = "generator";

	public static final String SUBCATEGORY_ENDER_GENERATOR_REGULAR = "regular";
	public static ForgeConfigSpec.DoubleValue ENDER_GENERATOR_BASE;
	public static ForgeConfigSpec.IntValue ENDER_GENERATOR_MAX_STACK;

	public static final String SUBCATEGORY_ENDER_GENERATOR_ADVANCED = "advanced";
	public static ForgeConfigSpec.DoubleValue ADV_ENDER_GENERATOR_BASE;
	public static ForgeConfigSpec.IntValue ADV_ENDER_GENERATOR_MAX_STACK;

	public static final String CATEGORY_ENDER_STORAGE = "storage";

	public static final String SUBCATEGORY_ENDER_STORAGE_REGULAR = "regular";
	public static ForgeConfigSpec.LongValue ENDER_STORAGE_MAX_ENERGY;

	public static final String SUBCATEGORY_ENDER_STORAGE_ADVANCED = "advanced";
	public static ForgeConfigSpec.DoubleValue ADV_ENDER_STORAGE_MAX_ENERGY;


	public static ForgeConfigSpec SERVER_CONFIG;

	static {
		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

		SERVER_BUILDER.comment("Ender Generator Settings").push(CATEGORY_ENDER_GENERATOR);

		SERVER_BUILDER.push(SUBCATEGORY_ENDER_GENERATOR_REGULAR);
		ENDER_GENERATOR_BASE = SERVER_BUILDER.comment("Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly.").defineInRange("base", 2, 0, Double.MAX_VALUE);
		ENDER_GENERATOR_MAX_STACK = SERVER_BUILDER.comment("Controls the number of Ender Cells required to reach the maximum power output.").defineInRange("maxStack", 64, 1 ,64);
		SERVER_BUILDER.pop();

		SERVER_BUILDER.push(SUBCATEGORY_ENDER_GENERATOR_ADVANCED);
		ADV_ENDER_GENERATOR_BASE = SERVER_BUILDER.comment("Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly.").defineInRange("base", 2, 0, Double.MAX_VALUE);
		ADV_ENDER_GENERATOR_MAX_STACK = SERVER_BUILDER.comment("Controls the number of Ender Cells required to reach the maximum power output.").defineInRange("maxStack", 64, 1 ,64);
		SERVER_BUILDER.pop(2);

		SERVER_BUILDER.comment("Ender Storage Settigns").push(CATEGORY_ENDER_STORAGE);

		SERVER_BUILDER.push(SUBCATEGORY_ENDER_STORAGE_REGULAR);
		ENDER_STORAGE_MAX_ENERGY = SERVER_BUILDER.comment("The maximum amount of power that can be stored in a single Ender Storage block.").defineInRange("maxEnergy", Long.MAX_VALUE, 0, Long.MAX_VALUE);
		SERVER_BUILDER.pop().push(SUBCATEGORY_ENDER_STORAGE_ADVANCED);
		ADV_ENDER_STORAGE_MAX_ENERGY = SERVER_BUILDER.comment("The maximum amount of power that can be stored in a single Advanced Ender Storage block.").defineInRange("maxEnergy", Double.MAX_VALUE, 0, Double.MAX_VALUE);
		SERVER_BUILDER.pop(2);

		SERVER_CONFIG = SERVER_BUILDER.build();
	}

}
