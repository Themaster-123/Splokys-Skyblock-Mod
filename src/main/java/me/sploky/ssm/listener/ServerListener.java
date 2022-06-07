package me.sploky.ssm.listener;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.hypixeldata.HypixelUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ServerListener {
    @SubscribeEvent
    public void onClientConnectionToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.split("\\.", 2)[2].equals("hypixel.net")) {
            HypixelUtils.fetchData();
        }
    }
}
