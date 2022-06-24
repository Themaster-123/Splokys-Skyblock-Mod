package me.sploky.ssm.gui;

import me.sploky.ssm.elements.Element;
import me.sploky.ssm.elements.ElementData;
import me.sploky.ssm.gui.buttons.ElementBaseButton;
import me.sploky.ssm.gui.buttons.ElementButton;
import me.sploky.ssm.gui.buttons.ElementCornerButton;
import me.sploky.ssm.gui.utils.Center;
import me.sploky.ssm.gui.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.json.simple.JSONObject;
import org.lwjgl.input.Mouse;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SplokysGui extends GuiScreen {
    public static int M2_MENU_SIZE_X = 100;
    public static int M2_MENU_SIZE_Y = 30;

    protected boolean isHoldingCorner = false;
    protected Element currentlyHeldElement = null;
    protected boolean canMoveElement = false;
    protected Vector2f elementOffset;
    protected List<GuiButton> elementButtons = new ArrayList<>();
    protected int m2MenuX = 0;
    protected int m2MenuY = 0;
    protected List<GuiButton> m2MenuButtons = new ArrayList<>();
    protected Element m2MenuElement = null;



    public SplokysGui() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();
        createButtons();
        clearRightClickMenu();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, new Color(0, 0, 0, 86).getRGB());
        RenderUtils.renderElements(Element.GUI_COLOR);

        RenderUtils.drawQuad(m2MenuX, m2MenuY, M2_MENU_SIZE_X, M2_MENU_SIZE_Y, Color.DARK_GRAY.darker(), Center.TOP_LEFT_CORNER);



        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button instanceof ElementCornerButton) {
            ElementCornerButton elementCornerButton = (ElementCornerButton)button;
            isHoldingCorner = true;
            currentlyHeldElement = elementCornerButton.element;
        } else if (button instanceof ElementBaseButton) {
            ElementBaseButton elementBaseButton = (ElementBaseButton)button;
            currentlyHeldElement = elementBaseButton.element;
            canMoveElement = true;

            Element element = elementBaseButton.element;
            int mouseX = Mouse.getX() * this.width / this.mc.displayWidth;
            int mouseY = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;

            elementOffset = new Vector2f(element.getScreenPositionX() - mouseX, element.getScreenPositionY() - mouseY);
        } else if (button.id == 1) {
            createElement();
        } else if (button.id == 2) {
           Minecraft.getMinecraft().displayGuiScreen((new ElementEditGui(m2MenuElement)));
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if (isHoldingCorner) {
            moveCorner(mouseX, mouseY, currentlyHeldElement);
        } else if (canMoveElement) {
            currentlyHeldElement.setScreenPositionX(mouseX + (int)elementOffset.x);
            currentlyHeldElement.setScreenPositionY(mouseY+ (int)elementOffset.y);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1) {
            Minecraft minecraft = Minecraft.getMinecraft();

            for (GuiButton guiButton : this.buttonList) {

                if (guiButton.mousePressed(minecraft, mouseX, mouseY)) {
                    if (guiButton.id == 0 && guiButton instanceof ElementButton) {
                        m2MenuElement = ((ElementButton)guiButton).getElement();
                        createRightClickMenu(mouseX, mouseY, true);
                        return;
                    }
                }
            }

            createRightClickMenu(mouseX, mouseY, false);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (isHoldingCorner || canMoveElement) {
            isHoldingCorner = false;
            currentlyHeldElement = null;
            canMoveElement = false;
        }
    }


    protected void moveCorner(int x, int y, Element element) {
        int diffX = Math.abs(x - element.getScreenPositionX());
        element.sizeX = diffX * 2;
        int diffY = Math.abs(y - element.getScreenPositionY());
        element.sizeY = diffY * 2;
    }

    protected void createButtons() {
        elementButtons.clear();

        for (Element element : ElementData.ELEMENTS) {
            elementButtons.add(new ElementCornerButton(element, 0));
            elementButtons.add(new ElementCornerButton(element, 1));
            elementButtons.add(new ElementCornerButton(element, 2));
            elementButtons.add(new ElementCornerButton(element, 3));
            elementButtons.add(new ElementBaseButton(element));

        }

        mergeButtons();
    }

    protected void createRightClickMenu(int x, int y, boolean editElement) {
        clearRightClickMenu();

        m2MenuX = x;
        m2MenuY = y;
        int buttonSize = (int) (M2_MENU_SIZE_X * .9);
        m2MenuButtons.add(new GuiButton(editElement ? 2 : 1, x + (M2_MENU_SIZE_X - buttonSize) / 2,
                y + (M2_MENU_SIZE_X - buttonSize) / 2, buttonSize, 20, editElement ? "Edit Element" : "Create Element"));

        mergeButtons();
    }

    protected void clearRightClickMenu() {
        m2MenuX = -99999;
        m2MenuY = -99999;
        m2MenuButtons.clear();
        mergeButtons();
    }

    protected void mergeButtons() {
        buttonList.clear();
        buttonList.addAll(elementButtons);
        buttonList.addAll(m2MenuButtons);
    }

    protected void createElement() {
        Element element = new Element(m2MenuX, m2MenuY, 50, 50);
        ElementData.ELEMENTS.add(element);
        clearRightClickMenu();
        createButtons();
    }
}
