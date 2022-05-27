package me.sploky.ssm.gui.buttons;

import me.sploky.ssm.elements.Element;
import me.sploky.ssm.gui.Center;
import me.sploky.ssm.gui.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class ElementCornerButton extends GuiButton {
    public static final int CORNER_BUTTON_SIZE = 6;

    public Element element;

    protected int corner;

    public ElementCornerButton(Element element, int corner) {
        super(0, 0, 0, CORNER_BUTTON_SIZE, CORNER_BUTTON_SIZE, "");
        this.element = element;
        this.corner = corner;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        switch (corner) {
            case 0:
                xPosition = element.getScreenPositionX() - element.sizeX / 2;
                yPosition = element.getScreenPositionY() - element.sizeY / 2;
                break;

            case 1:
                xPosition = element.getScreenPositionX() + element.sizeX / 2;
                yPosition = element.getScreenPositionY() - element.sizeY / 2;
                break;

            case 2:
                xPosition = element.getScreenPositionX() - element.sizeX / 2;
                yPosition = element.getScreenPositionY() + element.sizeY / 2;
                break;

            case 3:
                xPosition = element.getScreenPositionX() + element.sizeX / 2;
                yPosition = element.getScreenPositionY() + element.sizeY / 2;
                break;
        }

        xPosition -= width / 2;
        yPosition -= height / 2;

        RenderUtils.drawQuad(xPosition, yPosition, CORNER_BUTTON_SIZE, CORNER_BUTTON_SIZE, Color.WHITE, Center.TOP_LEFT_CORNER);
    }


}
