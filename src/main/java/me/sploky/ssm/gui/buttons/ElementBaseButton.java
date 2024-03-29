package me.sploky.ssm.gui.buttons;

import me.sploky.ssm.elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ElementBaseButton extends GuiButton implements ElementButton {
    public Element element;

    public ElementBaseButton(Element element) {
        super(0, 0, 0, 0, 0, "");
        this.element = element;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        resizeButton();
    }

    protected void resizeButton() {
        xPosition = element.getScreenPositionX() - element.sizeX / 2;
        yPosition = element.getScreenPositionY() - element.sizeY / 2;
        width = element.sizeX;
        height = element.sizeY;
    }

    @Override
    public Element getElement() {
        return element;
    }
}
