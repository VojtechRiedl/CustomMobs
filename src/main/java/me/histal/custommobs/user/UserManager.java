package me.histal.custommobs.user;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.user.listeners.LoginListener;

import java.util.HashMap;

public class UserManager {

    private CustomMobs plugin;

    HashMap<String, User> users;

    public UserManager(){
        this.plugin = CustomMobs.getInstance();
        loadData();

        plugin.getServer().getPluginManager().registerEvents(new LoginListener(),plugin);
    }

    public void loadData(){
        users = new HashMap<>();
    }

    public User createOrGetUser(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }
        User user = getUser(name);
        if(user != null){
            return user;
        }
        user = new User();
        users.put(name.toLowerCase(), user);
        return user;
    }


    public User getUser(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }
        if(!existUser(name)){
            return null;
        }

        return users.get(name);
    }

    public boolean existUser(String name) {
        return users.containsKey(name);
    }


}
