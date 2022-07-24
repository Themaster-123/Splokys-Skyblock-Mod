package me.sploky.ssm.gui;

import me.sploky.ssm.gui.utils.Center;
import me.sploky.ssm.gui.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;
import java.io.IOException;

public class TextEditGui extends GuiScreen {
    protected ElementEditGui elementEditGui;
    protected GuiTextField textField;

    public TextEditGui(ElementEditGui elementEditGui) {
        this.elementEditGui = elementEditGui;
    }

    @Override
    public void initGui() {
        super.initGui();

        textField = new GuiTextField(0, fontRendererObj, width / 2 - 200 / 2, height / 2 + 20 / 2, 200, 20);
        textField.setMaxStringLength(100);
        textField.setEnableBackgroundDrawing(false);
        textField.setFocused(true);
        textField.setText(elementEditGui.element.elementText);
        textField.setCanLoseFocus(false);

        buttonList.add(new GuiButton(1, width / 2 - 200 / 2, height / 2 + 20 / 2 + 40 / 2, "Done"));
    }

    @Override
    public void updateScreen() {

        textField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, SplokysGui.BACKGROUND_COLOR.getRGB());

        RenderUtils.drawText("Edit Text", width / 2, height / 2 + 20 / 2 - 35, Color.WHITE, true, Center.CENTER);

        RenderUtils.drawQuad(width / 2 - 200 / 2, height / 2 + 20 / 2, 200, 20, Color.DARK_GRAY, Center.LEFT_CENTER);
        textField.drawTextBox();


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void setText(String newChatText, boolean shouldOverwrite) {
        super.setText(newChatText, shouldOverwrite);

        if (shouldOverwrite) {
            textField.setText(newChatText);
        } else {
            textField.writeText(newChatText);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 1) {
            elementEditGui.elementText = textField.getText().trim();
            Minecraft.getMinecraft().displayGuiScreen(elementEditGui);
        }
    }
}
