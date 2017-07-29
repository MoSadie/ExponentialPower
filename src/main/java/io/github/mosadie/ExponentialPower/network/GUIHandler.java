package io.github.mosadie.ExponentialPower.network;

import io.github.mosadie.ExponentialPower.GUIContainer.ContainerEnderGeneratorMk2TE;
import io.github.mosadie.ExponentialPower.GUIContainer.ContainerEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorMk2TE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorTE;
import io.github.mosadie.ExponentialPower.client.gui.GUIEnderGeneratorMk2TE;
import io.github.mosadie.ExponentialPower.client.gui.GUIEnderGeneratorTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	public static final int MOD_TILE_ENTITY_GUI = 0;
    public static final int MOD_TILE_ENTITY_GUI_MK2 = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	if (ID == MOD_TILE_ENTITY_GUI)
        	return new ContainerEnderGeneratorTE(player.inventory, (EnderGeneratorTE) world.getTileEntity(new BlockPos(x, y, z)));

    	if (ID == MOD_TILE_ENTITY_GUI_MK2)
        	return new ContainerEnderGeneratorMk2TE(player.inventory, (EnderGeneratorMk2TE) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MOD_TILE_ENTITY_GUI)
        	return new GUIEnderGeneratorTE(player.inventory, (EnderGeneratorTE) world.getTileEntity(new BlockPos(x, y, z)));

    	if (ID == MOD_TILE_ENTITY_GUI_MK2)
        	return new GUIEnderGeneratorMk2TE(player.inventory, (EnderGeneratorMk2TE) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }
}