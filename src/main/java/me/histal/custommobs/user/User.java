package me.histal.custommobs.user;

import org.bukkit.entity.Player;

public class User {

    Player player;
    int killedMobs;

    public User(int killedMobs) {
        this.killedMobs = killedMobs;
    }

    public User(){
        this(0);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getKilledMobs() {
        return killedMobs;
    }

    public void setKilledMobs(int killedMobs) {
        this.killedMobs = killedMobs;
    }
    public void addKilledMob(){
        this.killedMobs++;
    }
    public boolean hasMaxAllowedKills(){
        return this.killedMobs >= 10;
    }
}
