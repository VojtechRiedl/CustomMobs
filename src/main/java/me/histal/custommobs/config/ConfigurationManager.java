package me.histal.custommobs.config;

import me.histal.custommobs.CustomMobs;

import java.util.HashMap;

public class ConfigurationManager {
    private CustomMobs plugin;

    HashMap<String, ConfigYmlFile> configFiles;
    public ConfigurationManager() {
        this.plugin = CustomMobs.getInstance();
        loadData();
    }
    /**
     * Loads all the config files
     */
    public void loadData() {
        configFiles = new HashMap<>();

        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            plugin.saveDefaultConfig();
        }

        ConfigYmlFile customMobsFile = new ConfigYmlFile("custom-mobs", true);
        configFiles.put("custom-mobs", customMobsFile);
    }
    /**
     * Reloads all the config files and the data from the user and mobs
     */
    public void reload() {
        configFiles.values().forEach(ConfigYmlFile::reload);
        plugin.getUserManager().loadData();
        plugin.getMobsManager().loadData();
    }
    /**
     * @param name The name of the file
     * @return The config file
     */
    public ConfigYmlFile getConfigFile(String name) {
        return configFiles.get(name);
    }
}
