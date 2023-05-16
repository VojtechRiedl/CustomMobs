package me.histal.custommobs.mobs;

import org.bukkit.entity.EntityType;

public class CustomMob {

    String id;
    EntityType entityType;
    String name;
    double health;

    public CustomMob(String id, EntityType entityType, String name, double health) {
        this.id = id;
        this.entityType = entityType;
        this.name = name;
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

}
