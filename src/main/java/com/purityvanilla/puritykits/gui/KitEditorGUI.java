package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.kits.PlayerKitsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class KitEditorGUI extends GUIWindow {
    private int kitNumber;
    private Player player;

    public KitEditorGUI (Player player, int kitNumber) {
        invTitle = "&9&l" + PurityKits.INSTANCE.getKitsManager().getKitName(player, kitNumber);
        this.player = player;
        this.kitNumber = kitNumber;
        createInventory(54);

        ItemStack[] kitContents = PurityKits.INSTANCE.getKitsManager().getKitContents(player, kitNumber);
        if (kitContents != null) {
            inventory.setContents(kitContents);
        }

        CreateMenu();
    }

    public void CreateMenu() {
        HashMap<String, GUIObject> guiObjects = PurityKits.INSTANCE.getGuiObjects();
        inventory.setItem(45, guiObjects.get("ExitMenu").createItem());
        inventory.setItem(46, guiObjects.get("KitEditor_Undo").createItem());
        inventory.setItem(48, guiObjects.get("KitEditor_Import").createItem());
        inventory.setItem(49, guiObjects.get("KitEditor_Clear").createItem());
    }

    public void SaveKit() {
        ItemStack[] kitItems = Arrays.copyOfRange(inventory.getContents(), 0, 41);
        PlayerKitsManager kitsManager = PurityKits.INSTANCE.getKitsManager();
        kitsManager.setKitContents(player, kitNumber, kitItems);
        kitsManager.savePlayerKits(player);
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

        switch (objectId) {
            case "KitEditor_Undo":
                new KitsGUI(player).openGUI(player);
                break;

            case "KitEditor_Clear":
                inventory.setContents(new ItemStack[0]);
                CreateMenu();
                break;

            case "KitEditor_Import":
                inventory.setContents(player.getInventory().getContents());
                CreateMenu();
                break;

            case "ExitMenu":
                SaveKit();
                new KitsGUI(player).openGUI(player);
                break;

        }

    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        SaveKit();
    }
}
