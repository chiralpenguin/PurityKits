package com.purityvanilla.puritykits.kits;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerKit {
    private String name;
    private Boolean isPublic;
    private HashMap<Integer, KitItem> kitItems;

    public PlayerKit(String name, Boolean isPublic, HashMap<Integer, KitItem> items) {
        this.name = name;
        this.isPublic = isPublic;
        this.kitItems = items;
    }

    public PlayerKit(int kitNumber) {
        this.name = "Kit " + kitNumber;
        this.isPublic = false;
        this.kitItems = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public ItemStack[] getKitContents() {
        ItemStack[] contents = new ItemStack[41];
        kitItems.forEach((slot, item) -> {
            contents[slot] = item.getItemStack();
        });

        return contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setKitContents(ItemStack[] contents) {
        HashMap<Integer, KitItem> kitItems = new HashMap<>();

        for (int slot = 0; slot <= 40; slot ++) {
            ItemStack currentItem = contents[slot];
            if (currentItem != null) {
                kitItems.put(slot, new KitItem(currentItem));
            }
        }

        this.kitItems = kitItems;
    }

}
