package me.sploky.ssm.hypixeldata;

import com.google.gson.JsonObject;
import me.sploky.ssm.elements.ElementTextDecoder;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DungeonData implements HypixelData {
    private static final int[] FLOOR_COMPLETIONS = new int[15];

    @Override
    public void GetData() {
        JsonObject timesPlayedInDungeons = HypixelUtils.CURRENT_SKYBLOCK_PLAYER.getAsJsonObject("dungeons")
                .getAsJsonObject("dungeon_types").getAsJsonObject("catacombs")
                .getAsJsonObject("tier_completions");
        JsonObject timesPlayedInMaster = HypixelUtils.CURRENT_SKYBLOCK_PLAYER.getAsJsonObject("dungeons")
                .getAsJsonObject("dungeon_types").getAsJsonObject("master_catacombs")
                .getAsJsonObject("tier_completions");

        for (int i = 0; i < timesPlayedInDungeons.entrySet().size(); i++) {
            int finalI = i;
            ElementTextDecoder.numberDecodeMap.put("floor " + i + " comps", () -> (float)getFloorCompletion(finalI));
            setFloorCompletion(i, timesPlayedInDungeons.get(Integer.toString(i)).getAsInt());
        }

        for (int i = 0; i < timesPlayedInMaster.entrySet().size(); i++) {
            int finalI = i;
            ElementTextDecoder.numberDecodeMap.put("master " + (i + 1) + " comps", () -> (float)getFloorCompletion(8 + finalI));
            setFloorCompletion(8 + i, timesPlayedInMaster.get(Integer.toString(i + 1)).getAsInt());
        }
    }

    public static int getFloorCompletion(int floor) {
        return FLOOR_COMPLETIONS[floor];
    }

    public static void setFloorCompletion(int floor, int amount) {
        FLOOR_COMPLETIONS[floor] = amount;
    }

    public static void addFloorCompletion(int floor, int amount) {
        FLOOR_COMPLETIONS[floor] += amount;
    }

}
