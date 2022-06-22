package com.purityvanilla.puritykits.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitItem {
    private String materialType;
    private int amount;
    private String name;
    private List<String> lore;
    private HashMap<NamespacedKey, Integer> enchantments;

    public KitItem(String materialType, int amount, String name, List<String> lore) {
        this.materialType = materialType;
        this.amount = amount;
        this.name = name;
        this.lore = lore;
    }

    public KitItem(ItemStack itemStack) {
        this.materialType = itemStack.getType().name();
        this.amount = itemStack.getAmount();

        if (itemStack.getEnchantments().size() != 0) {
            this.enchantments = new HashMap<>();
            itemStack.getEnchantments().forEach((enchant, lvl) -> {
                this.enchantments.put(enchant.getKey(), lvl);
            });
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.displayName() != null) {
            this.name = LegacyComponentSerializer.legacyAmpersand().serialize(itemMeta.displayName());
        }

        if (itemMeta.lore() != null) {
            this.lore = new ArrayList<>();
            for (Component loreLine : itemMeta.lore()) {
                this.lore.add(LegacyComponentSerializer.legacyAmpersand().serialize(loreLine));
            }
        }
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.getMaterial(this.materialType), this.amount);

        if (this.enchantments != null) {
            enchantments.forEach((enchant, lvl) -> {
                itemStack.addEnchantment(Enchantment.getByKey(enchant), lvl);
            });
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.name != null) {
            itemMeta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(this.name));
        }

        if (this.lore != null) {
            List<Component> lore = new ArrayList<>();
            for (String loreLine : this.lore) {
                lore.add(LegacyComponentSerializer.legacyAmpersand().deserialize(loreLine));
            }
            itemMeta.lore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}


