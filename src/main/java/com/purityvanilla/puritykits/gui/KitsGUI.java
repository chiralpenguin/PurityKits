package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitsGUI extends GUIWindow {

    public KitsGUI(Player player) {
        invTitle = "&b&l&nPurity Kits";
        createInventory(45);
        // Populate the 9 kit slots with the appropriate number of available kits (indices 11->20)
        int availableKits = 3;
        if (player.hasPermission("puritykits.morekits")) availableKits = 5;
        if (player.hasPermission("puritykits.allkits")) availableKits = 7;

        HashMap<String, GUIObject> guiObjects = PurityKits.INSTANCE.getGuiObjects();
        for (int i = 1; i <= 7; i ++) {
            int slot = i + 9;
            GUIObject slotObject;

            if (i <= availableKits) {
                slotObject = guiObjects.get("PersonalKit");
            }
            else {
                slotObject = guiObjects.get("LockedPersonalKit");
            }

            inventory.setItem(slot, GUIObject.SetKitNumberMeta(slotObject.createItem(), i));
        }

        inventory.setItem(31, guiObjects.get("KitRoom").createItem());

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

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
            case "KitRoom":
                new KitRoomGUI(player).openGUI(player);
                break;

            case "PersonalKit":
                int kitNumber = GUIObject.GetKitNumberMeta(clickedItem);

                if (event.isRightClick()) {
                    new KitEditorGUI(player, kitNumber).openGUI(player);
                }
                if (event.isLeftClick()) {
                    PurityKits.INSTANCE.getKitsManager().claimKit(player, kitNumber);
                }
                break;

        }
    }



}
