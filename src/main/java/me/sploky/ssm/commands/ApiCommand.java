package me.sploky.ssm.commands;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.hypixeldata.HypixelUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class ApiCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ssmapi";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "§a" + "/ssmapi [api]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        try {
            UUID apiKey = UUID.fromString(args[0]);

            HypixelUtils.setApiKey(apiKey);
        } catch (IllegalArgumentException e) {
            throw new WrongUsageException("commands.ssmapi.usage");
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
