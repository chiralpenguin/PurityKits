package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.kits.KitRoomManager;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.HashMap;

public class KitRoomGUI extends GUIWindow {
    protected int currentPage;

    public KitRoomGUI(Player player) {
        invTitle = "&a&l&nKit Room";
        createInventory(54);

        HashMap<String, GUIObject> guiObjects = PurityKits.INSTANCE.getGuiObjects();

        inventory.setItem(45, guiObjects.get("ExitMenu").createItem());
        inventory.setItem(46, guiObjects.get("KitRoom_Weaponry").createItem());
        inventory.setItem(47, guiObjects.get("KitRoom_Armoury").createItem());
        inventory.setItem(48, guiObjects.get("KitRoom_Potions_1").createItem());
        inventory.setItem(49, guiObjects.get("KitRoom_Potions_2").createItem());
        inventory.setItem(50, guiObjects.get("KitRoom_Consumables").createItem());
        inventory.setItem(51, guiObjects.get("KitRoom_Ammunition").createItem());
        inventory.setItem(52, guiObjects.get("KitRoom_Explosives").createItem());


        for (int i = 47; i < 53; i++) {
            ItemStack item = inventory.getItem(i);
            item.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS});
            inventory.setItem(i, item);
        }

        if (player.hasPermission("puritykits.editkitroom")) {
            inventory.setItem(53, guiObjects.get("KitRoom_Save").createItem());
        }

        currentPage = -1;
        GotoPage(1);
    }

    public void GotoPage(int page) {
        int slotOffset = 45;
        if (currentPage != -1) {
         inventory.getItem(currentPage + slotOffset).removeEnchantment(Enchantment.MENDING);
        }

        int pageSlot = page + slotOffset;
        inventory.getItem(pageSlot).addUnsafeEnchantment(Enchantment.MENDING, 1);

        KitRoomManager kitRoomManager = PurityKits.INSTANCE.getKitRoomManager();
        ItemStack[] contents = kitRoomManager.GetPageContents(page);
        ItemStack[] menuItems = Arrays.copyOfRange(inventory.getContents(), 45, 54);
        inventory.setContents((ItemStack[]) ArrayUtils.addAll(contents, menuItems));

        currentPage = page;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        if (event.getRawSlot() > 44 && !(event.getClickedInventory() instanceof PlayerInventory)) {
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

        switch(objectId) {
            case "ExitMenu":
                Player player = (Player) event.getWhoClicked();
                new KitsGUI(player).openGUI(player);
                break;

            case "KitRoom_Save":
                KitRoomManager kitRoomManager = PurityKits.INSTANCE.getKitRoomManager();
                ItemStack[] contents = Arrays.copyOfRange(inventory.getContents(), 0, 45);
                kitRoomManager.SetPageContents(contents, currentPage);
                break;

            case "KitRoom_Weaponry":
                GotoPage(1);
                break;

            case "KitRoom_Armoury":
                GotoPage(2);
                break;

            case "KitRoom_Potions_1":
                GotoPage(3);
                break;

            case "KitRoom_Potions_2":
                GotoPage(4);
                break;

            case "KitRoom_Consumables":
                GotoPage(5);
                break;

            case "KitRoom_Ammunition":
                GotoPage(6);
                break;

            case "KitRoom_Explosives":
                GotoPage(7);
                break;
        }

    }
}
