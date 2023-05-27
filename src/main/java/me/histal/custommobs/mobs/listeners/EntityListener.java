package me.histal.custommobs.mobs.listeners;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.Utils;
import me.histal.custommobs.user.User;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        if(!plugin.getMobsManager().getMobController().hasKiller(entity)) return;
        Player killer = entity.getKiller();

        User user = plugin.getUserManager().getUser(killer.getName());
        if(user == null) return;
        if(user.hasMaxAllowedKills()) return;
        user.addKilledMob();
        killer.sendMessage(Utils.colorize("&aZískáváš odměnu!"));

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){
        if(e.isCancelled()) return;
        if(!plugin.getMobsManager().getMobController().isAllowedMob(e.getEntityType())){
            return;
        }

        plugin.getMobsManager().updateMob(e.getEntity());

    }

}
