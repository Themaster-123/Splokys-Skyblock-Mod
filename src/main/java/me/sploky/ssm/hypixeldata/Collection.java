package me.sploky.ssm.hypixeldata;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Collection {
    public String name;
    public String collectionType;
    public String internalName;
    public int maxTier = 0;
    public int tier = 0;

    private final JsonArray tiersArray;
    private final JsonObject collection;

    public Collection(String name, String collectionType, String internalName) {
        this.name = name;
        this.collectionType = collectionType;
        this.internalName = internalName;
        System.out.println(internalName + " " + collectionType + " " + name);

        collection = getCollection();
        System.out.println(collection.hashCode());
        tiersArray = getTiersArray();
        getTierStats();
    }

    private void getTierStats() {
        JsonElement collection = HypixelUtils.CURRENT_SKYBLOCK_PLAYER.getAsJsonObject("collection").get(internalName);
        int collectionAmount = collection != null ? collection.getAsInt() : 0;
        int totalAmountReq = 0;

        this.tier = 0;

        for (int i = 0; i < tiersArray.size(); i++) {
            JsonObject tierObject = tiersArray.get(i).getAsJsonObject();
            int tier = tierObject.get("tier").getAsInt();
            int amountRequired = tierObject.get("amountRequired").getAsInt();
            totalAmountReq += amountRequired;
            if (collectionAmount >= totalAmountReq) {
                this.tier = tier;
            }
        }

        this.maxTier = this.collection.get("maxTiers").getAsInt();
    }

    private JsonArray getTiersArray() {
        return collection.
                getAsJsonArray("tiers");
    }

    private JsonObject getCollection() {
        return HypixelUtils.SKYBLOCK_COLLECTION.getResponse().
                getAsJsonObject("collections").
                getAsJsonObject(collectionType).getAsJsonObject("items").getAsJsonObject(internalName);
    }
}
