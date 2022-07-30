package com.gmail.ibmesp1.bp.utils.backpacks;

import com.gmail.ibmesp1.bp.Backpacks;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;
import java.util.UUID;

public class BackpackGUI extends Menu{

    private Player viewer;
    private UUID ownerId;
    private BackpackManager backpackManager;
    private int size;
    private String title;
    private String key;
    private Backpacks plugin;

    public BackpackGUI(int size, String title, Player viewer, UUID ownerId,String key, BackpackManager backpackManager, Backpacks plugin) {
        super(size, title);
        this.viewer = viewer;
        this.ownerId = ownerId;
        this.key = key;
        this.backpackManager = backpackManager;
        this.plugin = plugin;

        Inventory backpack = this.backpackManager.getInventory(ownerId,key);
        this.inventory.setContents(backpack.getContents());
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {}

    @Override
    public void onClose(InventoryCloseEvent event) {
        if(!viewer.getUniqueId().equals(ownerId)) return;

        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(ownerId + ".").getKeys(false);
        for (String key:set){
            if(plugin.playerBackpack.get(ownerId).get(key) == event.getInventory())
                this.backpackManager.saveInventory(ownerId, backpackManager.getInventory(ownerId,key),key);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
