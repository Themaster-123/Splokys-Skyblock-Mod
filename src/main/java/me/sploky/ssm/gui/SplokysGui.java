package me.sploky.ssm.gui;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.gui.buttons.ElementBaseButton;
import me.sploky.ssm.gui.buttons.ElementCornerButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;


public class SplokysGui extends GuiScreen {
    protected boolean isHoldingCorner = false;
    protected ElementCornerButton currentlyHeldCorner = null;

    public SplokysGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();
        for (Element element : SplokysSkyblockMod.main.elements) {
            buttonList.add(new ElementBaseButton(element));
            buttonList.add(new ElementCornerButton(element, 0));
            buttonList.add(new ElementCornerButton(element, 1));
            buttonList.add(new ElementCornerButton(element, 2));
            buttonList.add(new ElementCornerButton(element, 3));

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, new Color(0, 0, 0, 86).getRGB());
        RenderUtils.renderElements(Color.GRAY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button instanceof ElementCornerButton) {
            ElementCornerButton elementCornerButton = (ElementCornerButton)button;
            isHoldingCorner = true;
            currentlyHeldCorner = elementCornerButton;
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if (isHoldingCorner) {
            moveCorner(mouseX, mouseY, currentlyHeldCorner);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (isHoldingCorner) {
            isHoldingCorner = false;
            currentlyHeldCorner = null;
        }
    }

    protected void moveCorner(int x, int y, ElementCornerButton button) {
        Element element = button.element;
        int diffX = Math.abs(x - element.getScreenPositionX());
        element.sizeX = diffX * 2;
        int diffY = Math.abs(y - element.getScreenPositionY());
        element.sizeY = diffY * 2;
    }
}
