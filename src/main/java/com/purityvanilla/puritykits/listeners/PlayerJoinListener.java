package com.purityvanilla.puritykits.listeners;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PurityKits.INSTANCE.getKitsManager().loadPlayerKits(player);
    }
}
