package me.sploky.ssm.gui;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Tuple;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.function.Consumer;

public class RenderUtils {
    private static boolean depthEnabled;
    private static boolean blendEnabled;
    private static boolean alphaEnabled;
    private static int blendFunctionSrcFactor;
    private static int blendFunctionDstFactor;

    public static void renderElements(Color baseColor, Consumer<Element> onRenderElement) {

        for (Element element : SplokysSkyblockMod.main.elements) {
            element.render(baseColor);
            onRenderElement.accept(element);
        }
    }

    public static void renderElements(Color baseColor) {
        renderElements(baseColor, element -> {

        });
    }

    public static  void drawQuad(int x, int y, int width, int height, Color color, Center centerType) {
        getCurrentGlOptions();

        Tuple<Integer, Integer> coords = centerType.pivotCoords(x, y, width, height);
        x = coords.getFirst();
        y = coords.getSecond();

        Gui.drawRect(x, y, x + width, y + height, color.getRGB());

        resetGlOptions();
    }

    public static  void drawText(String text, int x, int y, int maxSize, int minSize, Color color, boolean dropShadow, Center centerType) {
        getCurrentGlOptions();

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        GlStateManager.pushMatrix();

        int textScale = Math.max(fontRenderer.getStringWidth(text), minSize);

        if (maxSize != -1) {
            ScaledResolution currentResolution = new ScaledResolution(Minecraft.getMinecraft());
            float changedAmount = (textScale != 0 ? 1f / textScale : 0) * maxSize;
            float widthOffset = 0;
            float heightOffset = 0;

            Tuple<Integer, Integer> coords = centerType.pivotCoords(x, y, textScale * changedAmount, fontRenderer.FONT_HEIGHT * changedAmount);
            x = coords.getFirst();
            y = coords.getSecond();

            x = Math.round((x + widthOffset) * currentResolution.getScaleFactor()) / currentResolution.getScaleFactor();
            x /= changedAmount;
            y = Math.round((y + heightOffset) * currentResolution.getScaleFactor()) / currentResolution.getScaleFactor();
            y /= changedAmount;
            GlStateManager.scale(changedAmount, changedAmount, 1);
        } else {
            Tuple<Integer, Integer> coords = centerType.pivotCoords(x, y, textScale, fontRenderer.FONT_HEIGHT);
            x = coords.getFirst();
            y = coords.getSecond();
        }

        fontRenderer.drawString(text, x, y, color.getRGB(), dropShadow);
        resetGlOptions();
        GlStateManager.popMatrix();
        //GlStateManager.scale(1, 1, 1);
    }

    public static  void drawText(String text, int x, int y, Color color, boolean dropShadow, Center centerType) {
        drawText(text, x, y, -1, 0, color, dropShadow, centerType);
    }

    public static  void getCurrentGlOptions() {
        depthEnabled = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        blendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
        alphaEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        blendFunctionSrcFactor = GL11.glGetInteger(GL11.GL_BLEND_SRC);
        blendFunctionDstFactor = GL11.glGetInteger(GL11.GL_BLEND_DST);

        GlStateManager.color(1, 1, 1, 1);
    }

    public static  void resetGlOptions() {
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

}
