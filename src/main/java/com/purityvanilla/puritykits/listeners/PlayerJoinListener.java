package com.purityvanilla.puritykits.listeners;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // TODO clear player inventory and send to spawn
        Player player = e.getPlayer();

        if (player.hasPermission("puritykits.spawnonjoin")) {
            resetPlayer(player);
        }


        PurityKits.INSTANCE.getKitsManager().loadPlayerKits(player);
    }

    public static void resetPlayer(Player player) {
        player.getInventory().setContents(new ItemStack[0]);
        player.updateInventory();
        player.closeInventory();

        player.teleport(PurityKits.INSTANCE.getServer().getWorld(PurityKits.config.getSpawnWorld()).getSpawnLocation());

        PurityKits.INSTANCE.getKitsManager().resetCooldown(player);
    }
}
