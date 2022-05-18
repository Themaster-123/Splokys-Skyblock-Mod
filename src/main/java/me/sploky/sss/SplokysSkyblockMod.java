package me.sploky.sss;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ssm", name = "Sploky's Skyblock Mod", version = "1.0")
public class SplokysSkyblockMod {
    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        System.out.println("Hello, " + Minecraft.getMinecraft().getSession().getUsername() + "!");
    }
}
