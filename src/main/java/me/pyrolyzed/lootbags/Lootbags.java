package me.pyrolyzed.lootbags;

import me.pyrolyzed.lootbags.config.ConfigManager;
import me.pyrolyzed.lootbags.factory.LootbagManager;
import me.pyrolyzed.lootbags.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lootbags extends JavaPlugin {


    private ConfigManager cfgManager;
    private LootbagManager lootbagManager;


    @Override
    public void onEnable() {
        Utils.log("Enabling Lootbags v" + getVersion());
        cfgManager = new ConfigManager(this);
        lootbagManager = new LootbagManager(this);

        lootbagManager.createLootbags();
    }

    @Override
    public void onDisable() {
        Utils.log("Disabling Lootbags v" + getVersion());
    }

    /**
     * Get the plugin version from the plugin.yml file
     * @return The plugin version
     */
    public String getVersion() {
        return getDescription().getVersion();
    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public LootbagManager getLootbagManager() {
        return lootbagManager;
    }
}
