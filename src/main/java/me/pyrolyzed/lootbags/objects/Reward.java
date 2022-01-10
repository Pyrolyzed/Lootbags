package me.pyrolyzed.lootbags.objects;

import org.bukkit.inventory.ItemStack;

public class Reward {
    private double chance;
    private ItemStack item;

    public Reward(double chance, ItemStack item) {
        this.chance = chance;
        this.item = item;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
