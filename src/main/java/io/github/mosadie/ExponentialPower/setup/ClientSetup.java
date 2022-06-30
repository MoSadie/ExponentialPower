package io.github.mosadie.exponentialpower.setup;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.client.gui.GUIEnderGeneratorBE;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExponentialPower.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.ENDER_GENERATOR_CONTAINER.get(), GUIEnderGeneratorBE::new);           // Attach our container to the screen
        });
    }
}
