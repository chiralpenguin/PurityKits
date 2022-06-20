package com.purityvanilla.puritykits.kits;

import org.bukkit.inventory.ItemStack;

public class PlayerKit {
    private String name;
    private Boolean isPublic;
    private ItemStack[] items;

    public PlayerKit(String name, Boolean isPublic, ItemStack[] items) {
        this.name = name;
        this.isPublic = isPublic;
        this.items = items;
    }

    public PlayerKit(String name) {
        this.name = name;
        this.isPublic = false;
        this.items = new ItemStack[0];
    }

    public String getName() {
        return name;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }
}
