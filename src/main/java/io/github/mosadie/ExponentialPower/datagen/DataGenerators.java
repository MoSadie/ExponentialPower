package io.github.mosadie.exponentialpower.datagen;

import io.github.mosadie.exponentialpower.ExponentialPower;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExponentialPower.LOGGER.info("Registering Data Providers!");

        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            ExponentialPower.LOGGER.info("Registering Server Providers!");
            generator.addProvider(new DataRecipes(generator));
            generator.addProvider(new DataLootTables(generator));
        }
        if (event.includeClient()) {
            ExponentialPower.LOGGER.info("Registering Client Providers!");
            generator.addProvider(new DataBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new DataItems(generator, event.getExistingFileHelper()));
            generator.addProvider(new DataLang(generator, "en_us"));
        }
    }
}
