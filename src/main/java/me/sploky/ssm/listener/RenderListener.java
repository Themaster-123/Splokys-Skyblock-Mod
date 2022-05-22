package me.sploky.ssm.listener;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.gui.SplokysGui;
import me.sploky.ssm.textures.GuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import scala.collection.parallel.ParIterableLike;

import java.awt.*;
import java.util.Queue;

public class RenderListener {
    private boolean openSplokyGui;
    private boolean depthEnabled;
    private boolean blendEnabled;
    private boolean alphaEnabled;
    private int blendFunctionSrcFactor;
    private int blendFunctionDstFactor;

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (openSplokyGui) {
            Minecraft.getMinecraft().displayGuiScreen(new SplokysGui());
            openSplokyGui = false;
        }
    }

    @SubscribeEvent
    public void onPostRenderGui(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        Minecraft minecraft = Minecraft.getMinecraft();
        if (!(minecraft.currentScreen instanceof SplokysGui)) {
            for (Element element : SplokysSkyblockMod.main.ELEMENTS) {
                drawText(element.getText(), (int) (element.positionX * event.resolution.getScaledWidth()),
                        (int) (element.positionY * event.resolution.getScaledHeight()), Color.BLUE, true);

            }
            drawText("tesdadadaa", event.resolution.getScaledWidth() / 2, event.resolution.getScaledHeight() /2, true);
        }
    }

    public void drawQuad(int x, int y, int width, int height, Color color) {
        Minecraft minecraft = Minecraft.getMinecraft();

        getCurrentGlOptions();

        Gui.drawRect(x, y, x + width, y + height, color.getRGB());

        resetGlOptions();
    }

    public void drawText(String text, int x, int y, boolean dropShadow) {
        getCurrentGlOptions();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        fontRenderer.drawString(text, x, y, color.getRGB(), dropShadow);

        resetGlOptions();
    }

    public void getCurrentGlOptions() {
        depthEnabled = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        blendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
        alphaEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        blendFunctionSrcFactor = GL11.glGetInteger(GL11.GL_BLEND_SRC);
        blendFunctionDstFactor = GL11.glGetInteger(GL11.GL_BLEND_DST);

        GlStateManager.color(1, 1, 1, 1);
    }

    public void resetGlOptions() {
        if (depthEnabled) {
            GlStateManager.enableDepth();
        } else {
            GlStateManager.disableDepth();
        }

        if (!alphaEnabled) {
            GlStateManager.disableAlpha();
        } else {
            GlStateManager.enableAlpha();
        }

        if (!blendEnabled) {
            GlStateManager.disableBlend();
        } else {
            GlStateManager.enableAlpha();
        }

        GlStateManager.blendFunc(blendFunctionSrcFactor, blendFunctionDstFactor);

        GlStateManager.color(1, 1, 1, 1);
    }

    public void openSplokyGui() {
        openSplokyGui = true;
    }
}
