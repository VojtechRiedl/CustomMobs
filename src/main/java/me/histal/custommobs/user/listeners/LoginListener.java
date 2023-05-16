package me.histal.custommobs.user.listeners;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

    private CustomMobs plugin;

    public LoginListener(){
        this.plugin = CustomMobs.getInstance();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        User user = plugin.getUserManager().createOrGetUser(player.getName());
        user.setPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        User user = plugin.getUserManager().getUser(player.getName());
        user.setPlayer(null);
    }

}
