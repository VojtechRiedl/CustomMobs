package me.histal.custommobs;

import org.bukkit.ChatColor;

import java.util.Random;

public class Utils {
    static Random rd = new Random();

    /**
     * This method converts a string to a double
     * @param string The string to convert
     * @return string as a double
     */
    public static Double convertToDouble(String string) {

        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method calculates a chance
     * @param chance The chance to calculate
     * @return The result of the calculation
     */
    public static boolean calculateChance(float chance){
        return rd.nextInt(100) < chance;

    }

    /**
     * This method colorizes a string
     * @param input The string to colorize
     * @return The colorized string
     */
    public static String colorize(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
