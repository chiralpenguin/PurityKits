package com.purityvanilla.puritykits.listeners;

import com.purityvanilla.puritykits.gui.GUIWindow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();

        if (e.getClickedInventory() instanceof PlayerInventory && !e.isShiftClick()) {
            return;
        }

        if (inv.getHolder() instanceof GUIWindow) {
            ((GUIWindow) inv.getHolder()).onClick(e);
        }

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getInventory();

        if (inv.getHolder() instanceof GUIWindow) {
            e.setCancelled(true);
        }
    }
}
