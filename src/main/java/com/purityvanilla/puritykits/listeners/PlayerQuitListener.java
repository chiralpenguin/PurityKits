package com.purityvanilla.puritykits.listeners;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PurityKits.INSTANCE.getKitsManager().unloadPlayerKits(player);
    }
}
