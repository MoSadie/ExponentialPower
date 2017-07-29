package io.github.mosadie.ExponentialPower.client.gui;

import io.github.mosadie.ExponentialPower.GUIContainer.ContainerEnderGeneratorMk2TE;
import io.github.mosadie.ExponentialPower.TileEntitys.EnderGeneratorMk2TE;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIEnderGeneratorMk2TE extends GuiContainer {
	private EnderGeneratorMk2TE te;
	ResourceLocation rl = new ResourceLocation("exponentialpower:textures/gui/containerendergeneratorte.png");
	public GUIEnderGeneratorMk2TE(IInventory playerInv, EnderGeneratorMk2TE te) {
        super(new ContainerEnderGeneratorMk2TE(playerInv, te));
        
        this.te = te;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	this.mc.getTextureManager().bindTexture(rl);
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    	this.fontRendererObj.drawString("Current Energy Stored:", guiLeft+5, guiTop+60, 1, false);
    	this.fontRendererObj.drawString(te.energy + " RF", guiLeft+5, guiTop+70, 1, false);
    }
}
