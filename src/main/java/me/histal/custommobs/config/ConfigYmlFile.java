package me.histal.custommobs.config;

import me.histal.custommobs.CustomMobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigYmlFile {
    private File file;
    private FileConfiguration configuration;
    private CustomMobs plugin;
    private String name;
    private String folder;

    /**
     * @param name The name of the file
     * @param createDefault If true, the file will be created if it doesn't exist
     */
    public ConfigYmlFile(String name, boolean createDefault) {
        this("", name,createDefault);
    }

    /**
     * @param folder The folder where the file is located
     * @param name The name of the file
     * @param createDefault If true, the file will be created if it doesn't exist
     */
    public ConfigYmlFile(String folder, String name, boolean createDefault) {
        this(CustomMobs.getInstance(), folder, name, createDefault);
    }

    /**
     * @param plugin The plugin where folder is located
     * @param folder The folder where the file is located
     * @param name The name of the file
     * @param createDefault If true, the file will be created if it doesn't exist
     */
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

    /**
     * @return true if the file exists
     */
    public boolean exists() {
        return this.file.exists();
    }
    /**
     * Creates a new file if it doesn't exist
     */
    public void createNewFile(){
        if(!this.exists()) {
            plugin.saveResource(this.name + ".yml", false);
        }
    }
    /**
     * Reloads configuration from file
     */
    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }
    /**
     * Saves configuration to file
     * @return true if the configuration was saved successfully
     */
    public boolean save() {
        try {
            this.configuration.save(this.file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Saves configuration to file asynchronously
     */
    public void asyncSave() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::save);
    }
    /**
     * Deletes the file asynchronously
     */
    public void asyncDelete() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::removeFile);
    }

    /**
     * @return The FileConfiguration of the file
     */
    public FileConfiguration getConfig(){
        return this.configuration;
    }
    /**
     * @return The File of the file
     */
    public File getFile(){
        return this.file;
    }
    /**
     * Deletes the file if it exists
     */
    public void removeFile() {
        if(exists()){
            this.file.delete();
        }
    }

}
