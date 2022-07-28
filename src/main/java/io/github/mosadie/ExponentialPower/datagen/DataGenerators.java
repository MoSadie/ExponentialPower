package io.github.mosadie.exponentialpower.datagen;

import io.github.mosadie.exponentialpower.ExponentialPower;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExponentialPower.LOGGER.info("Registering Data Providers!");

        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            ExponentialPower.LOGGER.info("Registering Server Providers!");
            generator.addProvider(true, new DataRecipes(generator));
            generator.addProvider(true, new DataLootTables(generator));
        }
        if (event.includeClient()) {
            ExponentialPower.LOGGER.info("Registering Client Providers!");
            generator.addProvider(true, new DataBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(true, new DataItems(generator, event.getExistingFileHelper()));
            generator.addProvider(true, new DataLang(generator, "en_us"));
        }
    }
}
