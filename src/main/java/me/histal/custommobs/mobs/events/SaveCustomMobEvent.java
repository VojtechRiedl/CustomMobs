package me.histal.custommobs.mobs.events;

import me.histal.custommobs.mobs.CustomMob;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SaveCustomMobEvent extends Event implements Cancellable {
    private CustomMob mob;
    private boolean cancelled;

    public SaveCustomMobEvent(CustomMob mob) {
        this.mob = mob;
    }

    public CustomMob getMob() {
        return mob;
    }
    public CustomMob setMob(CustomMob mob) {
        return this.mob = mob;
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
