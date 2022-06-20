package com.purityvanilla.puritykits;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private final YamlConfigurationLoader configLoader;
    private final String FNAME = "plugins/PurityKits/config.yml";
    private Boolean verbose;

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

    }

    public Boolean verbose() {
        return verbose;
    }

}
