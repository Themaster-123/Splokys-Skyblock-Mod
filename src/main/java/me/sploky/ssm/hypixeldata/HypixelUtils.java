package me.sploky.ssm.hypixeldata;

import com.google.gson.*;
import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.configs.Config;
import me.sploky.ssm.elements.ElementTextDecoder;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.ResourceReply;
import net.hypixel.api.reply.skyblock.SkyBlockProfilesReply;
import net.hypixel.api.util.ResourceType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public final class HypixelUtils {
    private static final int DATA_AMOUNT = 3;
    private static UUID API_KEY = null;
    private static int dataHeld = 0;
    public static HypixelAPI API;

    public static String HYPIXEL_DATA_CATEGORY = "Hypixel Data";

    public static Config HYPIXEL_DATA_CONFIG = new Config("Hypixel_Data");

    public static SkyBlockProfilesReply SKYBLOCK_PROFILES;
    public static ResourceReply SKYBLOCK_SKILLS;
    public static ResourceReply SKYBLOCK_COLLECTION;

    public static JsonObject CURRENT_SKYBLOCK_PROFILE;
    public static JsonObject CURRENT_SKYBLOCK_PLAYER;

    public static String CURRENT_SKYBLOCK_PROFILE_NAME = "";

    public static UUID getApiKey() {
        return API_KEY;
    }

    @SuppressWarnings("unchecked")
    public static void setApiKey(UUID apiKey) {
        API_KEY = apiKey;
        API = new HypixelAPI(new ApacheHttpClient(apiKey));
        HYPIXEL_DATA_CONFIG.jsonObject.put("ApiKey", apiKey.toString());
        HYPIXEL_DATA_CONFIG.save();

        fetchData();
    }

    public static void fetchData() {
        dataHeld = 0;

        ElementTextDecoder.numberDecodeMap.clear();


        if (API == null || CURRENT_SKYBLOCK_PROFILE_NAME.equals("")) return;

        IThreadListener mainThread = FMLCommonHandler.instance().getWorldThread(Minecraft.getMinecraft().getNetHandler());

        API.getResource(ResourceType.SKYBLOCK_SKILLS).thenAccept(resourceReply -> mainThread.addScheduledTask(() ->
        {SKYBLOCK_SKILLS = resourceReply;
            try {

                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(
                        new ResourceLocation(SplokysSkyblockMod.MODID, "constants/catacombs.json")).getInputStream()), JsonElement.class);


                SKYBLOCK_SKILLS.getResponse().getAsJsonObject("skills").add("CATACOMBS",jsonElement);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dataHeld++;
            getData();}));

        API.getResource(ResourceType.SKYBLOCK_COLLECTIONS).thenAccept(resourceReply -> mainThread.addScheduledTask(() -> {
            SKYBLOCK_COLLECTION = resourceReply;
            dataHeld++;
            getData();
        }));

        HypixelUtils.API.getSkyBlockProfiles(Minecraft.getMinecraft().thePlayer.getUniqueID()).
                thenAccept(skyBlockProfilesReply -> mainThread.addScheduledTask(() ->
                {SKYBLOCK_PROFILES = skyBlockProfilesReply; dataHeld++; getCurrentProfileAndPlayer(); getData();}));

    }

    public static void getData() {
        if (!hasAllData()) return;

        for (HypixelData data : SplokysSkyblockMod.main.hypixelDataSet) {
            data.GetData();
        }
    }

    public static boolean hasAllData() {
        return dataHeld >= DATA_AMOUNT;
    }

    public static void loadSavedData() {
        HYPIXEL_DATA_CONFIG.Load();
        if (HYPIXEL_DATA_CONFIG.jsonObject.containsKey("ApiKey"))
            setApiKey(UUID.fromString((String)HYPIXEL_DATA_CONFIG.jsonObject.get("ApiKey")));
    }

    private static void getCurrentProfileAndPlayer() {
        JsonArray profiles = SKYBLOCK_PROFILES.getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            JsonObject profile = profiles.get(i).getAsJsonObject();
            if (profile.get("cute_name").getAsString().equalsIgnoreCase(CURRENT_SKYBLOCK_PROFILE_NAME)) {
                CURRENT_SKYBLOCK_PROFILE = profile;
            }
        }

        CURRENT_SKYBLOCK_PLAYER = CURRENT_SKYBLOCK_PROFILE.getAsJsonObject("members").
                getAsJsonObject(Minecraft.getMinecraft().thePlayer.getUniqueID().toString().replace("-", ""));
    }
}
