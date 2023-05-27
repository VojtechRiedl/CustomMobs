package me.histal.custommobs.mobs.events;

import me.histal.custommobs.mobs.CustomMob;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddCustomMobEvent extends Event implements Cancellable {

    private CustomMob mob;
    public AddCustomMobEvent(CustomMob mob) {
        this.mob = mob;
    }



    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    public CustomMob getMob() {
        return mob;
    }

    public void setMob(CustomMob mob) {
        this.mob = mob;
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
