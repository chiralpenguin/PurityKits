package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitRoomGUI extends GUIWindow {

    public KitRoomGUI(Player player) {
        invTitle = "&a&l&nKit Room";
        createInventory(54);

        HashMap<String, GUIObject> guiObjects = PurityKits.INSTANCE.getGuiObjects();

        inventory.setItem(45, guiObjects.get("ExitMenu").createItem());
        inventory.setItem(47, guiObjects.get("KitRoom_Armoury").createItem());
        inventory.setItem(48, guiObjects.get("KitRoom_Potions").createItem());
        inventory.setItem(49, guiObjects.get("KitRoom_Consumables").createItem());
        inventory.setItem(50, guiObjects.get("KitRoom_Ammunition").createItem());
        inventory.setItem(51, guiObjects.get("KitRoom_Explosives").createItem());

    }
}
