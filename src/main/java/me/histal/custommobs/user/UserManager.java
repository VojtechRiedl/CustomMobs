package me.histal.custommobs.user;

import me.histal.custommobs.CustomMobs;
import me.histal.custommobs.user.listeners.LoginListener;

import java.util.HashMap;

public class UserManager {

    private CustomMobs plugin;

    private HashMap<String, User> users;

    public UserManager(){
        this.plugin = CustomMobs.getInstance();
        loadData();

        plugin.getServer().getPluginManager().registerEvents(new LoginListener(),plugin);
    }

    public void loadData(){
        users = new HashMap<>();
    }
    /**
     * Creates a user if they don't have one and returns it or returns the user if they already have one
     * @param name The name of the user
     * @return The user that was successfully created
     */
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

    /**
     * checks if a user exists with the given name and returns it if it does exist otherwise returns null
     * @param name The name of the user
     * @return The user with the given name
     */
    public User getUser(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }
        if(!existUser(name)){
            return null;
        }
        return users.get(name.toLowerCase());
    }

    public boolean existUser(String name) {
        return users.containsKey(name);
    }


}
