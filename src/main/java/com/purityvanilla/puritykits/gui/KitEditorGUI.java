package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.kits.PlayerKitsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class KitEditorGUI extends GUIWindow {
    private int kitNumber;

    public KitEditorGUI (Player player, int kitNumber) {
        invTitle = "&9&l" + PurityKits.INSTANCE.getKitsManager().getKitName(player, kitNumber);
        this.kitNumber = kitNumber;
        createInventory(54);

        ItemStack[] kitContents = PurityKits.INSTANCE.getKitsManager().getKitContents(player, kitNumber);
        if (kitContents != null) {
            inventory.setContents(kitContents);
        }

        HashMap<String, GUIObject> guiObjects = PurityKits.INSTANCE.getGuiObjects();
        inventory.setItem(45, guiObjects.get("ExitMenu").createItem());
        inventory.setItem(46, guiObjects.get("KitEditor_Undo").createItem());

    }

    @Override
    public void onClick(InventoryClickEvent event) {

        if (event.getRawSlot() > 40 && !(event.getClickedInventory() instanceof PlayerInventory)) {
            event.setCancelled(true);
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        String objectId = GUIObject.getIdFromItem(clickedItem);
        if (objectId == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        switch (objectId) {
            case "ExitMenu":
                ItemStack[] kitItems = Arrays.copyOfRange(inventory.getContents(), 0, 41);
                PlayerKitsManager kitsManager = PurityKits.INSTANCE.getKitsManager();
                kitsManager.setKitContents(player, kitNumber, kitItems);
                kitsManager.savePlayerKits(player);
                new KitsGUI(player).openGUI(player);

            case "KitEditor_Undo":
                new KitsGUI(player).openGUI(player);

        }

    }
}
