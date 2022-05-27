package me.sploky.ssm.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class Element {
    public float positionX;

    public float positionY;

    public int sizeX;

    public int sizeY;

    public Color textColor;

    public Element(float positionX, float positionY, int sizeX, int sizeY, Color textColor) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textColor = textColor;
    }

    public Element(int positionX, int positionY, int sizeX, int sizeY, Color textColor) {
        setScreenPositionX(positionX);
        setScreenPositionY(positionY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.textColor = textColor;
    }

    public Element(float positionX, float positionY, int sizeX, int sizeY) {
        this(positionX, positionY, sizeX, sizeY, new Color(217, 163, 52, 255));
    }

    public Element(int positionX, int positionY, int sizeX, int sizeY) {
        this(positionX, positionY, sizeX, sizeY, new Color(217, 163, 52, 255));
    }


    public String getText() {
        StringBuilder test = new StringBuilder();
        long time = Minecraft.getMinecraft().theWorld.getTotalWorldTime() / 10;
        Random random = new Random(time);

        int length = (int) time % 10;
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                test.append((char)(random.nextInt(26) + 65));
                continue;
            }
            test.append((char)(random.nextInt(26) + 97));
        }
        return test.toString();
    }

    public int getScreenPositionX() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        return (int) (positionX * scaledResolution.getScaledWidth());
    }

    public int getScreenPositionY() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        return (int) (positionY * scaledResolution.getScaledHeight());
    }

    public void setScreenPositionX(int x) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.positionX = (float)x / scaledResolution.getScaledWidth();
    }

    public void setScreenPositionY(int y) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.positionY = (float)y / scaledResolution.getScaledHeight();
    }
}
