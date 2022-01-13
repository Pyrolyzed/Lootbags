package me.pyrolyzed.lootbags.objects;

import me.pyrolyzed.lootbags.interfaces.IOpenable;
import me.pyrolyzed.lootbags.utils.ItemBuilder;
import me.pyrolyzed.lootbags.utils.Utils;
import me.pyrolyzed.lootbags.utils.WeightedRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Lootbag implements IOpenable {

    private String name;
    private int maxRewards;
    private Material display;
    private Set<Reward> rewards;
    private List<String> lore;
    private ItemStack item;

    private WeightedRandom<ItemStack> prizes;


    public ItemStack getItem() {
        return item;
    }

    public Lootbag(String id, String name, int maxRewards, Material display, List<String> lore, Set<Reward> rewards) {
        this.name = name;
        this.maxRewards = maxRewards;
        this.display = display;
        this.lore = lore;

        prizes = new WeightedRandom<>();
        for(Reward reward : rewards) {
            prizes.add(reward.getChance(), reward.getItem());
        }
        item = new ItemBuilder(display).setName(name).setLore(lore).nbt().set("lootbag", id).build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxRewards() {
        return maxRewards;
    }

    public void setMaxRewards(int maxRewards) {
        this.maxRewards = maxRewards;
    }

    public Material getDisplay() {
        return display;
    }

    public void setDisplay(Material display) {
        this.display = display;
    }



    public void addRewards(Reward... toAdd) {
        for(Reward reward : toAdd) {
            rewards.add(reward);
            prizes.add(reward.getChance(), reward.getItem());
        }
    }

    public void removeRewards(Reward... toRemove) {
        for (Reward reward : toRemove) {
            rewards.remove(reward);
            prizes.remove(reward.getChance());
        }
    }

    public Set<Reward> getRewards() {
        return rewards;
    }
    @Override
    public String toString() {
        return "Lootbag:\n" +
                "Name: " + name + "\n" +
                "Max-Rewards: " + maxRewards + "\n" +
                "Display-Material: " + display.toString();
    }


    @Override
    public void open(Player player) {
        player.getInventory().addItem(prizes.next());
    }
}
