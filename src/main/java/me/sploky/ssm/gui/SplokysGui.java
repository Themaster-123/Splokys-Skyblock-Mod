package me.sploky.ssm.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.Sys;

import java.awt.*;

public class SplokysGui extends GuiScreen {

    public SplokysGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawRect(0, 0, width, height, new Color(0, 0, 0, 86).getRGB());
        System.out.println("tesaasada");
    }
}
