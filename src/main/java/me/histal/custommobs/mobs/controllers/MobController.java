package me.histal.custommobs.mobs.controllers;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.Utils;
import me.histal.custommobs.mobs.CustomMob;
import me.histal.custommobs.mobs.CustomMobWithItem;
import me.histal.custommobs.mobs.MobBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
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

    public boolean isAllowedMob(EntityType entityType) {
        return Arrays.asList(MobBuilder.getPossibleTypes()).contains(entityType);
    }

    public List<CustomMob> getMobsByType(EntityType entityType) {
        return plugin.getMobsManager().getMobs().stream().filter(mob -> mob.getEntityType().equals(entityType)).collect(Collectors.toList());
    }

    public CustomMob getRandomMob(List<CustomMob> mobs) {
        return mobs.get(new Random().nextInt(mobs.size()));
    }


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

        if(!Utils.calculateChance(mobWithItem.getMobDrop().getDropChance())){
            return false;
        };

        drops.remove(mobWithItem.getDropAsItem());
        return true;
    }

}
