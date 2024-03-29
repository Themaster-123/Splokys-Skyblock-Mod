package me.sploky.ssm.hypixeldata;

public enum SkillType {
    FARMING("Farming"),
    MINING("Mining"),
    COMBAT("Combat"),
    FORAGING("Foraging"),
    FISHING("Fishing"),
    ENCHANTING("Enchanting"),
    ALCHEMY("Alchemy"),
    CARPENTRY("Carpentry"),
    RUNECRAFTING("Runecrafting"),
    SOCIAL("Social"),
    TAMING("Taming"),
    CATACOMBS("Catacombs");

    public final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
