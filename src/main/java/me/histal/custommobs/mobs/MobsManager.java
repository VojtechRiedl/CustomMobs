package me.histal.custommobs.mobs;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.config.ConfigYmlFile;
import me.histal.custommobs.mobs.commands.CustomMobsCommand;
import me.histal.custommobs.mobs.controllers.MobController;
import me.histal.custommobs.mobs.enums.CreateMobResult;
import me.histal.custommobs.mobs.listeners.EntityListener;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MobsManager {

    private CustomMobs plugin;
    private ConfigYmlFile mobFile;
    private HashMap<String, CustomMob> mobs;

    private MobController mobController;
    public MobsManager() {
        this.plugin = CustomMobs.getInstance();
        this.mobController = new MobController();
        loadData();

        plugin.getServer().getPluginManager().registerEvents(new EntityListener(), plugin);
        plugin.getCommand("custommobs").setExecutor(new CustomMobsCommand());
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
            plugin.getLogger().log(Level.WARNING, "Can not load from custom-mobs.yml");
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
                mobs.put(mobId,mob);
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
            mobs.put(mobId, mob);

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

    public void spawnMob(CustomMob mob, Location location){
        if(mob == null){
            return;
        }
        if(location == null){
            return;
        }
        if(!location.isWorldLoaded()){
            return;
        }
        Entity entity = location.getWorld().spawnEntity(location, mob.getEntityType());

        updateMob(entity);
    }


    public void updateMob(Entity entity){
        List<CustomMob> mobs  = mobController.getMobsByType(entity.getType());
        if(mobs.size() == 0){
            return;
        }
        CustomMob customMob = mobs.size() > 1 ? mobController.getRandomMob(mobs) : mobs.get(0);

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setMaxHealth(customMob.getHealth());
        livingEntity.setCustomNameVisible(true);
        livingEntity.setCustomName(customMob.getName());

        livingEntity.setMetadata("custom-mob", new FixedMetadataValue(plugin, customMob.getId()));

        if(!(customMob instanceof CustomMobWithItem)) {
            return;
        }

        CustomMobWithItem customMobWithItem = (CustomMobWithItem) customMob;
        EntityEquipment equipment = livingEntity.getEquipment();
        if(equipment == null) {
            return;
        }
        equipment.setItemInMainHand(customMobWithItem.getDropAsItem());
    }


    public boolean isCustomMob(Entity entity){
        return entity.hasMetadata("custom-mob");
    }


    public CustomMob getMob(String mobId) {
        return mobs.get(mobId);
    }

    public List<CustomMob> getMobs() {
        return new ArrayList<>(mobs.values());
    }


    public boolean existMob(String mobId) {
        return mobs.containsKey(mobId);
    }

    public MobController getMobController() {
        return mobController;
    }
}
