package io.github.mosadie.ExponentialPower.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.mosadie.ExponentialPower.GUIContainer.ContainerEnderGeneratorTE;
import io.github.mosadie.ExponentialPower.TileEntitys.BaseClasses.GeneratorTE;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GUIEnderGeneratorTE extends ContainerScreen<ContainerEnderGeneratorTE> {
	private final GeneratorTE te;

	//TODO update to MODID, loc form.
	private final ResourceLocation GUI = new ResourceLocation("exponentialpower:textures/gui/containerendergeneratorte.png");

	public GUIEnderGeneratorTE(ContainerEnderGeneratorTE container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);

        te = container.getTileEntity();
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
        drawString(matrixStack, getMinecraft().fontRenderer, "Current Energy Stored:", guiLeft+5, guiTop+60, 1);
        drawString(matrixStack, getMinecraft().fontRenderer, te.energy + " RF", guiLeft+5, guiTop+70, 1);
    }

    private static ITextComponent getTitle(GeneratorTE te) {
	    if (te.hasCustomName()) return te.getCustomName();
	    if (te.tier == GeneratorTE.GeneratorTier.ADVANCED)
	        return new TranslationTextComponent("screen.exponentialpower.title.advanced");
	    else
	        return new TranslationTextComponent("screen.exponentialpower.title.regular");
    }
}
