package me.histal.custommobs;

import org.bukkit.ChatColor;

import java.util.Random;

public class Utils {
    static Random rd = new Random();
    public static Double convertToDouble(String string) {

        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean calculateChance(float chance){
        return rd.nextInt(100) < chance;

    }

    public static String colorize(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
