package me.pyrolyzed.lootbags.config;

import me.pyrolyzed.lootbags.Lootbags;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private Lootbags plugin;

    private File configFile;
    private File lootbagFile;

    private FileConfiguration config;
    private FileConfiguration lootbagCfg;



    public ConfigManager(Lootbags plugin) {
        this.plugin = plugin;

        // Initialize configuration files on class instantiation
        initializeConfigs();
    }

    private void initializeConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        lootbagFile = new File(plugin.getDataFolder(), "lootbags.yml");

        if (!lootbagFile.exists()) {
            lootbagFile.getParentFile().mkdirs();
            plugin.saveResource("lootbags.yml", false);
        }

        lootbagCfg = new YamlConfiguration();

        try {
            lootbagCfg.load(lootbagFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    private void reloadConfig(FileConfiguration cfg, File file) {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveLootbagConfig() {
        reloadConfig(lootbagCfg, lootbagFile);
    }


    public void saveConfig() {
        reloadConfig(config, configFile);
    }


    public FileConfiguration getLootbagCfg() {
        return lootbagCfg;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
