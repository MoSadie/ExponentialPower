package io.github.mosadie.ExponentialPower;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy{
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModelLoader.setCustomModelResourceLocation(ItemManager.enderCell, 0, new ModelResourceLocation(ItemManager.enderCell.getRegistryName(), "inventory"));
        Item block = Item.getItemFromBlock(BlockManager.enderGenerator);
        ModelResourceLocation mre = new ModelResourceLocation("exponentialpower:endergenerator");
        if (block != null && mre != null) ModelLoader.setCustomModelResourceLocation(block,0,mre);
        else if (block == null && mre == null) System.out.println("Both are Null. BANANAS");
        else if (block == null) System.out.println("block is Null. BANANAS");
        else if (mre == null) System.out.println("mre is Null. BANANAS");
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
