package me.histal.custommobs.mobs;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class CustomMobWithItem extends CustomMob{

    private MobDrop mobDrop;

    /**
     * This class store data about custom mobs with item drops
     * @param id The id of the custom mob
     * @param entityType The entity type of the custom mob
     * @param name The name of the custom mob
     * @param health The health of the custom mob
     * @param mobDrop The item drop of the custom mob
     */
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


    public ItemStack getDropAsItem(){
        if(mobDrop == null)
            return null;
        return mobDrop.getItem();
    }
}
