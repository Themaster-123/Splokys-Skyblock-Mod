package me.sploky.ssm.listener;

import me.sploky.ssm.elements.Element;
import me.sploky.ssm.gui.utils.RenderUtils;
import me.sploky.ssm.gui.SplokysGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RenderListener {
    private GuiScreen nextGui;

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (nextGui != null) {
            Minecraft.getMinecraft().displayGuiScreen(nextGui);
            nextGui = null;
        }
    }

    @SubscribeEvent
    public void onPostRenderGui(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL || (Minecraft.getMinecraft().currentScreen instanceof SplokysGui)) return;
        RenderUtils.renderElements(Element.DEFAULT_COLOR);
    }


    public void openGui(GuiScreen guiScreen) {
        nextGui = guiScreen;
    }
}
