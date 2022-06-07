package me.sploky.ssm.hypixeldata;

import net.minecraft.client.Minecraft;

public class SkillData implements HypixelData {
    public static long MINING_XP;

    public void GetData() {
        MINING_XP = HypixelUtils.SKYBLOCK_PROFILES.getProfiles().getAsJsonObject().getAsJsonObject("profiles").getAsJsonObject("2").
            getAsJsonObject("members").getAsJsonObject(Minecraft.getMinecraft().thePlayer.getUniqueID().toString()).
            get("experience_skill_mining").getAsLong();
    }

    @Override
    public void UpdateData() {

    }
}
