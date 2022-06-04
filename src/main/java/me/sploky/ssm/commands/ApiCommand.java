package me.sploky.ssm.commands;

import me.sploky.ssm.hypixeldata.HypixelUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

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
            notifyOperators(sender, this, "commands.ssmapi.success", apiKey);
            HypixelUtils.setApiKey(apiKey);
        } catch (IllegalArgumentException e) {
            throw new WrongUsageException("commands.ssmapi.usage");
        }

    }
}
