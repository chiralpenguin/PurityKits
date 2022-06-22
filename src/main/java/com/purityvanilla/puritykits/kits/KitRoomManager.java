package com.purityvanilla.puritykits.kits;

import com.google.common.reflect.TypeToken;
import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.util.DataFile;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitRoomManager {
    private HashMap<Integer, HashMap<Integer, KitItem>> kitRoomMap;
    private String kitRoomDataPath = "plugins/PurityKits/kitroom.json";
    private DataFile datafile;

    public KitRoomManager() {
        this.datafile = new DataFile(kitRoomDataPath, new TypeToken<HashMap<Integer, HashMap<Integer, KitItem>>>(){}.getType());
        kitRoomMap = datafile.load();
        if (kitRoomMap.equals(new HashMap<>())) {
            InitKitRoomMap();
        }
    }

    public void InitKitRoomMap() {
        kitRoomMap = new HashMap<>();
        PurityKits.logger().info("Initialising KitRoom.json");

        for (int i = 1; i <= 5; i ++) {
            kitRoomMap.put(i, new HashMap<>());
        }
    }

    public ItemStack[] GetPageContents(int pageNumber) {
        ItemStack[] contents = new ItemStack[45];
        HashMap<Integer, KitItem> pageItems = kitRoomMap.get(pageNumber);

        pageItems.forEach((slot, item) -> {
            contents[slot] = item.getItemStack();
        });

        return contents;
    }

    public void SetPageContents(ItemStack[] contents, int pageNumber) {
        HashMap<Integer, KitItem> pageItems = new HashMap<>();

        for (int slot = 0; slot <= 44; slot ++) {
            ItemStack currentItem = contents[slot];
            if (currentItem != null) {
                pageItems.put(slot, new KitItem(currentItem));
            }
        }

        this.kitRoomMap.put(pageNumber, pageItems);
        datafile.save(kitRoomMap);
    }

}
