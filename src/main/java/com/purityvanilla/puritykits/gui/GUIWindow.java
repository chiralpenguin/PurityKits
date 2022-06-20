package com.purityvanilla.puritykits.gui;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class GUIWindow implements InventoryHolder {
    protected Inventory inventory;
    protected String invTitle;

    public void createInventory(int size) {
        this.inventory = Bukkit.createInventory(this, size, LegacyComponentSerializer.legacyAmpersand().deserialize(invTitle));
    }

    public void openGUI(Player player) {
        player.openInventory(inventory);
    }

    public void onClick(InventoryClickEvent event) {

    }

    public void closeGUI(Player player) {
        player.closeInventory();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
