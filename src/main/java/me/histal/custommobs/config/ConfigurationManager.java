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

    public void loadData() {
        configFiles = new HashMap<>();

        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            plugin.saveDefaultConfig();
        }

        ConfigYmlFile customMobsFile = new ConfigYmlFile("custom-mobs", true);
        configFiles.put("custom-mobs", customMobsFile);
    }

    public void reload() {
        configFiles.values().forEach(ConfigYmlFile::reload);
        plugin.getUserManager().loadData();
        plugin.getMobsManager().loadData();
    }

    public ConfigYmlFile getConfigFile(String name) {
        return configFiles.get(name);
    }
}
