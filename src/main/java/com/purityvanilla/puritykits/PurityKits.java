package com.purityvanilla.puritykits;

import com.google.common.reflect.TypeToken;
import com.purityvanilla.puritykits.commands.KitCommand;
import com.purityvanilla.puritykits.commands.ReloadCommand;
import com.purityvanilla.puritykits.gui.GUIObject;
import com.purityvanilla.puritykits.kits.PlayerKitsManager;
import com.purityvanilla.puritykits.listeners.InventoryClickListener;
import com.purityvanilla.puritykits.listeners.PlayerJoinListener;
import com.purityvanilla.puritykits.util.DataFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class PurityKits extends JavaPlugin {
    public static PurityKits INSTANCE;
    public static Config config;

    private PlayerKitsManager kitsManager;
    private String DATA_PATH = "plugins/PurityKits/";
    private HashMap<String, GUIObject> guiObjects;
    private DataFile guiObjectsData;

    @Override
    public void onEnable() {
        INSTANCE = this;
        config = new Config();

        kitsManager = new PlayerKitsManager();
        guiObjects = GUIObject.InitGuiObjectMap();

        guiObjectsData = new DataFile(DATA_PATH + "gui.json", new TypeToken<HashMap<String, GUIObject>>(){}.getType());
        guiObjectsData.save(guiObjects);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("reload").setExecutor(new ReloadCommand());

    }

    @Override
    public void onDisable() {

    }

    public PlayerKitsManager getKitsManager() {
        return kitsManager;
    }

    public HashMap<String, GUIObject> getGuiObjects() {
        return guiObjects;
    }

    public static Logger logger() {
        return INSTANCE.getLogger();
    }
}
