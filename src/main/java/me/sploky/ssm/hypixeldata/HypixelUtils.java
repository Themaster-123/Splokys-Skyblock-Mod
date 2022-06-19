package me.sploky.ssm.hypixeldata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.elements.ElementTextDecoder;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.ResourceReply;
import net.hypixel.api.reply.skyblock.SkyBlockProfilesReply;
import net.hypixel.api.util.ResourceType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public final class HypixelUtils {
    private static final int DATA_AMOUNT = 2;
    private static UUID API_KEY = null;
    private static int dataHeld = 0;
    public static HypixelAPI API;

    public static SkyBlockProfilesReply SKYBLOCK_PROFILES;
    public static ResourceReply SKYBLOCK_SKILLS;
    public static JsonObject CURRENT_SKYBLOCK_PROFILE;
    public static JsonObject CURRENT_SKYBLOCK_PLAYER;

    public static String CURRENT_SKYBLOCK_PROFILE_NAME = "";

    public static UUID getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(UUID apiKey) {
        API_KEY = apiKey;
        API = new HypixelAPI(new ApacheHttpClient(apiKey));
        fetchData();
    }

    public static void fetchData() {
        dataHeld = 0;

        if (API == null || CURRENT_SKYBLOCK_PROFILE_NAME.equals("")) return;

        IThreadListener mainThread = FMLCommonHandler.instance().getWorldThread(Minecraft.getMinecraft().getNetHandler());

        API.getResource(ResourceType.SKYBLOCK_SKILLS).thenAccept(resourceReply -> mainThread.addScheduledTask(() ->
        {SKYBLOCK_SKILLS = resourceReply; dataHeld++; getData();}));

        HypixelUtils.API.getSkyBlockProfiles(Minecraft.getMinecraft().thePlayer.getUniqueID()).
                thenAccept(skyBlockProfilesReply -> mainThread.addScheduledTask(() ->
                {SKYBLOCK_PROFILES = skyBlockProfilesReply; dataHeld++; getCurrentProfileAndPlayer(); getData();}));
    }

    public static void getData() {
        if (!hasAllData()) return;

        ElementTextDecoder.numberDecodeMap.clear();

        for (HypixelData data : SplokysSkyblockMod.main.hypixelDataSet) {
            data.GetData();
        }
    }

    public static boolean hasAllData() {
        return dataHeld >= DATA_AMOUNT;
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
