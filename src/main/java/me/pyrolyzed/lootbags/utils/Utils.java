package me.pyrolyzed.lootbags.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * A general purpose utility class.
 */
public class Utils {

    /**
     * Translate a string into color
     * @param col The string to color
     * @return The translated string
     */
    public static String color(String col) {
        return ChatColor.translateAlternateColorCodes('&', col);
    }

    /**
     * Translate a list of strings into color
     * @param col The string list to color
     * @return The translated list
     */
    public static List<String> color(List<String> col) {
        return col.stream().map(Utils::color).collect(Collectors.toList());
    }


    /**
     * Log a message to the console using the Bukkit logger
     * @param message The message to log to the console
     * @param level The level of the message (i.e: warning)
     */
    public static void log(String message, Level level) {
        Bukkit.getLogger().log(level, Utils.color(message));
    }

    /**
     * Log a INFO message to the console using the Bukkit logger
     * @param message The message to log to the console
     */
    public static void log(String message) {
        log(message, Level.INFO);
    }

}
