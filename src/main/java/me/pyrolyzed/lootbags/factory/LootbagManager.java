package me.pyrolyzed.lootbags.factory;

import me.pyrolyzed.lootbags.Lootbags;
import me.pyrolyzed.lootbags.objects.Lootbag;
import me.pyrolyzed.lootbags.objects.Reward;
import me.pyrolyzed.lootbags.utils.ItemBuilder;
import me.pyrolyzed.lootbags.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public class LootbagManager {
    private Lootbags plugin;
    private FileConfiguration config;

    private static final Map<String, Lootbag> LOOTBAGS = new HashMap<>();
    public LootbagManager(Lootbags plugin) {
        this.plugin = plugin;
        config = plugin.getCfgManager().getLootbagCfg();
    }

    public void createLootbags() {
        // All lootbag keys
        for(String key : config.getConfigurationSection("lootbags").getKeys(false)) {
            ConfigurationSection lSection = config.getConfigurationSection("lootbags." + key);


            String name = lSection.getString("name");
            int maxRewards = lSection.getInt("max-rewards");
            Material display = Material.valueOf(lSection.getString("display-material").toUpperCase());
            List<String> lore = lSection.getStringList("description");
            Set<Reward> rewards = new HashSet<>();

            ConfigurationSection rSection;

            for(String rKey : lSection.getConfigurationSection("rewards").getKeys(false)) {
                rSection = lSection.getConfigurationSection("rewards." + rKey);
                ItemBuilder builder = new ItemBuilder(Material.valueOf(rSection.getString("type").toUpperCase()))
                        .setName(rSection.getString("name"))
                        .setLore(rSection.getStringList("lore"))
                        .setAmount((rSection.get("amount") != null) ? rSection.getInt("amount") : 1);

                if (rSection.getBoolean("glowing")) builder.withGlow();

                if (rSection.getConfigurationSection("enchantments") != null) {
                    for (String eKey : rSection.getConfigurationSection("enchantments").getKeys(false)) {
                        builder.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(eKey.toLowerCase())), rSection.getInt("enchantments." + eKey));
                    }
                }
                double chance = rSection.getDouble("chance");

                rewards.add(new Reward(chance, builder.build()));
            }

            Lootbag bag = new Lootbag(key, name, maxRewards, display, lore, rewards);
            LOOTBAGS.put(key, bag);

        }
    }

    public static Lootbag getLootbagFromKey(String key) {
        return LOOTBAGS.get(key);
    }
}
