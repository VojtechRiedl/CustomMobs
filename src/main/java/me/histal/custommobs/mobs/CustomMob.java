package me.histal.custommobs.mobs;

import me.histal.custommobs.Utils;
import org.bukkit.entity.EntityType;

public class CustomMob {

    String id;
    EntityType entityType;
    String name;
    double health;
    /**
     * This class store data about custom mobs
     * @param id The id of the custom mob
     * @param entityType The entity type of the custom mob
     * @param name The name of the custom mob
     * @param health The health of the custom mob
     */
    public CustomMob(String id, EntityType entityType, String name, double health) {
        this.id = id;
        this.entityType = entityType;
        this.name = Utils.colorize(name);
        this.health = health;
    }

    public String getId() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setName(String name) {
        this.name = name;
    }
}
