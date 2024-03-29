package me.sploky.ssm;

import me.sploky.ssm.commands.ApiCommand;
import me.sploky.ssm.commands.GuiCommand;
import me.sploky.ssm.commands.HelpCommand;
import me.sploky.ssm.elements.Element;
import me.sploky.ssm.elements.ElementData;
import me.sploky.ssm.elements.ElementTextDecoder;
import me.sploky.ssm.gui.HelpCategory;
import me.sploky.ssm.gui.HelpGui;
import me.sploky.ssm.hypixeldata.*;
import me.sploky.ssm.listener.PlayerListener;
import me.sploky.ssm.listener.RenderListener;
import me.sploky.ssm.listener.ServerListener;
import net.hypixel.api.HypixelAPI;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;


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

    public HashSet<HypixelData> hypixelDataSet = new HashSet<>();

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        main = this;
        HypixelUtils.loadSavedData();
        ElementData.loadSavedData();
        registerListeners();
        registerCommands();
        registerData();
        ElementTextDecoder.initDecoder();
        setHelpCategories();
        System.out.println(NAME + "Started!");
        Runtime.getRuntime().addShutdownHook(new EndHook());

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
        registerData(new DungeonData());
        registerData(new CollectionData());
    }

    private void registerCommands() {
        
        ClientCommandHandler.instance.registerCommand(new GuiCommand());
        ClientCommandHandler.instance.registerCommand(new ApiCommand());
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
    }

    private void setHelpCategories() {
        HelpGui.HELP_CATEGORIES.add(new HelpCategory("Collection", "(§eCollection Name§r) §aTier", "(§eCollection Name§r) §aMax Tier"));
        HelpGui.HELP_CATEGORIES.add(new HelpCategory("Skill", "(§eSkill Name§r) §aXp", "(§eSkill Name§r) §aProgress", "(§eSkill Name§r) §aLevel",
                "(§eSkill Name§r) §aXp Needed"));
        HelpGui.HELP_CATEGORIES.add(new HelpCategory("Dungeons", "[§eFloor§r/§eMaster§r] (§eFloor Level§r) §aComps"));
    }
}
