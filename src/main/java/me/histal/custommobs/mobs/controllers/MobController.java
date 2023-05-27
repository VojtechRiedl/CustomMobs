package me.histal.custommobs.mobs.controllers;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.Utils;
import me.histal.custommobs.mobs.CustomMob;
import me.histal.custommobs.mobs.CustomMobWithItem;
import me.histal.custommobs.mobs.MobBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MobController {


    private CustomMobs plugin;

    public MobController() {
        this.plugin = CustomMobs.getInstance();
    }
    /**
     * Check if the entity type is allowed
     * @param entityType the entity type to check
     * @return true if the entity type is allowed
     */
    public boolean isAllowedMob(EntityType entityType) {
        return Arrays.asList(MobBuilder.getPossibleTypes()).contains(entityType);
    }

    /**
     * get a list of custom mobs by type
     * @param entityType the entity type to get custom mobs
     * @return a list of custom mobs
     */
    public List<CustomMob> getMobsByType(EntityType entityType) {
        return plugin.getMobsManager().getMobs().stream().filter(mob -> mob.getEntityType().equals(entityType)).collect(Collectors.toList());
    }

    /**
     * get a random custom mob by type
     * @param mobs the list of custom mobs
     * @return a random custom mob
     */
    public CustomMob getRandomMob(List<CustomMob> mobs) {
        return mobs.get(new Random().nextInt(mobs.size()));
    }

    /**
     * remove the item from the drops of the entity if it is a custom mob with item drop and the drop chance is not reached
     * @param entity the entity to check if it is a custom mob with item drop
     * @param drops the list of drops of the entity
     * @return true if the entity is a custom mob with item drop and the drop chance is reached
     */
    public boolean removeItemFromDrops(Entity entity, List<ItemStack> drops) {
        if (!(entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON)) {
            return false;
        }
        String mobId = entity.getMetadata("custom-mob").get(0).asString();

        if(!plugin.getMobsManager().existMob(mobId)){
            return false;
        }
        CustomMob mob = plugin.getMobsManager().getMob(mobId);
        if(!(mob instanceof CustomMobWithItem)){
            return false;
        }
        CustomMobWithItem mobWithItem = (CustomMobWithItem) mob;

        drops.removeIf(item -> item.getType().equals(mobWithItem.getMobDrop().getMaterial()));

        if(Utils.calculateChance(mobWithItem.getMobDrop().getDropChance())){
            if(!drops.contains(mobWithItem.getDropAsItem())){
                drops.add(mobWithItem.getDropAsItem());
            }
            return false;
        };
        return true;
    }
    /**
     * check if the entity has a killer
     * @param entity the entity to check
     * @return true if the entity has a killer
     */
    public boolean hasKiller(LivingEntity entity){
        return entity.getKiller() != null;
    }

}
