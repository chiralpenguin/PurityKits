package com.purityvanilla.puritykits.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void onPlayerPostRespawn(PlayerPostRespawnEvent e) {
        Player player = e.getPlayer();

        if (player.hasPermission("puritykits.spawnonjoin")) {
            PlayerJoinListener.resetPlayer(player);
        }
    }
}
