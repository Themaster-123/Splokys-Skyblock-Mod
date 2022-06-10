package me.sploky.ssm.hypixeldata;

import net.minecraft.client.Minecraft;

public class SkillData implements HypixelData {
    public static long MINING_XP = 0;

    public void GetData() {
        MINING_XP = (long) HypixelUtils.SKYBLOCK_PROFILES.getProfiles().get(2).getAsJsonObject().
            getAsJsonObject("members").
                getAsJsonObject(Minecraft.getMinecraft().thePlayer.getUniqueID().toString().replace("-", "")).
            get("experience_skill_mining").
                getAsFloat();
    }
}
