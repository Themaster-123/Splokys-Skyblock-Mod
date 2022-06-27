package me.sploky.ssm.hypixeldata;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.MathHelper;

public class Skill {
    public final SkillType skillType;
    public int level = 0;
    public float progress;
    public int maxLevel = 0;

    private JsonArray levels;

    public Skill(int xp, SkillType skillType) {
        this.skillType = skillType;
        this.maxLevel = HypixelUtils.SKYBLOCK_SKILLS.getResponse().getAsJsonObject("skills").
                getAsJsonObject(skillType.getName().toUpperCase()).get("maxLevel").getAsInt();
        this.levels = getLevelsArray();
        setLevelAndProgressFromXp(xp);
    }

    public int getXp() {
        if (HypixelUtils.hasAllData()) {
            int xpReq = level != 0 ? levels.get(level - 1).getAsJsonObject().get("totalExpRequired").getAsInt() : 0;
            int nextXpReq = levels.get(Math.min(levels.size() - 1, level)).getAsJsonObject()
                    .get("totalExpRequired").getAsInt();
            return (int) (xpReq + progress * (nextXpReq - xpReq));
        }

        return 0;
    }

    public int getXpNeeded() {

        int xpReq = level != 0 ? levels.get(level - 1).getAsJsonObject().get("totalExpRequired").getAsInt() : 0;
        int nextXpReq = levels.get(Math.min(levels.size() - 1, level)).getAsJsonObject()
                .get("totalExpRequired").getAsInt();
        return nextXpReq - xpReq;
    }

    public void addXp(int addedXp) {

        int currentXp = getXp() + addedXp;

        int prevXpReq = getTotalXpNeeded(level);
        int xpReq = getTotalXpNeeded(level + 1);
        int nextXpReq = getTotalXpNeeded(level + 2);

        if (currentXp >= xpReq && level < maxLevel) {
            setLevel(level + 1);
            setProgress((float)(currentXp - xpReq) / (float)(nextXpReq - xpReq));
        } else {
            setProgress((float)(currentXp - prevXpReq) / (float)(xpReq - prevXpReq));
        }

    }

    public void setLevelAndProgressFromXp(int xp) {
        if (HypixelUtils.hasAllData()) {

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
        this.level = MathHelper.clamp_int(level, 0, maxLevel);
    }

    public void setProgress(float progress) {
        this.progress = MathHelper.clamp_float(progress, 0, 1);
    }

    private int getTotalXpNeeded(int level) {
        return levels.get(Math.min(levels.size() - 1, level - 1)).getAsJsonObject().get("totalExpRequired").getAsInt();
    }

    private JsonArray getLevelsArray() {
        return HypixelUtils.SKYBLOCK_SKILLS.getResponse().
                getAsJsonObject("skills").
                getAsJsonObject(skillType.getName().toUpperCase()).
                getAsJsonArray("levels");
    }


}
