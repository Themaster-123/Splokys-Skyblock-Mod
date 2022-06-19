package me.sploky.ssm.hypixeldata;

import me.sploky.ssm.elements.ElementTextDecoder;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;

public class SkillData implements HypixelData {
    public static Skill FARMING_XP = null;
    public static Skill MINING_XP = null;
    public static Skill COMBAT_XP = null;
    public static Skill FORAGING_XP = null;
    public static Skill FISHING_XP = null;
    public static Skill ENCHANTING_XP = null;
    public static Skill ALCHEMY_XP = null;
    public static Skill CARPENTRY_XP = null;
    public static Skill RUNECRAFTING_XP = null;
    public static Skill SOCIAL_XP = null;
    public static Skill TAMING_XP = null;


    private static final HashMap<SkillType, Skill> SKILLS = new HashMap<>();


    public void GetData() {
        SKILLS.clear();

        for (Field field : SkillData.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                String skillName = field.getName().split("_")[0];
                String expName = skillName.equals("SOCIAL") ? "experience_skill_social2" : "experience_skill_" + skillName.toLowerCase();
                SkillType skillType = SkillType.valueOf(skillName.toUpperCase());

                int xp = HypixelUtils.CURRENT_SKYBLOCK_PLAYER.get(expName) == null ? 0 :
                        (int) HypixelUtils.CURRENT_SKYBLOCK_PLAYER.get(expName).getAsFloat();
                Skill skill = new Skill(xp, skillType);

                try {
                    field.set(null, skill);
                    SKILLS.put(skillType, (Skill) field.get(null));

                    System.out.println(skillName.toLowerCase() + " xp");

                    ElementTextDecoder.numberDecodeMap.put(skillName.toLowerCase() + " xp", () -> (float) skill.getXp());
                    ElementTextDecoder.numberDecodeMap.put(skillName.toLowerCase() + " progress", () -> (float) skill.progress);
                    ElementTextDecoder.numberDecodeMap.put(skillName.toLowerCase() + " level", () -> (float) skill.level);

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static Skill getSkill(SkillType skillType) {
        return  SKILLS.get(skillType);
    }
}
