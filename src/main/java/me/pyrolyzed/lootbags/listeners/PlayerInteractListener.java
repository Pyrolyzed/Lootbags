package me.pyrolyzed.lootbags.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.pyrolyzed.lootbags.Lootbags;
import me.pyrolyzed.lootbags.factory.LootbagManager;
import me.pyrolyzed.lootbags.objects.Lootbag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {

            NBTItem nbtItem = new NBTItem(event.getItem());

            if (!nbtItem.hasKey("lootbag")) return;

            Lootbag bag = LootbagManager.getLootbagFromKey(nbtItem.getString("lootbag"));

            bag.open(event.getPlayer());

            // Some bullshit that server crashes without a 1 tick delay >:(
            new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().getInventory().setItemInMainHand(null);
                }
            }.runTaskLater(Lootbags.getPlugin(Lootbags.class), 1);
        }
    }
}
