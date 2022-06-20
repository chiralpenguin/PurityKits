package com.purityvanilla.puritykits.gui;

import com.purityvanilla.puritykits.PurityKits;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUIObject {
    private String id;
    private String name;
    private String materialType;
    private int amount;
    private List<String> lore;

    public GUIObject(String name, String materialType, int amount, List<String> lore, String id) {
        this.name = name;
        this.materialType = materialType;
        this.amount = amount;
        this.lore = lore;
        this.id = id;
    }

    public ItemStack createItem() {
        Material material = Material.getMaterial(materialType);
        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(
                LegacyComponentSerializer.legacyAmpersand().deserialize(name)));
        List<Component> lore = new ArrayList<>();
        for (String loreLine : this.lore) {
            lore.add(Component.empty().decoration(TextDecoration.ITALIC, false).append(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(loreLine)));
        }
        meta.lore(lore);

        NamespacedKey pdcKey = new NamespacedKey(PurityKits.INSTANCE, "gui-object");
        meta.getPersistentDataContainer().set(pdcKey, PersistentDataType.STRING, id);

        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static HashMap<String, GUIObject> InitGuiObjectMap() {
        HashMap<String, GUIObject> guiObjects = new HashMap<>();

        guiObjects.put("PersonalKit", new GUIObject("&b&lKit {number}", "CHEST", 1,
                new ArrayList<>(Arrays.asList("&aLeft click to load", "&eRight click to edit", "&7Command: /kit {number}")), "PersonalKit"));

        guiObjects.put("LockedPersonalKit", new GUIObject("&8&lKit {number}", "CHEST", 1,
                new ArrayList<>(Arrays.asList("&7This kit is locked!")), "LockedPersonalKit"));

        guiObjects.put("KitRoom", new GUIObject("&a&lKit Room", "NETHER_STAR", 1,
                new ArrayList<>(), "KitRoom"));

        guiObjects.put("ExitMenu", new GUIObject("&cExit", "OAK_DOOR", 1,
                new ArrayList<>(), "ExitMenu"));

        guiObjects.put("KitRoom_Armoury", new GUIObject("&bArmoury", "NETHERITE_SWORD", 1,
                new ArrayList<>(Arrays.asList("")), "KitRoom_Armoury"));

        guiObjects.put("KitRoom_Potions", new GUIObject("&bPotions", "SPLASH_POTION", 1,
                new ArrayList<>(Arrays.asList("")), "KitRoom_Potions"));

        guiObjects.put("KitRoom_Consumables", new GUIObject("&bConsumables", "ENDER_PEARL", 1,
                new ArrayList<>(), "KitRoom_Consumables"));

        guiObjects.put("KitRoom_Ammunition", new GUIObject("&bAmmunition", "TIPPED_ARROW", 1,
                new ArrayList<>(Arrays.asList("")), "KitRoom_Ammunition"));

        guiObjects.put("KitRoom_Explosives", new GUIObject("&bExplosives", "RESPAWN_ANCHOR", 1,
                new ArrayList<>(), "KitRoom_Explosives"));

        return guiObjects;
    }

    public static ItemStack SetNumberInDisplayname(ItemStack itemStack, int number) {
        ItemMeta meta = itemStack.getItemMeta();

        String displayName = LegacyComponentSerializer.legacyAmpersand().serialize(meta.displayName());
        displayName = displayName.replace("{number}", Integer.toString(number));
        meta.displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(
                LegacyComponentSerializer.legacyAmpersand().deserialize(displayName)));

        List<Component> lore = new ArrayList<>();
        for (Component loreLine : meta.lore()) {
            String loreString = LegacyComponentSerializer.legacyAmpersand().serialize(loreLine);
            loreString = loreString.replace("{number}", Integer.toString(number));
            lore.add(Component.empty().decoration(TextDecoration.ITALIC, false).append(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(loreString)));
        }
        meta.lore(lore);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static String getIdFromItem(ItemStack item) {
        NamespacedKey pdcKey = new NamespacedKey(PurityKits.INSTANCE, "gui-object");

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(pdcKey, PersistentDataType.STRING);
    }
}
