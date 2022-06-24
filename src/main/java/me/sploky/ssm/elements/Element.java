package me.sploky.ssm.elements;

import me.sploky.ssm.gui.utils.Center;
import me.sploky.ssm.gui.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.json.simple.JSONObject;
import scala.util.parsing.json.JSON;

import java.awt.*;

public class Element implements Cloneable {
    public static final Color DEFAULT_COLOR = new Color(0, 0, 0, 82);
    public static final Color GUI_COLOR = Color.GRAY;

    public float positionX;

    public float positionY;

    public int sizeX;

    public int sizeY;

    public Color textColor;

    public String elementText = "";

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
        this(positionX, positionY, sizeX, sizeY, new Color(255, 170, 0));
    }

    public Element(int positionX, int positionY, int sizeX, int sizeY) {
        this(positionX, positionY, sizeX, sizeY, new Color(255, 170, 0));
    }


    public String getTrueText() {
        return ElementTextDecoder.decodeText(elementText);
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

    public void render(Color baseColor) {
        RenderUtils.drawQuad(getScreenPositionX(), getScreenPositionY(),
                sizeX, sizeY, baseColor, Center.CENTER);

        RenderUtils.drawText(getTrueText(), getScreenPositionX(),
                getScreenPositionY() + sizeY / 2, sizeX, sizeX, textColor, true, Center.BOTTOM_CENTER);

    }

    @SuppressWarnings("unchecked")
    public static JSONObject toJsonObject(Element element) {
        JSONObject elementObject = new JSONObject();
        elementObject.put("text", element.elementText);
        elementObject.put("posX", element.positionX);
        elementObject.put("posY", element.positionY);
        elementObject.put("sizeX", element.sizeX);
        elementObject.put("sizeY", element.sizeY);
        elementObject.put("colorR", element.textColor.getRed());
        elementObject.put("colorG", element.textColor.getGreen());
        elementObject.put("colorB", element.textColor.getBlue());

        return elementObject;
    }

    public static Element fromJsonObject(JSONObject jsonObject) {
        Element element = new Element((float)(double)jsonObject.get("posX"),
                (float)(double)jsonObject.get("posY"),
                (int)(long)jsonObject.get("sizeX"),
                (int)(long)jsonObject.get("sizeY"),
        new Color((int)(long)jsonObject.get("colorR"), (int)(long)jsonObject.get("colorG"), (int)(long)jsonObject.get("colorB")));
        element.elementText = (String) jsonObject.get("text");
        return element;
    }

    @Override
    public Element clone() {
        try {


            return (Element) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
