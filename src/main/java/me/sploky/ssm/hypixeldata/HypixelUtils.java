package me.sploky.ssm.hypixeldata;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.skyblock.SkyBlockProfilesReply;
import net.minecraft.client.Minecraft;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class HypixelUtils {
    private static UUID API_KEY = null;
    public static HypixelAPI API;

    public static SkyBlockProfilesReply SKYBLOCK_PROFILES;

    public static UUID getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(UUID apiKey) {
        API_KEY = apiKey;
        API = new HypixelAPI(new ApacheHttpClient(apiKey));

        try {
            SKYBLOCK_PROFILES = HypixelUtils.API.getSkyBlockProfiles(Minecraft.getMinecraft().thePlayer.getUniqueID()).get();

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
