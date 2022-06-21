package com.purityvanilla.puritykits.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitItem {
    private String materialType;
    private int amount;
    private String name;
    private List<String> lore;

    public KitItem(String materialType, int amount, String name, List<String> lore) {
        this.materialType = materialType;
        this.amount = amount;
        this.name = name;
        this.lore = lore;
    }

    public KitItem(ItemStack itemStack) {
        this.materialType = itemStack.getType().name();
        this.amount = itemStack.getAmount();
        /* Item lore preservation - not currently working
        ItemMeta itemMeta = itemStack.getItemMeta();
        this.name = LegacyComponentSerializer.legacyAmpersand().serialize(itemMeta.displayName());

        this.lore = new ArrayList<>();
        for (Component loreLine : itemMeta.lore()) {
            this.lore.add(LegacyComponentSerializer.legacyAmpersand().serialize(loreLine));
        }
         */
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.getMaterial(this.materialType), this.amount);

        /* Item lore preservation - not currently working
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(this.name));

        List<Component> lore = new ArrayList<>();
        for (String loreLine : this.lore) {
            lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(loreLine));
        }
         */

        return itemStack;
    }

}


