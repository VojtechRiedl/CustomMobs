package me.histal.custommobs.mobs;

import me.histal.custommobs.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobDrop {

    private Material material;
    private String displayName;
    private float dropChance;
    /**
     * This class store data about custom mob drops
     * @param material The material of the drop
     * @param displayName The display name of the drop
     * @param dropChance The drop chance of the drop
     */
    public MobDrop(Material material, String displayName, float dropChance) {
        this.material = material;
        this.displayName = Utils.colorize(displayName);
        this.dropChance = dropChance <= 0.0 ? 100.0f : dropChance;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public float getDropChance() {
        return dropChance;
    }
    /**
     * This method returns the drop as an item
     * @return The drop built as an item
     */
    public ItemStack getItem(){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        if(displayName != null){
            itemMeta.setDisplayName(displayName);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

}
