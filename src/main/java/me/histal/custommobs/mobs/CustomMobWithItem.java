package me.histal.custommobs.mobs;

import org.bukkit.entity.EntityType;

public class CustomMobWithItem extends CustomMob{

    MobDrop mobDrop;

    public CustomMobWithItem(String id, EntityType entityType, String name, double health, MobDrop mobDrop) {
        super(id, entityType, name, health);
        this.mobDrop = mobDrop;
    }

    public MobDrop getMobDrop() {
        return mobDrop;
    }

    public void setMobDrop(MobDrop mobDrop) {
        this.mobDrop = mobDrop;
    }
}
