package me.histal.custommobs.config;

import me.histal.custommobs.CustomMobs;

import java.util.HashMap;

public class ConfigurationManager {

    private CustomMobs plugin;

    HashMap<String,ConfigFile> configFiles;

    public ConfigurationManager() {
        this.plugin = CustomMobs.getInstance();
        loadData();
    }

    public void loadData() {
        configFiles = new HashMap<>();

        configFiles.put("custom-mobs", new ConfigFile("custom-mobs", true));
    }

    public void reload() {
        configFiles.values().forEach(ConfigFile::reload);
        plugin.getUserManager().loadData();
        plugin.getMobsManager().loadData();
    }

    public ConfigFile getConfigFile(String name) {
        return configFiles.get(name);
    }
}
