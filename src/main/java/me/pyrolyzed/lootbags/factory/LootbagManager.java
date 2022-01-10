package me.pyrolyzed.lootbags.factory;

import me.pyrolyzed.lootbags.Lootbags;
import me.pyrolyzed.lootbags.objects.Lootbag;
import me.pyrolyzed.lootbags.objects.Reward;
import me.pyrolyzed.lootbags.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LootbagManager {
    private Lootbags plugin;
    private FileConfiguration config;

    public static final Set<Lootbag> LOOTBAGS = new HashSet<>();
    public LootbagManager(Lootbags plugin) {
        this.plugin = plugin;
        config = plugin.getCfgManager().getLootbagCfg();
    }

    /*
    Name (string)
    Max Rewards (int)
    Display (Material)
    HashSet<Rewards>

    Reward:
    type (material)
    amount (int)
    name (string)
    glowing (bool)
    enchantments
        type (string)
        level (int)
     */
    public void createLootbags() {
        // All lootbag keys
        for(String key : config.getConfigurationSection("lootbags").getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection("lootbags." + key);

            String name = section.getString("name");
            int maxRewards = section.getInt("max-rewards");
            Material display = Material.valueOf(section.getString("display-material").toUpperCase());

            ConfigurationSection rSection;
            Set<Reward> rewards = new HashSet<>();
            for(String rKey : section.getConfigurationSection("rewards").getKeys(false)) {
                rSection = section.getConfigurationSection("rewards." + rKey);

                ItemStack item = new ItemStack(Material.valueOf(rSection.getString("type").toUpperCase()),
                        (rSection.get("amount") != null) ? rSection.getInt("amount") : 1);
                ItemMeta meta = item.getItemMeta();
                String displayName = rSection.getString("name");
                boolean glowing = rSection.getBoolean("glowing");

                if (displayName != null) {
                    meta.setDisplayName(Utils.color(displayName));
                }

                if (rSection.get("glowing") != null) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                }

                ConfigurationSection eSection;

                for(String eKey : rSection.getConfigurationSection("enchantments").getKeys(false)) {
                    eSection = rSection.getConfigurationSection("enchantments." + eKey);
                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(eSection.getString("type").toLowerCase()));
                    int level = eSection.getInt("level");

                    meta.addEnchant(enchantment, level, true);
                }

                item.setItemMeta(meta);

                rewards.add(new Reward(rSection.getDouble("chance"), item));
            }
            Lootbag bag = new Lootbag(name, maxRewards, display, rewards);
            LOOTBAGS.add(bag);
            Utils.log("Created bag " + bag.toString());
        }
    }
}
