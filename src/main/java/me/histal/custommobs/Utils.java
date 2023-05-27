package me.histal.custommobs;

import org.bukkit.ChatColor;

public class Utils {

    public static Double convertToDouble(String string) {

        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean calculateChance(float chance){
        return Math.random() * 100 <= chance;
    }

    public static String colorize(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
