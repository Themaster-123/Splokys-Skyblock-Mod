package me.sploky.ssm.listener;

import me.sploky.ssm.hypixeldata.HypixelData;
import me.sploky.ssm.hypixeldata.HypixelUtils;
import me.sploky.ssm.hypixeldata.SkillData;
import me.sploky.ssm.hypixeldata.SkillType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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


                if (!HypixelUtils.hasAllData() || HypixelUtils.CURRENT_SKYBLOCK_PROFILE_NAME.equals(profileName)) {
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
                int level = Integer.parseInt(skillData[1]);


            }
        }




    }
}
