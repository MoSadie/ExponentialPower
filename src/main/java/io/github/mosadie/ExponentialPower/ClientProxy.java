package io.github.mosadie.ExponentialPower;

import io.github.mosadie.ExponentialPower.Blocks.BlockManager;
import io.github.mosadie.ExponentialPower.Items.ItemManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModelLoader.setCustomModelResourceLocation(ItemManager.enderCell, 0, new ModelResourceLocation(ItemManager.enderCell.getRegistryName(), "inventory"));
        
        Item block = Item.getItemFromBlock(BlockManager.enderGenerator);
        ModelResourceLocation mre = new ModelResourceLocation("exponentialpower:endergenerator");
        if (block != null && mre != null) ModelLoader.setCustomModelResourceLocation(block,0,mre);
        
        block = Item.getItemFromBlock(BlockManager.enderStorage);
        mre = new ModelResourceLocation("exponentialpower:enderstorage");
        if (block != null && mre != null) ModelLoader.setCustomModelResourceLocation(block,0,mre);
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
