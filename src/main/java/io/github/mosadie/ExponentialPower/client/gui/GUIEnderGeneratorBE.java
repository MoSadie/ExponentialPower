package io.github.mosadie.exponentialpower.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GUIEnderGeneratorBE extends AbstractContainerScreen<ContainerEnderGeneratorBE> {
	private final GeneratorBE be;

    private final ResourceLocation GUI = new ResourceLocation(ExponentialPower.MODID, "textures/gui/containerendergeneratorbe.png");

	public GUIEnderGeneratorBE(ContainerEnderGeneratorBE container, Inventory playerInv, Component title) {
        super(container, playerInv, title);

        be = container.getBlockEntity();

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int p_97809_, int p_97810_) {
        super.renderLabels(matrixStack, p_97809_, p_97810_);
        drawString(matrixStack, Minecraft.getInstance().font, Component.translatable("screen.exponentialpower.generator_rate"), 10, 53, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, be.energy + " RF/t", 10, 63, 0xffffff);
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
