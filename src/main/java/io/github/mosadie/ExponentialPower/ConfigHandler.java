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

	@Name("Ender Storage")
	public static ConfigEnderStorage 			ender_storage = new ConfigEnderStorage();
	
	//Ender Generator Config Values
	public static class ConfigEnderGenerator {


		//Advanced Ender Generator Config Values
		public static class AdvancedGenerator {
			@Name("Base")
			@Comment({"Controls the rate of change of the power output. Remember Base^MaxStack must be less than Double.MAX_VALUE for things to work correctly."})
			public double			BASE = 2;

			@Name("Max Stack")
			@Comment({"Controls the number of Ender Cells required to reach the maximum power output."})
			@RangeInt(min = 1, max = 64)
			public int				MAXSTACK = 64;
		}

		public static class RegularGenerator {
			@Name("Base")
			@Comment({"Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly."})
			public double			BASE = 2;

			@Name("Max Stack")
			@Comment({"Controls the number of Ender Cells required to reach the maximum power output."})
			@RangeInt(min = 1, max = 64)
			public int				MAXSTACK = 64;
		}

		public AdvancedGenerator Advanced = new AdvancedGenerator();
		public RegularGenerator Regular = new RegularGenerator();
	}
	
	//Ender Storage Config Values
	public static class ConfigEnderStorage {

		public static class RegularStorage {
			@Name("Max Energy")
			@Comment({"The maximum amount of power that can be stored in a single Ender Storage block."})
			@RangeDouble(min = 1, max = (double)Long.MAX_VALUE)
			public double				MAXENERGY = Long.MAX_VALUE;
		}

		public static class AdvancedStorage {
			@Name("Max Energy")
			@Comment({"The maximum amount of power that can be stored in a single Advanced Ender Storage block."})
			@RangeDouble(min = 1, max = Double.MAX_VALUE)
			public double				MAXENERGY = Double.MAX_VALUE;	
		}

		public AdvancedStorage Advanced = new AdvancedStorage();
		public RegularStorage Regular = new RegularStorage();
	}
}
