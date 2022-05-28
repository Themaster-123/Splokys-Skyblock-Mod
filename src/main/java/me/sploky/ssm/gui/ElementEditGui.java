package me.sploky.ssm.gui;

import me.sploky.ssm.elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

public class ElementEditGui extends GuiScreen {
    public final Element element;
    public String elementText;

    protected final Element trueElement;

    public ElementEditGui(Element element) {
        trueElement = element;
        this.element = element.clone();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, new Color(0, 0, 0, 86).getRGB());

        element.render(Element.GUI_COLOR);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        element.sizeX = 100;
        element.sizeY = 70;

        element.elementText = elementText;
        element.setScreenPositionX(width / 2);
        element.setScreenPositionY((int) (element.sizeY / 1.5));

        buttonList.add(new GuiButton(0, width / 2 - 200 / 2, height / 2 - 30, "Done"));
        buttonList.add(new GuiButton(1, width / 2 - 200 / 2, height / 2, "Edit Text"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            trueElement.elementText = elementText;
            Minecraft.getMinecraft().displayGuiScreen(new SplokysGui());
        } else if (button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(new TextEditGui(this));
        }
    }
}
