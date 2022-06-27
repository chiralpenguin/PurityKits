package com.purityvanilla.puritykits.kits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitItem {
    private String materialType;
    private int amount;
    private String name;
    private List<String> lore;
    private HashMap<String, Integer> enchantments;
    private PotionType potionType;
    private int potionLevel;

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
                this.enchantments.put(enchant.getName(), lvl); // Aware this is deprecated, will get namespacedkeys working eventually...
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

        if (itemMeta instanceof PotionMeta) {
            PotionMeta pMeta = (PotionMeta) itemMeta;
            PotionData pData = pMeta.getBasePotionData();
            int level = 0;
            potionType = pData.getType();

            if (pData.isUpgraded()) {
                level = level + 1;
            }

            if (pData.isExtended()) {
                level = level + 2;
            }

            this.potionLevel = level;
        }
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.getMaterial(this.materialType), this.amount);

        if (this.enchantments != null) {
            enchantments.forEach((enchant, lvl) -> {
                itemStack.addEnchantment(Enchantment.getByName(enchant), lvl);
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

        if (this.potionType != null) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            switch (potionLevel) {
                case 0:
                    potionMeta.setBasePotionData(new PotionData(potionType));
                    break;

                case 1:
                    potionMeta.setBasePotionData(new PotionData(potionType, false, true));
                    break;

                case 2:
                    potionMeta.setBasePotionData(new PotionData(potionType, true, false));
                    break;

                case 3:
                    potionMeta.setBasePotionData(new PotionData(potionType, true, true));
                    break;
            }

            itemStack.setItemMeta(potionMeta);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}


