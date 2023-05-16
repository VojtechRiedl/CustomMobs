package me.histal.custommobs.config;

import me.histal.custommobs.CustomMobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigYmlFile {

    File file;
    FileConfiguration configuration;

    CustomMobs plugin;
    String name;
    String folder;

    public ConfigYmlFile(String name, boolean createDefault) {
        this("", name,createDefault);
    }

    public ConfigYmlFile(String folder, String name, boolean createDefault) {
        this(CustomMobs.getInstance(), folder, name, createDefault);
    }

    public ConfigYmlFile(CustomMobs plugin, String folder, String name, boolean createDefault) {
        this.plugin = plugin;
        this.folder = folder;
        this.name = name;
        this.configuration = YamlConfiguration.loadConfiguration(this.file = new File(plugin.getDataFolder() + this.folder, this.name + ".yml"));
        if(createDefault) {
            createNewFile();
        }
        plugin.getLogger().log(Level.INFO, "Loaded " + this.name + ".yml");

    }

    public boolean exists() {
        return this.file.exists();
    }

    public void createNewFile(){
        if(!this.exists()) {
            plugin.saveResource(this.name + ".yml", false);
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.configuration.save(this.file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void asyncSave() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::save);
    }

    public void asyncDelete() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::removeFile);
    }


    public FileConfiguration getConfig(){
        return this.configuration;
    }

    public File getFile(){
        return this.file;
    }

    public void removeFile() {
        if(exists()){
            this.file.delete();
        }
    }

}
