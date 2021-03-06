package com.purityvanilla.puritykits;

import com.google.common.reflect.TypeToken;
import com.purityvanilla.puritykits.commands.KitCommand;
import com.purityvanilla.puritykits.commands.ReloadCommand;
import com.purityvanilla.puritykits.commands.SpawnCommand;
import com.purityvanilla.puritykits.gui.GUIObject;
import com.purityvanilla.puritykits.kits.KitRoomManager;
import com.purityvanilla.puritykits.kits.PlayerKitsManager;
import com.purityvanilla.puritykits.listeners.*;
import com.purityvanilla.puritykits.tasks.UpdateCooldown;
import com.purityvanilla.puritykits.util.DataFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.logging.Logger;

public class PurityKits extends JavaPlugin {
    public static PurityKits INSTANCE;
    public static Config config;

    private PlayerKitsManager kitsManager;
    private KitRoomManager kitRoomManager;
    private String DATA_PATH = "plugins/PurityKits/";
    private HashMap<String, GUIObject> guiObjects;
    private DataFile guiObjectsData;
    public BukkitTask updateCooldownTask;

    @Override
    public void onEnable() {
        INSTANCE = this;
        config = new Config();

        kitsManager = new PlayerKitsManager();
        kitRoomManager = new KitRoomManager();
        guiObjects = GUIObject.InitGuiObjectMap();

        guiObjectsData = new DataFile(DATA_PATH + "gui.json", new TypeToken<HashMap<String, GUIObject>>(){}.getType());
        guiObjectsData.save(guiObjects);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPostRespawnListener(), this);

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("reload").setExecutor(new ReloadCommand());

        if (config.cooldownEnabled()) {
            updateCooldownTask = new UpdateCooldown().runTaskTimer(this, 20L, 20L);
        }

    }

    @Override
    public void onDisable() {

    }

    public PlayerKitsManager getKitsManager() {
        return kitsManager;
    }

    public KitRoomManager getKitRoomManager() {
        return kitRoomManager;
    }

    public HashMap<String, GUIObject> getGuiObjects() {
        return guiObjects;
    }

    public static Logger logger() {
        return INSTANCE.getLogger();
    }
}
