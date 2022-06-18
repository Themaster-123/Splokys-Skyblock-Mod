package me.sploky.ssm.hypixeldata;

import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

import java.util.HashMap;
import java.util.HashSet;

public class SkillData implements HypixelData {
    public static Skill MINING_XP = null;

    private static final HashMap<SkillType, Skill> SKILLS = new HashMap<>();


    public void GetData() {
        SKILLS.clear();

        int miningXp = HypixelUtils.CURRENT_SKYBLOCK_PLAYER.get("experience_skill_mining") == null ? 0 :
                (int) HypixelUtils.CURRENT_SKYBLOCK_PLAYER.get("experience_skill_mining").getAsFloat();
        MINING_XP = new Skill(miningXp, SkillType.MINING);

        SKILLS.put(SkillType.MINING, MINING_XP);
    }

    public static Skill getSkill(SkillType skillType) {
        return  SKILLS.get(skillType);
    }
}
