package io.github.mosadie.exponentialpower.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorTE;
import io.github.mosadie.exponentialpower.tiles.BaseClasses.GeneratorTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GUIEnderGeneratorTE extends AbstractContainerScreen<ContainerEnderGeneratorTE> {
	private final GeneratorTE te;

	//TODO update to MODID, loc form.
    //private final ResourceLocation GUI = new ResourceLocation(ExponentialPower.MODID, "textures/gui/containerendergeneratorte.png");
	private final ResourceLocation GUI = new ResourceLocation("exponentialpower:textures/gui/containerendergeneratorte.png");

	public GUIEnderGeneratorTE(ContainerEnderGeneratorTE container, Inventory playerInv, Component title) {
        super(container, playerInv, title);

        te = container.getTileEntity();

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int p_97809_, int p_97810_) {
        drawString(matrixStack, Minecraft.getInstance().font, new TranslatableComponent("screen.exponentialpower.generator_rate"), 10, 53, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, te.energy + " RF/t", 10, 63, 0xffffff);
//        getMinecraft().font.draw(matrixStack, new TranslatableComponent("screen.exponentialpower.generator_rate"), getGuiLeft()+5, getGuiTop()+53, 4210752);
//        getMinecraft().font.draw(matrixStack, te.energy + " RF/t", getGuiLeft()+5, getGuiTop()+63, 4210752);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
	    renderBackground(matrixStack);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
