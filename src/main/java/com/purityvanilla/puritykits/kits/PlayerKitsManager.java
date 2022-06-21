package com.purityvanilla.puritykits.kits;

import com.google.common.reflect.TypeToken;
import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.util.DataFile;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerKitsManager {

    private HashMap<UUID, HashMap<Integer, PlayerKit>> kitMap;
    private String KIT_DATA_PATH = "plugins/PurityKits/playerkits/";


    public PlayerKitsManager() {
        this.kitMap = new HashMap<>();
    }

    public void initPlayerKits(Player player){
        HashMap<Integer, PlayerKit> playerKitMap = new HashMap<>();

        for (int i = 1; i <= 7; i++) {
            playerKitMap.put(i, new PlayerKit(i));
        }

        kitMap.put(player.getUniqueId(), playerKitMap);
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Initialised kits for player: %s", PlainTextComponentSerializer.plainText().serialize(player.displayName())));
    }

    public void loadPlayerKits(Player player) {
        UUID uuid = player.getUniqueId();

        DataFile datafile = new DataFile(KIT_DATA_PATH + uuid + ".json", new TypeToken<HashMap<Integer, PlayerKit>>(){}.getType());
        HashMap<Integer, PlayerKit> playerKitMap = datafile.load();

        if (playerKitMap.equals(new HashMap<Integer, PlayerKit>())) {
            initPlayerKits(player);
        } else {
            kitMap.put(uuid, playerKitMap);
            if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Loaded kits for player: %s", PlainTextComponentSerializer.plainText().serialize(player.displayName())));
        }
    }

    public void savePlayerKits(Player player) {
        UUID uuid = player.getUniqueId();

        HashMap<Integer, PlayerKit> playerKitMap = kitMap.get(uuid);
        DataFile datafile = new DataFile(KIT_DATA_PATH + uuid + ".json", new TypeToken<HashMap<Integer, PlayerKit>>(){}.getType());

        datafile.save(playerKitMap);
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Saved kits for player: %s", PlainTextComponentSerializer.plainText().serialize(player.displayName())));
    }

    public void unloadPlayerKits(Player player) {
        savePlayerKits(player);

        kitMap.remove(player.getUniqueId());
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Unloaded kits for player: %s", PlainTextComponentSerializer.plainText().serialize(player.displayName())));
    }

    public String getKitName(Player player, int kitNumber) {
        return kitMap.get(player.getUniqueId()).get(kitNumber).getName();
    }

    public ItemStack[] getKitContents(Player player, int kitNumber) {
        return kitMap.get(player.getUniqueId()).get(kitNumber).getKitContents();
    }

    public void setKitContents(Player player, int kitNumber, ItemStack[] contents) {
        UUID uuid = player.getUniqueId();
        HashMap<Integer, PlayerKit> playerKitMap = kitMap.get(uuid);

        PlayerKit kit = playerKitMap.get(kitNumber);
        kit.setKitContents(contents);
        playerKitMap.put(kitNumber, kit);

        kitMap.put(uuid, playerKitMap);
    }
}
