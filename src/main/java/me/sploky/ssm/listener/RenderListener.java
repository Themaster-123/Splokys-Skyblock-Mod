package me.sploky.ssm.listener;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.gui.Center;
import me.sploky.ssm.gui.RenderUtils;
import me.sploky.ssm.gui.SplokysGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderListener {
    private boolean openSplokyGui;


    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (openSplokyGui) {
            Minecraft.getMinecraft().displayGuiScreen(new SplokysGui());
            openSplokyGui = false;
        }
    }

    @SubscribeEvent
    public void onPostRenderGui(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL || (Minecraft.getMinecraft().currentScreen instanceof SplokysGui)) return;
        RenderUtils.renderElements(new Color(0, 0, 0, 82));
    }


    public void openSplokyGui() {
        openSplokyGui = true;
    }
}
