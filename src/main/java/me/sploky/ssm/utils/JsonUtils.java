package me.sploky.ssm.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonUtils {
    public static <T> JsonElement gsonNullOrDefault(JsonElement element, T defaultValue) {
        if (element == null) {
            if (defaultValue instanceof Number) {
                return new JsonPrimitive((Number) defaultValue);
            } else if (defaultValue instanceof Boolean) {
                return new JsonPrimitive((Boolean) defaultValue);
            } else if (defaultValue instanceof String) {
                return new JsonPrimitive((String) defaultValue);
            } else if (defaultValue instanceof Character) {
                return new JsonPrimitive((Character) defaultValue);
            }
            return null;
        }

        return element;
    }

    public static <T> T nullOrDefault(Object element, T defaultValue) {
        if (element == null) {
            return defaultValue;
        }

        return (T) element;
    }

}
