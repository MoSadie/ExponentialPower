package io.github.mosadie.exponentialpower.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GUIEnderGeneratorTE extends AbstractContainerScreen<ContainerEnderGeneratorTE> {
	private final GeneratorTE te;

	//TODO update to MODID, loc form.
    private final ResourceLocation GUI = new ResourceLocation(ExponentialPower.MODID, "textures/gui/containerendergeneratorte");
//	private final ResourceLocation GUI = new ResourceLocation("exponentialpower:textures/gui/containerendergeneratorte.png");

	public GUIEnderGeneratorTE(ContainerEnderGeneratorTE container, Inventory playerInv, Component title) {
        super(container, playerInv, title);

        te = container.getTileEntity();

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        //renderComponentHoverEffect(matrixStack, Style.EMPTY, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
	    renderBackground(matrixStack);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        getMinecraft().font.draw(matrixStack, new TranslatableComponent("screen.exponentialpower.generator_rate"), getGuiLeft()+5, getGuiTop()+53, 4210752);
        getMinecraft().font.draw(matrixStack, te.energy + " RF/t", getGuiLeft()+5, getGuiTop()+63, 4210752);
    }
}
