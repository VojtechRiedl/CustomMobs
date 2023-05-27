package me.histal.custommobs.mobs;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.config.ConfigYmlFile;
import me.histal.custommobs.mobs.commands.CustomMobsCommand;
import me.histal.custommobs.mobs.controllers.MobController;
import me.histal.custommobs.mobs.enums.CreateMobResult;
import me.histal.custommobs.mobs.events.AddCustomMobEvent;
import me.histal.custommobs.mobs.events.SaveCustomMobEvent;
import me.histal.custommobs.mobs.events.SpawnCustomMobEvent;
import me.histal.custommobs.mobs.listeners.EntityListener;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
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

    /**
     * load all custom mobs from custom-mobs.yml file and store them in a HashMap with their id as key and CustomMob as value
     * @see CustomMob
     */
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
                registerMob(mob);
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
            registerMob(mob);
            plugin.getLogger().log(Level.INFO, "Loaded mob " + mobId);
        }

    }

    /**
     * create a new custom mob with the given parameters and save it to custom-mobs.yml file
     * @param displayName the name of the mob
     * @param type the type of the mob
     * @param health the health of the mob
     * @return CreateMobResult the result of the creation
     */
    public CreateMobResult addNewMob(String displayName, String type, double health){
        MobBuilder mobBuilder = new MobBuilder(displayName, type, health);
        CustomMob mob = mobBuilder.build();
        if(mob == null){
            return CreateMobResult.FAILED_BUILD;
        }
        if(!saveMobToFile(mob)){
            plugin.getLogger().log(Level.WARNING, "Can not save mob to file");
            return CreateMobResult.FAIL_ADDING_TO_FILE;
        }

        AddCustomMobEvent event = new AddCustomMobEvent(mob);
        if(event.isCancelled()){
            return CreateMobResult.CANCELLED_BY_EVENT;
        }
        if(event.getMob() == null){
            return CreateMobResult.FAILED_BUILD;
        }
        registerMob(event.getMob());

        return CreateMobResult.SUCCESSFUL_CREATE;
    }

    /**
     * save the given mob to custom-mobs.yml file
     * @param mob the mob to save to file
     * @return true if the mob was saved to file, return false if mob is not successfully saved to file
     */
    private boolean saveMobToFile(CustomMob mob){
        if(mobFile == null){
            plugin.getLogger().log(Level.WARNING, "Can not save mob to file because mobFile was not loaded");
            return false;
        }
        if(mob == null){
            return false;
        }
        SaveCustomMobEvent event = new SaveCustomMobEvent(mob);
        if(event.isCancelled()){
            return false;
        }
        if(event.getMob() == null){
            return false;
        }

        ConfigurationSection mobsSection = mobFile.getConfig().getConfigurationSection("");

        ConfigurationSection mobSection = mobsSection.createSection(mob.getId());

        mobSection.set("type", event.getMob().getEntityType().toString());
        mobSection.set("name", event.getMob().getName());
        mobSection.set("health", event.getMob().getHealth());

        mobFile.asyncSave();
        return true;

    }

    /**
     * spawn a custom mob at the given location
     * @param customMob the custom mob to spawn
     * @param location the location to spawn the mob at
     */
    public void spawnMob(CustomMob customMob, Location location){
        if(customMob == null){
            return;
        }
        if(location == null){
            return;
        }
        if(!location.isWorldLoaded()){
            return;
        }
        Entity entity = location.getWorld().spawnEntity(location, customMob.getEntityType());

        SpawnCustomMobEvent event = new SpawnCustomMobEvent(entity,customMob);
        if(event.isCancelled()){
            return;
        }
        updateMob(event.getEntity(), event.getMob());

    }

    /**
     * spawn a custom mob on the given entity
     * @param entity the entity to spawn the mob on
     */
    public void spawnMob(Entity entity){
        List<CustomMob> mobs  = mobController.getMobsByType(entity.getType());
        if(mobs.size() == 0){
            return;
        }
        CustomMob customMob = mobs.size() > 1 ? mobController.getRandomMob(mobs) : mobs.get(0);

        SpawnCustomMobEvent event = new SpawnCustomMobEvent(entity,customMob);
        if(event.isCancelled()){
            return;
        }
        updateMob(event.getEntity(), event.getMob());
    }
    /**
     * update the given entity to the custom mob
     * @param entity the entity to update
     * @param customMob the custom mob to update the entity to
     */
    public void updateMob(Entity entity, CustomMob customMob){
        if(entity == null || customMob == null){
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(customMob.getHealth());
        livingEntity.setHealth(customMob.getHealth());
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

    /**
     * check if the given entity is a custom mob
     * @param entity the entity to check
     * @return true if the entity is a custom mob, return false if the entity is not a custom mob
     */
    public boolean isCustomMob(Entity entity){
        return entity.hasMetadata("custom-mob");
    }

    /**
     * get the custom mob of the given entity
     * @param entity the entity to get the custom mob of
     * @return the custom mob of the given entity, return null if the entity is not a custom mob
     */
    public CustomMob getMob(Entity entity){
        if(!isCustomMob(entity)){
            return null;
        }
        String mobId = entity.getMetadata("custom-mob").get(0).asString();
        return getMob(mobId);
    }

    /**
     * register a custom mob into plugin
     * @param mob the custom mob to register
     */
    public void registerMob(CustomMob mob) {
        if(mob == null) {
            return;
        }
        mobs.put(mob.getId(), mob);
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
