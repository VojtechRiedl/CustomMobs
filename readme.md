<h1>CustomMobs</h1>

Spigot plugin for creating custom mobs in Minecraft and give bonuses to players for killing them.

<h2>Commands</h2>

<h3>/custommobs</h3>

Main command for the plugin. All other commands are subcommands of this command.

<h4>Subcommands</h4>

<h5>create</h5>

Creates a new custom mob. The mob will spawn as type of entity with the specified health and display name.

Usage: `/custommobs add [entity_type] [health] [display_name]`

Permission: `none`

<h5>reload</h5>

Reloads the plugin's config file.

Usage: `/custommobs reload`

Permission: `custommobs.reload`

<h2>Configurations</h2>

<h3>custom-mobs.yml</h3>

This file contains all the custom mobs that have been created. Each mob has a unique id, and contains the following information:

- `type`: The type of entity that the mob will spawn as
- `name`: The name that will be displayed above the mob
- `health`: The amount of health that the mob will have

<h4>Optional</h4>

- `material`: The item that the mob will drop when killed
- `display-name`: The name of the item that
- `drop-chance`:  The chance that the mob will drop the item

<a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html">List of entity types</a>

<h3>Congifuration Example</h3>

```yaml
example-mob:
    type: ZOMBIE
    health: 100.0
    name: "Custom Zombie"
    item-in-hand:
        material: DIAMOND
        display-name: "Diamond"
        drop-chance: 0.5
zombie-king:
    type: ZOMBIE
    health: 50
    name: "&cZombie King"
```

<h2>API</h2>

<h3>Get a user</h3>

```JAVA

public class ExamplePlugin extends JavaPlugin {

    public void example() {
        // get a user by name
        CustomMobs.getInstance().getUserManager().getUser("player_name");
    }
}
```

<h3>Creating a new custom mob</h3>

```JAVA
public class ExamplePlugin extends JavaPlugin {

    public void example() {
        // Create a new custom mob
        MobBuilder mobBuilder = new MobBuilder("Example Mob", "zombie", 100);

        // Set the item that the mob will drop (optional)
        mobBuilder.createMobDrop("sword", "iron_sword", 0.5);

        // build mob
        CustomMob newCustomMob = mobBuilder.build();
        
        // Register the mob
        CustomMobs.getInstance().getMobsManager().registerMob(newCustomMob);
    }
}

``` 

<h3>Get a custom mob</h3>

```JAVA

public class ExampleListener implements Listener {

    @EventHandler
    public void onEntityDied(EntityDeathEvent event) {
        // Get a custom mob by id
        CustomMob customMob = CustomMobs.getInstance().getMobsManager().getMob("example-mob");
        
        // Get a custom mob from an entity
        CustomMob customMob2 = CustomMobs.getInstance().getMobsManager().getMob(event.getEntity());
        
    }
}

``` 

<h3>Save a custom mob</h3>

```JAVA

public class ExamplePlugin extends JavaPlugin {

    public void example() {
        // Create a new custom mob
        MobBuilder mobBuilder = new MobBuilder("Example Mob", "zombie", 100);

        // Set the item that the mob will drop (optional)
        mobBuilder.createMobDrop("sword", "iron_sword", 0.5);

        // build mob
        CustomMob newCustomMob = mobBuilder.build();

        // Register the mob
        CustomMobs.getInstance().getMobsManager().registerMob(newCustomMob);
        
        // Save the mob to the config
        CustomMobs.getInstance().getMobsManager().saveMob(newCustomMob);
    }
}
    
```

<h3>Events</h3>

```JAVA
public class ExampleListener implements Listener {

    @EventHandler
    public void onMobSpawn(SpawnCustomMobEvent event) {

    }

    @EventHandler
    public void onMobAdded(AddCustomMobEvent event) {

    }
    
    @EventHandler
    public void onMobSave(SaveCustomMobEvent event) {
    
    }
}

```

