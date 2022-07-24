package me.sploky.ssm.commands;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.gui.HelpGui;
import me.sploky.ssm.gui.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class HelpCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ssmhelp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "§a" + "/ssmhelp";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        SplokysSkyblockMod.main.renderListener.openGui(new HelpGui());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
