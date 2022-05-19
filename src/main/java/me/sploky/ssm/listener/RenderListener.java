package me.sploky.ssm.listener;

import me.sploky.ssm.gui.SplokysGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RenderListener {
    private boolean openSplokyGui;

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (openSplokyGui) {
            Minecraft.getMinecraft().displayGuiScreen(new SplokysGui());
            openSplokyGui = false;
        }
    }

    public void openSplokyGui() {
        openSplokyGui = true;
    }
}
