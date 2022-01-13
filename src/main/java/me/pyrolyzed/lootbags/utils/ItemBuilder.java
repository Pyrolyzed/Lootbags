package me.pyrolyzed.lootbags.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setMaterial(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Utils.color(name));
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Utils.color(lore));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreLine(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null)
            lore = new ArrayList<>();

        lore.add(Utils.color(name));
        meta.setLore(lore);

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addItemFlags(flags);

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder withGlow() {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return this;
    }

    public Nbt nbt() {
        return new Nbt(this);
    }

    public ItemStack build() {
        return itemStack;
    }

    public class Nbt {

        protected final ItemBuilder builder;
        protected NBTItem nbtItem;

        public Nbt(ItemBuilder builder) {
            this.builder = builder;
            this.nbtItem = new NBTItem(builder.itemStack);
        }

        public Nbt set(String key, String value) {
            nbtItem.setString(key, value);
            return this;
        }

        public Nbt set(String key, Integer intVal) {
            nbtItem.setInteger(key, intVal);
            return this;
        }

        public Nbt set(String key, Double intVal) {
            nbtItem.setDouble(key, intVal);
            return this;
        }

        public Nbt set(String key, Boolean bool) {
            nbtItem.setBoolean(key, bool);
            return this;
        }

        public ItemStack build() {
            return nbtItem.getItem();
        }

        public ItemBuilder builder() {
            return builder;
        }

    }
}
