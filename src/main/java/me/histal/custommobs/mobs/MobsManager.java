package me.histal.custommobs.mobs;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.config.ConfigYmlFile;
import me.histal.custommobs.mobs.enums.CreateMobResult;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.logging.Level;

public class MobsManager {

    private CustomMobs plugin;
    private ConfigYmlFile mobFile;
    private HashMap<String, CustomMob> mobs;
    public MobsManager() {
        this.plugin = CustomMobs.getInstance();
        loadData();
    }

    public void loadData() {
        mobs = new HashMap<>();

        this.mobFile = plugin.getConfigurationManager().getConfigFile("custom-mobs");

        if(mobFile == null){
            plugin.getLogger().log(Level.WARNING, "Can not load custom-mobs.yml");
            return;
        }

        ConfigurationSection mobsSection = mobFile.getConfig().getConfigurationSection("");
        if(mobsSection == null) {
            plugin.getLogger().log(Level.WARNING, "Can not load custom-mobs.yml");
            return;
        }

        for (String mobId : mobsSection.getKeys(false)){
            ConfigurationSection mobSection = mobsSection.getConfigurationSection(mobId);
            if(mobSection == null) {
                plugin.getLogger().log(Level.WARNING, "Can not load " + mobId + "section in custom-mobs.yml");
                continue;
            }

            String type = mobSection.getString("type");
            String name = mobSection.getString("name");
            double health = mobSection.getDouble("health");
            MobBuilder mobBuilder = new MobBuilder(mobId, name, type, health);

            if(mobSection.getConfigurationSection("item-in-hand") == null) {
                CustomMob mob = mobBuilder.build();
                if(mob == null) {
                    continue;
                }
                mobs.put(mobId, mobBuilder.build());
                plugin.getLogger().log(Level.INFO, "Loaded mob " + mobId);
                continue;
            }
            ConfigurationSection itemSection = mobSection.getConfigurationSection("item-in-hand");
            String material = itemSection.getString("material");
            String displayName = itemSection.getString("display-name");
            float dropChance = (float) itemSection.getDouble("drop-chance");
            mobBuilder.createMobDrop(displayName, material, dropChance);
            CustomMob mob = mobBuilder.build();
            if(mob == null) {
                continue;
            }
            mobs.put(mobId, mobBuilder.build());

            plugin.getLogger().log(Level.INFO, "Loaded mob " + mobId);

        }

    }

    public CreateMobResult createNewMob(String displayName, String type, double health){
        MobBuilder mobBuilder = new MobBuilder(displayName, type, health);
        CustomMob mob = mobBuilder.build();
        if(mob == null){
            return CreateMobResult.FAILED_BUILD;
        }
        if(!saveMobToFile(mob)){
            plugin.getLogger().log(Level.WARNING, "Can not save mob to file");
            return CreateMobResult.FAIL_ADDING_TO_FILE;
        }
        mobs.put(mobBuilder.getMobId(), mob);

        return CreateMobResult.SUCCESSFUL_CREATE;
    }


    private boolean saveMobToFile(CustomMob mob){
        if(mobFile == null){
            plugin.getLogger().log(Level.WARNING, "Can not save mob to file because mobFile was not loaded");
            return false;
        }
        ConfigurationSection mobsSection = mobFile.getConfig().getConfigurationSection("");

        ConfigurationSection mobSection = mobsSection.createSection(mob.getId());

        mobSection.set("type", mob.getEntityType().toString());
        mobSection.set("name", mob.getName());
        mobSection.set("health", mob.getHealth());

        mobFile.asyncSave();
        return true;

    }


    public boolean existMob(String mobId) {
        return mobs.containsKey(mobId);
    }
}
