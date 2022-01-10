package me.pyrolyzed.lootbags.objects;

import me.pyrolyzed.lootbags.interfaces.IOpenable;
import me.pyrolyzed.lootbags.utils.Utils;
import me.pyrolyzed.lootbags.utils.WeightedRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class Lootbag implements IOpenable {

    private String name;
    private int maxRewards;
    private Material display;
    private Set<Reward> rewards;


    private WeightedRandom<ItemStack> prizes;


    public Lootbag(String name, int maxRewards, Material display, Set<Reward> rewards) {
        this.name = name;
        this.maxRewards = maxRewards;
        this.display = display;

        prizes = new WeightedRandom<>();
        for(Reward reward : rewards) {
            prizes.add(reward.getChance(), reward.getItem());
        }
        Utils.log("Created lootbag with name: " + name);
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
        Location location = player.getLocation();

        Utils.log("Lootbag " + name + " was opened at location " + location);
    }
}
