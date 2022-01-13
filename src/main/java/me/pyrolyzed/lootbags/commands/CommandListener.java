package me.pyrolyzed.lootbags.commands;

import me.pyrolyzed.lootbags.factory.LootbagManager;
import me.pyrolyzed.lootbags.objects.Lootbag;
import me.pyrolyzed.lootbags.utils.ItemBuilder;
import me.pyrolyzed.lootbags.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("lootbags")) {
            switch (args.length) {
                case 0:
                    player.sendMessage(getHelpMessage());
                    break;
                case 1:
                    if (args[0].equalsIgnoreCase("help")) {
                        player.sendMessage(getHelpMessage());
                    } else {
                        player.sendMessage(Utils.color("&cInvalid amount of arguments!"));
                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("giveall")) {
                        if (LootbagManager.getLootbagFromKey(args[1]) != null) {
                            Bukkit.broadcastMessage(Utils.color("&aAll players have been given &d1 &e" + args[1] + " &alootbag!"));
                            giveAllLootbag(LootbagManager.getLootbagFromKey(args[1]), 1);
                        } else {
                            player.sendMessage(Utils.color("&cLootbag not found!"));
                        }
                    } else if (args[0].equalsIgnoreCase("give")) {
                        if (LootbagManager.getLootbagFromKey(args[1]) != null) {
                            giveLootbag(LootbagManager.getLootbagFromKey(args[1]), player, 1);
                        } else {
                            player.sendMessage(Utils.color("&cLootbag not found!"));
                        }
                    } else {
                        player.sendMessage(Utils.color("&cCommand not found!"));
                    }
                    break;

                case 3:
                    if (args[0].equalsIgnoreCase("give")) {
                        if (Bukkit.getServer().getPlayer(args[2]) == null) {
                            player.sendMessage(Utils.color("&cThat player doesn't exist!"));
                        } else {
                            Player target = Bukkit.getServer().getPlayer(args[2]);

                            if (LootbagManager.getLootbagFromKey(args[1]) != null) {
                                giveLootbag(LootbagManager.getLootbagFromKey(args[1]), target, 1);
                                target.sendMessage(Utils.color("&aYou have been given &d1 &e" + args[1] + " &alootbag!"));
                            } else {
                                player.sendMessage(Utils.color("&aThat player doesn't exist!"));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("giveall")) {
                        if (LootbagManager.getLootbagFromKey(args[1]) != null) {
                            giveAllLootbag(LootbagManager.getLootbagFromKey(args[1]), Integer.parseInt(args[2]));
                        }
                    } else {
                        player.sendMessage(Utils.color("&cCommand not found!"));
                    }
                    break;
                case 4:
                    if (args[0].equalsIgnoreCase("give")) {
                        if (LootbagManager.getLootbagFromKey(args[1]) != null && Bukkit.getPlayer(args[2]) != null) {
                            Player target = Bukkit.getPlayer(args[2]);

                            giveLootbag(LootbagManager.getLootbagFromKey(args[1]), target, Integer.parseInt(args[3]));

                            player.sendMessage(Utils.color("&aYou have given &b" + target.getDisplayName() + " &d" + args[3] + " &alootbags!"));
                            target.sendMessage(Utils.color("&aYou have been given &d" + args[3] + " " + args[1] + " &alootbags!"));
                        }
                    } else {
                        player.sendMessage(Utils.color("&cCommand not found!"));
                    }
                    break;
            }
        }

        return true;
    }

    private void giveAllLootbag(Lootbag bag, int amount) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            giveLootbag(bag, player, amount);
        }
    }

    private void giveLootbag(Lootbag bag, Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(new ItemBuilder(bag.getItem()).nbt().set("noStack", UUID.randomUUID().toString()).build());
        }
    }

    public String getHelpMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("&cLoot&dbags &aHelp&7:\n");

        builder.append("\n&7A argument with &dthis color&7 means it is &doptional&7.\n \n&7A argument with &cthis color&7 means it is &crequired&7.\n ");
        builder.append("\n&7/lootbags &bgive &c{BAG} &d{PLAYER} {AMOUNT} &7: Give the specified bag to the target player with the specified amount.\n ");
        builder.append("\n&7/lootbags &bgiveall &c{BAG} &d{AMOUNT} &7: Give all players the specified bag with the specified amount\n ");
        builder.append("\n&7/lootbags &bhelp &7: Show this message");

        return Utils.color(builder.toString());
    }
}
