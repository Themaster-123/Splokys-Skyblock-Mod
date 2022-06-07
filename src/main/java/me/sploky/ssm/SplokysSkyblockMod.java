package me.sploky.ssm;

import me.sploky.ssm.commands.ApiCommand;
import me.sploky.ssm.commands.GuiCommand;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.elements.ElementTextDecoder;
import me.sploky.ssm.hypixeldata.HypixelData;
import me.sploky.ssm.hypixeldata.HypixelUtils;
import me.sploky.ssm.hypixeldata.SkillData;
import me.sploky.ssm.listener.PlayerListener;
import me.sploky.ssm.listener.RenderListener;
import me.sploky.ssm.listener.ServerListener;
import net.hypixel.api.HypixelAPI;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod(modid = SplokysSkyblockMod.MODID, name = SplokysSkyblockMod.NAME, version = "1.0")
public class SplokysSkyblockMod {
    public static final String MODID = "ssm";
    public static final String NAME = "Sploky's Skyblock Mod";

    public static SplokysSkyblockMod main;
    public RenderListener renderListener;

    public HashSet<Element> elements = new HashSet<>();
    public HashSet<HypixelData> hypixelDataSet = new HashSet<>();

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        main = this;
        elements.add(new Element(.5f, .5f, 20, 20, new Color(217, 163, 52, 255)));
        registerListeners();
        registerCommands();
        registerData();
        ElementTextDecoder.initDecoder();
        System.out.println(NAME + "Started!");


    }

    public void registerData(HypixelData data) {
        hypixelDataSet.add(data);
    }

    private void registerListeners() {
        renderListener = new RenderListener();

        MinecraftForge.EVENT_BUS.register(renderListener);
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new ServerListener());
    }

    private void registerData() {
        registerData(new SkillData());
    }

    private void registerCommands() {
        
        ClientCommandHandler.instance.registerCommand(new GuiCommand());
        ClientCommandHandler.instance.registerCommand(new ApiCommand());
    }
}
