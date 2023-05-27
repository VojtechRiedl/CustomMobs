package me.histal.custommobs;

import me.histal.custommobs.config.ConfigurationManager;
import me.histal.custommobs.mobs.MobsManager;
import me.histal.custommobs.mobs.commands.CustomMobsCommand;
import me.histal.custommobs.user.UserManager;
import me.histal.custommobs.user.listeners.LoginListener;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CustomMobs extends JavaPlugin {

    private static CustomMobs instance;

    private ConfigurationManager configurationManager;
    private UserManager userManager;
    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configurationManager = new ConfigurationManager();
        this.userManager = new UserManager();
        this.mobsManager = new MobsManager();

    }

    @Override
    public void onDisable() {
    }

    public static CustomMobs getInstance() {
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public MobsManager getMobsManager() {
        return mobsManager;
    }
}
