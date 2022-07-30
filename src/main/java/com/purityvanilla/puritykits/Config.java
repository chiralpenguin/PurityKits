package com.purityvanilla.puritykits;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandExecutor;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private final YamlConfigurationLoader configLoader;
    private final String FNAME = "plugins/PurityKits/config.yml";
    private Boolean verbose;
    private Boolean cooldownEnabled;
    private int cooldown;
    private String spawnWorld;
    private HashMap<String, String> messageMap;

    public Config() {
        File file = new File(FNAME);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                InputStream configStream = getClass().getResourceAsStream("/config.yml");
                Files.copy(configStream, Paths.get(file.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configLoader = YamlConfigurationLoader.builder().path(Path.of(FNAME)).build();
        CommentedConfigurationNode root = null;

        try {
            root = configLoader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

        verbose = root.node("verbose").getBoolean();

        cooldownEnabled = root.node("kit-cooldown").getBoolean();
        cooldown = root.node("cooldown-time").getInt();

        spawnWorld = root.node("spawn-world").getString();

        messageMap = new HashMap<>();
        Map<Object, CommentedConfigurationNode> messages = root.node("messages").childrenMap();
        for (Map.Entry<Object, CommentedConfigurationNode> message : messages.entrySet()) {
            messageMap.put(message.getKey().toString(), message.getValue().getString());
        }

    }

    public Boolean verbose() {
        return verbose;
    }

    public Boolean cooldownEnabled() {
        return cooldownEnabled;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getSpawnWorld() {
        return spawnWorld;
    }

    public String KitClaimMessage() {
        return messageMap.get("kitclaim");
    }

    public String KitClaimFailMessage() {
        return messageMap.get("kitclaim-fail");
    }

    public String KitClaimCooldownMessage() {
        return messageMap.get("kitclaim-cooldown");
    }

    public String KitClaimNoAccessMessage() {
        return messageMap.get("kitclaim-no-access");
    }

}
