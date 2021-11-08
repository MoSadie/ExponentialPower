package io.github.mosadie.exponentialpower.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.setup.Registration;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
	    renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);

        getMinecraft().fontRenderer.drawString(matrixStack, I18n.format("screen.exponentialpower.generator_rate"), guiLeft+5, guiTop+53, TextFormatting.DARK_GRAY.getColor());
        getMinecraft().fontRenderer.drawString(matrixStack, te.energy + " RF/t", guiLeft+5, guiTop+63, TextFormatting.DARK_GRAY.getColor());
    }
}
