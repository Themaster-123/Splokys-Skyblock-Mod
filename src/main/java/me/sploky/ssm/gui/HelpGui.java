package me.sploky.ssm.gui;

import me.sploky.ssm.gui.utils.Center;
import me.sploky.ssm.gui.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HelpGui extends GuiScreen {
    public static List<HelpCategory> HELP_CATEGORIES = new ArrayList<>();

    private static int CATEGORY_SIZE_X = 120;
    private static int CATEGORY_SIZE_Y = 230;
    private static int CATEGORY_PADDING = 10;
    private static int TEXT_PADDING = 5;
    private int categoryListX = 0;
    private int categoryListY = 0;
    private int categoryFullSizeX = CATEGORY_SIZE_X + CATEGORY_PADDING;
    private int categoryFullSizeY = CATEGORY_SIZE_Y + CATEGORY_PADDING;

    @Override
    public void initGui() {
        super.initGui();
        categoryListX = width / categoryFullSizeX;
        categoryListY = height / categoryFullSizeY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, SplokysGui.BACKGROUND_COLOR.getRGB());

        for (int i = 0; i < HELP_CATEGORIES.size(); i++) {

            HelpCategory helpCategory = HELP_CATEGORIES.get(i);
            int x = (i % categoryListX) * (categoryFullSizeX) + CATEGORY_PADDING;
            int y = i / categoryListX * (categoryFullSizeY) + CATEGORY_PADDING;
            RenderUtils.drawQuad(x, y, CATEGORY_SIZE_X, CATEGORY_SIZE_Y, Color.DARK_GRAY, Center.TOP_LEFT_CORNER);
            for (int j = 0; j < helpCategory.decodeText.size(); j++) {
                String text = helpCategory.decodeText.get(j);
                int textY = y + (j + 1) * (fontRendererObj.FONT_HEIGHT + TEXT_PADDING);
                RenderUtils.drawQuad(x, textY, CATEGORY_SIZE_X, fontRendererObj.FONT_HEIGHT, Color.GRAY, Center.TOP_LEFT_CORNER);
                RenderUtils.drawText(text, x, textY + fontRendererObj.FONT_HEIGHT / 2 + 1, CATEGORY_SIZE_X, Color.WHITE, true, Center.LEFT_CENTER);
            }

            RenderUtils.drawText(helpCategory.name, x + CATEGORY_SIZE_X / 2 , y, CATEGORY_SIZE_X, new Color(255, 170, 0), true, Center.TOP_CENTER);

        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }


}
