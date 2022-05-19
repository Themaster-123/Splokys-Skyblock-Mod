package me.sploky.ssm.commands;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.gui.SplokysGui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class GuiCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ssmgui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "§c" + "figure it out your self idoit" + "§a" + "(just do /ssmgui)";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        System.out.println("test");
        SplokysSkyblockMod.main.renderListener.openSplokyGui();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
