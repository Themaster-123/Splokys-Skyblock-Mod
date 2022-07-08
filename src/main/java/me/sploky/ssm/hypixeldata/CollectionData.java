package me.sploky.ssm.hypixeldata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sploky.ssm.elements.ElementTextDecoder;

import java.util.HashMap;
import java.util.Map;

public class CollectionData implements HypixelData {
    private static HashMap<String, Collection> COLLECTIONS = new HashMap<>();

    @Override
    public void GetData() {
        JsonObject collections = HypixelUtils.SKYBLOCK_COLLECTION.getResponse().getAsJsonObject("collections");

        for (Map.Entry<String, JsonElement> entry : collections.entrySet()) {
            JsonObject collectionCategory = entry.getValue().getAsJsonObject();
            JsonObject items = collectionCategory.getAsJsonObject("items");
            for (Map.Entry<String, JsonElement> itemEntry : items.entrySet()) {
                JsonObject collection = itemEntry.getValue().getAsJsonObject();
                String name = collection.get("name").getAsString();
                Collection collectionObject = new Collection(name, collectionCategory.get("name").getAsString().toUpperCase(),
                        itemEntry.toString().split("=")[0]);
                COLLECTIONS.put(name, collectionObject);
                ElementTextDecoder.numberDecodeMap.put(name.toLowerCase() + " tier", () -> {return (float) COLLECTIONS.get(name).tier;});
                ElementTextDecoder.numberDecodeMap.put(name.toLowerCase() + " max tier", () -> {return (float) COLLECTIONS.get(name).maxTier;});
            }
        }
    }

    public static Collection getCollection(String name) {
        return COLLECTIONS.get(name);
    }
}
