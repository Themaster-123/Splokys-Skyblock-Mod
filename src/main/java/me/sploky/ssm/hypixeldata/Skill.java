package me.sploky.ssm.hypixeldata;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.MathHelper;

public class Skill {
    public final SkillType skillType;
    public int level = 0;
    public float progress;

    public Skill(int xp, SkillType skillType) {
        this.skillType = skillType;
        setLevelAndProgressFromXp(xp);
    }

    public int getXp() {
        if (HypixelUtils.hasAllData()) {
            JsonArray levels = getLevelsArray();
            int xpReq = level != 0 ? levels.get(level - 1).getAsJsonObject().get("totalExpRequired").getAsInt() : 0;
            int nextXpReq = levels.get(Math.min(levels.size() - 1, level)).getAsJsonObject()
                    .get("totalExpRequired").getAsInt();
            return (int) (xpReq + progress * (nextXpReq - xpReq));
        }

        return 0;
    }

    public void setLevelAndProgressFromXp(int xp) {
        if (HypixelUtils.hasAllData()) {
            JsonArray levels = getLevelsArray();

            int currentLevel = 0;
            int currentXpRequirement = 0;
            int nextXpRequirement = levels.get(0).getAsJsonObject().get("totalExpRequired").getAsInt();

            for (int i = 0; i < levels.size(); i++) {
                JsonObject levelObject = levels.get(i).getAsJsonObject();
                JsonObject nextLevelObject = i < levels.size() - 1 ? levels.get(i + 1).getAsJsonObject() : levels.get(i).getAsJsonObject();
                int nextXpReq = nextLevelObject.get("totalExpRequired").getAsInt();
                int currentXpReq = levelObject.get("totalExpRequired").getAsInt();
                int level = levelObject.get("level").getAsInt();
                if (xp >= currentXpReq && currentLevel < level) {
                    currentLevel = level;
                    nextXpRequirement = nextXpReq;
                    currentXpRequirement = currentXpReq;
                    continue;
                }

                break;
            }


            this.level = currentLevel;
            this.progress = currentLevel < levels.size() ? Math.min((float)(xp - currentXpRequirement) / (float) (nextXpRequirement - currentXpRequirement), 1f) : 0;

            System.out.println(Float.toString(this.progress) + Float.toString(this.level));
        }

    }

    public void setLevel(int level) {
        this.level = MathHelper.clamp_int(level, 0, HypixelUtils.SKYBLOCK_SKILLS.getResponse().getAsJsonObject("skills").
                getAsJsonObject(skillType.getName().toUpperCase()).get("maxLevel").getAsInt());
    }

    public void setProgress(float progress) {
        this.progress = MathHelper.clamp_float(progress, 0, 1);
    }

    private JsonArray getLevelsArray() {
        return HypixelUtils.SKYBLOCK_SKILLS.getResponse().
                getAsJsonObject("skills").
                getAsJsonObject(skillType.getName().toUpperCase()).
                getAsJsonArray("levels");
    }
}
