package io.github.mosadie.ExponentialPower.datagen;

import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataItems extends ItemModelProvider {

    public DataItems(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExponentialPower.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(Registration.ENDER_CELL.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"), new ResourceLocation(ExponentialPower.MODID, "item/endercell"));
        withExistingParent(Registration.ENDER_GENERATOR_ITEM.get().getRegistryName().getPath(), new ResourceLocation(ExponentialPower.MODID, "block/endergenerator"));
    }
}