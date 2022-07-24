package me.sploky.ssm.elements;

import com.google.gson.JsonArray;
import me.sploky.ssm.configs.Config;
import me.sploky.ssm.utils.JsonUtils;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;

public final class ElementData {
    public static HashSet<Element> ELEMENTS = new HashSet<>();
    public static Config ELEMENT_DATA_CONFIG = new Config("Elements");
    public static JSONArray ELEMENT_ARRAY;

    public static void loadSavedData() {
        ELEMENT_DATA_CONFIG.Load();
        ELEMENT_ARRAY = JsonUtils.nullOrDefault(ELEMENT_DATA_CONFIG.jsonObject.get("elements"), new JSONArray() );
        for (Object o : ELEMENT_ARRAY) {
            JSONObject elementObject = (JSONObject) o;
            Element element = Element.fromJsonObject(elementObject);
            ELEMENTS.add(element);
        }
    }

    @SuppressWarnings("unchecked")
    public static void saveData() {
        ELEMENT_DATA_CONFIG.jsonObject.clear();
        ELEMENT_ARRAY = new JSONArray();
        for (Element element : ELEMENTS) {
            ElementData.ELEMENT_ARRAY.add(Element.toJsonObject(element));
        }

        ELEMENT_DATA_CONFIG.jsonObject.put("elements", ELEMENT_ARRAY);
        ELEMENT_DATA_CONFIG.save();
    }

}
