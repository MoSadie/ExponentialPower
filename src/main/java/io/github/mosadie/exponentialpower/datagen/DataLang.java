package io.github.mosadie.exponentialpower.datagen;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class DataLang extends LanguageProvider {

    public DataLang(DataGenerator gen, String locale) {
        super(gen, ExponentialPower.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(Registration.ENDER_CELL, "Ender Cell");

        addBlock(Registration.ENDER_GENERATOR, "Ender Generator");
        addBlock(Registration.ADV_ENDER_GENERATOR, "Advanced Ender Generator");

        addBlock(Registration.ENDER_STORAGE, "Ender Storage");
        addBlock(Registration.ADV_ENDER_STORAGE, "Advanced Ender Storage");

        add("itemGroup.exponentialpower", "Exponential Power");

        add("screen.exponentialpower.generator_rate", "Current Energy Generation:");
        add("screen.exponentialpower.storage_total", "Current Energy Stored:");

    }
}
