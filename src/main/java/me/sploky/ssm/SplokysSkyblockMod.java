package me.sploky.ssm;

import me.sploky.ssm.commands.GuiCommand;
import me.sploky.ssm.listener.RenderListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ssm", name = "Sploky's Skyblock Mod", version = "1.0")
public class SplokysSkyblockMod {
    public static SplokysSkyblockMod main;
    public RenderListener renderListener;

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        main = this;
        renderListener = new RenderListener();
        registerListeners();
        registerCommands();
        System.out.println("Starting!");
    }

    private void registerListeners() {
        MinecraftForge.EVENT_BUS.register(renderListener);

    }

    private void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new GuiCommand());

    }
}
