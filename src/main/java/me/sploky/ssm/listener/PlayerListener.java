package me.sploky.ssm.listener;

import me.sploky.ssm.hypixeldata.*;
import me.sploky.ssm.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

public class PlayerListener {
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (HypixelUtils.hasAllData() && event.type == 2) {
            String hotbarMessage = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
            String[] hotbarInfo = hotbarMessage.split("     ");
            String actualMessage = (hotbarInfo.length == 3) ? hotbarInfo[1] : hotbarInfo[0];
            if (actualMessage.startsWith("+") && actualMessage.endsWith(")")) {
                String[] expMessageSplit = actualMessage.replace(",", "").split(" ");
                String skillName = expMessageSplit[1];
                SkillType skillType = SkillType.valueOf(skillName.toUpperCase());
                if (expMessageSplit[2].contains("/")) {
                    Float currentXp = Float.parseFloat(expMessageSplit[2].replace("(", "").replace(")", "").split("/")[0]);
                    Float neededXp = Float.parseFloat(expMessageSplit[2].replace("(", "").replace(")", "").split("/")[1]);
                    float progress = (currentXp / neededXp);

                    SkillData.getSkill(skillType).setProgress(progress);
                } else {
                    float progress = Float.parseFloat(expMessageSplit[2].replace("(", "").replace(")", "").
                            replace("%", ""));

                    SkillData.getSkill(skillType).setProgress(progress / 100f);
                }

            }
        } else if (event.type == 0) {
            String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());

            if (message.startsWith("You are playing on profile:") || message.startsWith("Your profile was changed to: ")) {
                String profileName = message.replace("You are playing on profile: ", "").
                        replace("Your profile was changed to: ", "").split(" ")[0];


                if (!HypixelUtils.hasAllData() || !HypixelUtils.CURRENT_SKYBLOCK_PROFILE_NAME.equals(profileName)) {
                    HypixelUtils.CURRENT_SKYBLOCK_PROFILE_NAME = profileName;

                    HypixelUtils.fetchData();
                    return;
                }

                HypixelUtils.CURRENT_SKYBLOCK_PROFILE_NAME = profileName;
                return;
            }

            if (message.startsWith("  SKILL LEVEL UP ")) {
                String[] skillData = message.replace("  SKILL LEVEL UP ", "").split(" ");
                String skillName = skillData[0];
                String number = skillData[1];

                if (number.contains("➜")) {
                    number = number.split("➜")[1];
                }

                int level = 0;
                try {
                    level = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    level = MathUtils.romanToNumber(number);
                }

                SkillData.getSkill(SkillType.valueOf(skillName.toUpperCase())).setLevel(level);
                return;
            }

            if (message.trim().startsWith("+") && message.trim().endsWith("Catacombs Experience")) {
                int xp = (int)Float.parseFloat(message.replaceAll("[^\\d.]+", ""));

                SkillData.getSkill(SkillType.CATACOMBS).addXp(xp);
                return;
            }

            updateDungeonFloorData(message);
        }
    }

    private void updateDungeonFloorData(String message) {
        boolean floorComp = message.trim().startsWith("The Catacombs - ");
        boolean isMaster = message.trim().startsWith("Master Mode Catacombs - ");
        floorComp = floorComp || isMaster;
        if (floorComp) {
            String floorName = message.trim().replace("The Catacombs - ", "").replace("Master Mode Catacombs - ", "").replace("Floor ", "");

            if (floorName.equalsIgnoreCase("Entrance")) {
                DungeonData.addFloorCompletion(0, 1);
            } else {
                DungeonData.addFloorCompletion(MathUtils.romanToNumber(floorName) + (isMaster ? 8 : 0), 1);
            }

        }
    }
}
