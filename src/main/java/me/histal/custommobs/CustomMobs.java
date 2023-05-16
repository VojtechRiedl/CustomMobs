package me.histal.custommobs;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomMobs extends JavaPlugin {

    private static CustomMobs instance;

    @Override
    public void onEnable() {
        instance = this;

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CustomMobs getInstance() {
        return instance;
    }
}
