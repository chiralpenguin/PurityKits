package com.purityvanilla.puritykits.kits;

import com.google.common.reflect.TypeToken;
import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.util.DataFile;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerKitsManager {
    private HashMap<UUID, HashMap<Integer, PlayerKit>> kitMap;
    private String KIT_DATA_PATH = "plugins/PurityKits/playerkits/";

    public PlayerKitsManager() {
        this.kitMap = new HashMap<>();
        new File(KIT_DATA_PATH).mkdirs();
    }

    public void initPlayerKits(UUID uuid){
        HashMap<Integer, PlayerKit> playerKitMap = new HashMap<>();

        for (int i = 1; i <= 7; i++) {
            playerKitMap.put(i, new PlayerKit(i));
        }

        kitMap.put(uuid, playerKitMap);
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Initialised kits for player: %s", uuid));
    }

    public void loadPlayerKits(Player player) {
        loadPlayerKits(player.getUniqueId());
    }

    public void loadPlayerKits(UUID uuid) {
        DataFile datafile = new DataFile(KIT_DATA_PATH + uuid + ".json", new TypeToken<HashMap<Integer, PlayerKit>>(){}.getType());
        HashMap<Integer, PlayerKit> playerKitMap = datafile.load();

        if (playerKitMap.equals(new HashMap<Integer, PlayerKit>())) {
            initPlayerKits(uuid);
        } else {
            kitMap.put(uuid, playerKitMap);
            if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Loaded kits for player: %s", uuid));
        }
    }

    public void savePlayerKits(Player player) {
        savePlayerKits(player.getUniqueId());
    }

    public void savePlayerKits(UUID uuid) {
        HashMap<Integer, PlayerKit> playerKitMap = kitMap.get(uuid);
        DataFile datafile = new DataFile(KIT_DATA_PATH + uuid + ".json", new TypeToken<HashMap<Integer, PlayerKit>>(){}.getType());

        datafile.save(playerKitMap);
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Saved kits for player: %s", uuid));
    }

    public void unloadPlayerKits(Player player) {
        unloadPlayerKits(player.getUniqueId());
    }

    public void unloadPlayerKits(UUID uuid) {
        savePlayerKits(uuid);

        kitMap.remove(uuid);
        if (PurityKits.config.verbose()) PurityKits.logger().info(String.format("Unloaded kits for player: %s", uuid));
    }

    public void loadAllPlayerKits() {
        List<UUID> playerIDs = new ArrayList<>();
        File[] files = new File(KIT_DATA_PATH).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String id = file.getName().substring(0, file.getName().lastIndexOf('.'));
                playerIDs.add(UUID.fromString(id));
            }
        }

        for (UUID uuid : playerIDs) {
            loadPlayerKits(uuid);
        }
    }

    public void claimKit(Player player, int kitNumber) {
        claimKit(player, player.getUniqueId(), kitNumber);
    }

    public void claimKit(Player player, UUID kitOwner, int kitNumber) {

        if (player.getUniqueId() == kitOwner) {
            int availableKits = 3;
            if (player.hasPermission("puritykits.morekits")) availableKits = 5;
            if (player.hasPermission("puritykits.allkits")) availableKits = 7;

            if (kitNumber > availableKits) {
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                        PurityKits.config.KitClaimNoAccessMessage().replace("{kitname}", getKitName(kitOwner, kitNumber))
                ));
                return;
            }

            ItemStack[] kitContents = getKitContents(kitOwner, kitNumber);
            player.getInventory().setContents(kitContents);
            player.updateInventory();
            player.closeInventory();
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                    PurityKits.config.KitClaimMessage().replace("{kitname}", getKitName(kitOwner, kitNumber))
            ));
        }

    }

    public String getKitName(Player player, int kitNumber) {
        return getKitName(player.getUniqueId(), kitNumber);
    }

    public String getKitName(UUID player, int kitNumber) {
        return kitMap.get(player).get(kitNumber).getName();
    }

    public ItemStack[] getKitContents(Player player, int kitNumber) {
        return getKitContents(player.getUniqueId(), kitNumber);
    }

    public ItemStack[] getKitContents(UUID player, int kitNumber) {
        return kitMap.get(player).get(kitNumber).getKitContents();
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
