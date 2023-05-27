package me.histal.custommobs.mobs;

import me.histal.custommobs.CustomMobs;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Date;
import java.util.logging.Level;

public class MobBuilder {
    private static final EntityType[] possibleTypes = {
            EntityType.ALLAY,
            EntityType.BAT,
            EntityType.BEE,
            EntityType.AXOLOTL,
            EntityType.BLAZE,
            EntityType.CAT,
            EntityType.CAVE_SPIDER,
            EntityType.CHICKEN,
            EntityType.COD,
            EntityType.COW,
            EntityType.CREEPER,
            EntityType.DOLPHIN,
            EntityType.DONKEY,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDER_DRAGON,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.FOX,
            EntityType.GHAST,
            EntityType.GIANT,
            EntityType.GOAT,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.HORSE,
            EntityType.HUSK,
            EntityType.ILLUSIONER,
            EntityType.IRON_GOLEM,
            EntityType.LLAMA,
            EntityType.MAGMA_CUBE,
            EntityType.MULE,
            EntityType.MUSHROOM_COW,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.PIG,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.PILLAGER,
            EntityType.POLAR_BEAR,
            EntityType.PUFFERFISH,
            EntityType.RABBIT,
            EntityType.RAVAGER,
            EntityType.SALMON,
            EntityType.SHEEP,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SKELETON_HORSE,
            EntityType.SLIME,
            EntityType.SNOWMAN,
            EntityType.SPIDER,
            EntityType.SQUID,
            EntityType.STRAY,
            EntityType.STRIDER,
            EntityType.TRADER_LLAMA,
            EntityType.TROPICAL_FISH,
            EntityType.TURTLE,
            EntityType.VEX,
            EntityType.VILLAGER,
            EntityType.VINDICATOR,
            EntityType.WANDERING_TRADER,
            EntityType.WITCH,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.WOLF,
            EntityType.ZOGLIN,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.GLOW_SQUID,
            EntityType.FROG,
            EntityType.TADPOLE,
            EntityType.WARDEN
    };

    public static EntityType[] getPossibleTypes(){
        return possibleTypes;
    }

    private String mobId;
    private String name;
    private EntityType type;
    private double health;
    private MobDrop itemInHand;
    private CustomMobs plugin;
    /**
     * This class is used to create custom mobs
     * @param displayName The name of the custom mob
     * @param type The entity type of the custom mob
     * @param health The health of the custom mob
     */
    public MobBuilder(String displayName,String type, double health){
        this.plugin = CustomMobs.getInstance();
        this.type = getMobEntityTypeFromString(type);
        this.name = displayName;
        this.mobId = getMobIdFromName(displayName);
        this.health = health;
    }
    /**
     * This class is used to create custom mobs
     * @param mobId The id of the custom mob
     * @param displayName The name of the custom mob
     * @param type The entity type of the custom mob
     * @param health The health of the custom mob
     */
    public MobBuilder(String mobId,String displayName,String type, double health){
        this.plugin = CustomMobs.getInstance();
        this.type = getMobEntityTypeFromString(type);
        this.mobId = mobId;
        this.name = displayName;
        this.health = health;
    }


    /**
     * this method builds the mob and returns it as a CustomMob
     * @return CustomMob
     */
    public CustomMob build(){
        if(type == null){
            plugin.getLogger().log(Level.SEVERE, "Can not build mob: " + mobId + " because type is null");
            return null;
        }
        if(name == null){
            plugin.getLogger().log(Level.WARNING, "name is not set");
            return null;
        }
        if(health <= 0){
            plugin.getLogger().log(Level.WARNING, "mob health is 0 or under 0");
            return null;
        }
        if(type == EntityType.SKELETON || type == EntityType.ZOMBIE){
            return new CustomMobWithItem(mobId,type,name, health, itemInHand);
        }

        return new CustomMob(mobId,type,name, health);
    }

    /**
     * Creates a drop for the mob to drop when killed by a player
     * @param displayName The name of the custom mob
     * @param material The material of the item
     * @param dropChance The chance of the item dropping
     */
    public void createMobDrop(String displayName, String material, float dropChance) {
        Material itemMaterial = Material.getMaterial(material);
        if(itemMaterial == null){
            plugin.getLogger().log(Level.WARNING, "Can not find material: " + material);
            return;
        }
        if(itemMaterial.isAir()){
            return;
        }
        if(displayName == null){
            plugin.getLogger().log(Level.WARNING, "displayName is not set");
        }
        if(dropChance <= 0){
            plugin.getLogger().log(Level.WARNING, "dropChance is 0 or under 0");
        }

        itemInHand = new MobDrop(itemMaterial, displayName, dropChance);



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
    /**
     * This method gets the entity type from the string
     * @param type The entity type as a string
     * @return The entity type
     */
    public EntityType getMobEntityTypeFromString(String type){

        for (EntityType entityType : possibleTypes){
            if (entityType.toString().equalsIgnoreCase(type)){
                return entityType;
            }

        }
        plugin.getLogger().log(Level.WARNING, "Can not find entity type: " + type);
        return null;
    }
    /**
     * This method gets the mob id from the mob name
     * @param name The name of the custom mob
     * @return The mob id
     */
    public String getMobIdFromName(String name){
        String mobId = name.toLowerCase().replace(" ", "-");
        if(plugin.getMobsManager().existMob(mobId)){
            return mobId + "-" + new Date().getTime();
        }

        return mobId;
    }

    public MobDrop getItemInHand() {
        return itemInHand;
    }

    public String getMobId() {
        return mobId;
    }

    public double getHealth() {
        return health;
    }
}
