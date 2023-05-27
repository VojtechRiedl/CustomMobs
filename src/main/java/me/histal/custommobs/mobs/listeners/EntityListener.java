package me.histal.custommobs.mobs.listeners;

import me.histal.custommobs.CustomMobs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityListener implements Listener {

    private CustomMobs plugin;

    public EntityListener(){
        this.plugin = CustomMobs.getInstance();
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(!plugin.getMobsManager().getMobController().isAllowedMob(e.getEntityType())){
            return;
        }
        LivingEntity entity = e.getEntity();

        if(!plugin.getMobsManager().isCustomMob(entity)){
            return;
        };

        plugin.getMobsManager().getMobController().removeItemFromDrops(entity, e.getDrops());

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){
        if(e.isCancelled()) return;
        System.out.println(e.getEntityType());
        if(!plugin.getMobsManager().getMobController().isAllowedMob(e.getEntityType())){
            return;
        }

        plugin.getMobsManager().spawnMob(e.getEntity());

    }

}
