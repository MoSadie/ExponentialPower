package io.github.mosadie.ExponentialPower;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = ExponentialPower.MODID, category = "")
public class ConfigHandler {

	//Configuration Catagories
	@Name("Ender Generator")
	public static ConfigEnderGenerator 			ender_generator = new ConfigEnderGenerator();

	@Name("Advanced Ender Generator")
	public static ConfigAdvancedEnderGenerator 	advanced_ender_generator = new ConfigAdvancedEnderGenerator();

	@Name("Ender Storage")
	public static ConfigEnderStorage 			ender_storage = new ConfigEnderStorage();

	@Name("Advanced Ender Storage")
	public static ConfigAdvancedEnderStorage	advanced_ender_storage = new ConfigAdvancedEnderStorage();
	
	//Advanced Ender Generator Config Values
	public static class ConfigAdvancedEnderGenerator {
		@Name("Base")
		@Comment({"Controls the rate of change of the power output. Remember Base^MaxStack must be less than Double.MAX_VALUE for things to work correctly."})
		public double			BASE = 2;

		@Name("Max Stack")
		@Comment({"Controls the number of Ender Cells required to reach the maximum power output."})
		@RangeInt(min = 1, max = 64)
		public int				MAXSTACK = 64;
	}
	
	//Ender Generator Config Values
	public static class ConfigEnderGenerator {
		@Name("Base")
		@Comment({"Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly."})
		public double			BASE = 2;

		@Name("Max Stack")
		@Comment({"Controls the number of Ender Cells required to reach the maximum power output."})
		@RangeInt(min = 1, max = 64)
		public int				MAXSTACK = 64;
	}
	
	//Ender Storage Config Values
	public static class ConfigEnderStorage {
		/*
		//TODO Put this back when Long type is supported in @Config system
		@Name("Max Energy")
		@Comment({"The maximum amount of power that can be stored in a single Ender Storage block.", "Min: 1", "Max: 9223372036854775806"})
		public long				MAXENERGY = Long.MAX_VALUE;
		*/

		@Name("Max Energy")
		@Comment({"The percent of the maximum amount of power that can be stored in a single Ender Storage block."})
		@RangeDouble(min = 0.01, max = 1.0)
		public double			MAXENERGYPERCENT = 1.0;
	}

	//Advanced Ender Storage Config Values
	public static class ConfigAdvancedEnderStorage {
		/*
		//TODO Put this back when Long type is supported in @Config system
		@Name("Max Energy")
		@Comment({"The maximum amount of power that can be stored in a single Advanced Ender Storage block."})
		@RangeDouble(min = 0.0)
		public double			MAXENERGY = Double.MAX_VALUE;
		*/

		@Name("Max Energy")
		@Comment({"The percent of the maximum amount of power that can be stored in a single Advanced Ender Storage block."})
		@RangeDouble(min = 0.01, max = 1.0)
		public double			MAXENERGYPERCENT = 1.0;
	}
}
