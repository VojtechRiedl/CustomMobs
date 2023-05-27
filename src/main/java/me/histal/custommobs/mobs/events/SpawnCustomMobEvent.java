package me.histal.custommobs.mobs.events;

import me.histal.custommobs.mobs.CustomMob;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnCustomMobEvent extends Event implements Cancellable {

    private CustomMob mob;
    private Entity entity;
    private boolean cancelled;


    /**
     * This event is called when a custom mob is spawned
     * @param entity The entity that is being spawned
     * @param mob The custom mob that is being spawned as entity
     */
    public SpawnCustomMobEvent(Entity entity,CustomMob mob) {
        this.mob = mob;
        this.entity = entity;
    }

    public CustomMob getMob() {
        return mob;
    }
    public CustomMob setMob(CustomMob mob) {
        return this.mob = mob;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }


    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }


}
