package me.sploky.ssm.listener;

import me.sploky.ssm.SplokysSkyblockMod;
import me.sploky.ssm.hypixeldata.HypixelData;
import me.sploky.ssm.hypixeldata.HypixelUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerListener {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.type == TickEvent.Type.CLIENT) {
            HypixelUtils.updateData();
        }
    }
}
