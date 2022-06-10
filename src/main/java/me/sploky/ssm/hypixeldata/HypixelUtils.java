package me.sploky.ssm.hypixeldata;

import me.sploky.ssm.SplokysSkyblockMod;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.skyblock.SkyBlockProfilesReply;
import net.minecraft.client.Minecraft;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class HypixelUtils {
    private static final int DATA_AMOUNT = 1;
    private static UUID API_KEY = null;
    private static int dataHeld = 0;
    public static HypixelAPI API;

    public static SkyBlockProfilesReply SKYBLOCK_PROFILES;

    public static UUID getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(UUID apiKey) {
        API_KEY = apiKey;
        API = new HypixelAPI(new ApacheHttpClient(apiKey));
        fetchData();
    }

    public static void fetchData() {
        if (API == null) return;
        dataHeld = 0;
        HypixelUtils.API.getSkyBlockProfiles(Minecraft.getMinecraft().thePlayer.getUniqueID()).
                thenAccept(skyBlockProfilesReply -> {SKYBLOCK_PROFILES = skyBlockProfilesReply; dataHeld++; getData();});

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
}
