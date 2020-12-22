package io.github.mosadie.ExponentialPower.datagen;

import io.github.mosadie.ExponentialPower.ExponentialPower;
import io.github.mosadie.ExponentialPower.setup.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class DataRecipes extends RecipeProvider {

    public DataRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ExponentialPower.LOGGER.info("Registering Recipes!");
        ShapedRecipeBuilder.shapedRecipe(Registration.ENDER_CELL.get())
                .patternLine("DED")
                .patternLine("EIE")
                .patternLine("DED")
                .key('D', Blocks.DIAMOND_BLOCK)
                .key('E', Blocks.EMERALD_BLOCK)
                .key('I', Items.ENDER_EYE)
                .setGroup("exponentialpower")
                .addCriterion("enderEye", InventoryChangeTrigger.Instance.forItems(Items.ENDER_EYE));

        ShapedRecipeBuilder.shapedRecipe(Registration.ENDER_GENERATOR.get())
                .patternLine("ioi")
                .patternLine("oco")
                .patternLine("ioi")
                .key('i', Blocks.IRON_BLOCK)
                .key('o', Tags.Items.OBSIDIAN)
                .key('c', Registration.ENDER_CELL.get())
                .setGroup("exponentialpower")
                .addCriterion("endercell", InventoryChangeTrigger.Instance.forItems(Registration.ENDER_CELL.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Registration.ADV_ENDER_GENERATOR.get())
                .patternLine("DGD")
                .patternLine("GcG")
                .patternLine("DGD")
                .key('D', Blocks.DIAMOND_BLOCK)
                .key('G', Registration.ENDER_GENERATOR.get())
                .key('c', Registration.ENDER_CELL.get())
                .setGroup("exponentialpower")
                .addCriterion("endergenerator", InventoryChangeTrigger.Instance.forItems(Registration.ENDER_GENERATOR.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(Registration.ENDER_STORAGE.get())
                .patternLine("DoD")
                .patternLine("oco")
                .patternLine("DoD")
                .key('D', Blocks.DIAMOND_BLOCK)
                .key('o', Tags.Items.OBSIDIAN)
                .key('c', Registration.ENDER_CELL.get())
                .setGroup("exponentialpower")
                .addCriterion("endercell", InventoryChangeTrigger.Instance.forItems(Registration.ENDER_CELL.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Registration.ADV_ENDER_STORAGE.get())
                .patternLine("DSD")
                .patternLine("ScS")
                .patternLine("DSD")
                .key('D', Blocks.DIAMOND_BLOCK)
                .key('S', Registration.ENDER_STORAGE.get())
                .key('c', Registration.ENDER_CELL.get())
                .setGroup("exponentialpower")
                .addCriterion("enderstorage", InventoryChangeTrigger.Instance.forItems(Registration.ENDER_STORAGE.get()))
                .build(consumer);
    }
}