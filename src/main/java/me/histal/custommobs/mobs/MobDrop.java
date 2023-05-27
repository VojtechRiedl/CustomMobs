package me.histal.custommobs.mobs;

import me.histal.custommobs.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobDrop {

    Material material;
    String displayName;
    float dropChance;

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
