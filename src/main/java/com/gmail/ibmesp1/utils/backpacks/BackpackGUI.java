package com.gmail.ibmesp1.utils.backpacks;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class BackpackGUI extends Menu{

    private Player viewer;
    private UUID ownerId;
    private BackpackManager backpackManager;
    private int size;
    private String title;

    public BackpackGUI(int size, String title,Player viewer,UUID ownerId,BackpackManager backpackManager) {
        super(size, title);
        this.viewer = viewer;
        this.ownerId = ownerId;
        this.backpackManager = backpackManager;

        Inventory backpack = this.backpackManager.getInventory(ownerId);
        this.inventory.setContents(backpack.getContents());
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        if(!viewer.getUniqueId().equals(ownerId)) return;

        this.backpackManager.saveInventory(ownerId, backpackManager.getInventory(ownerId));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(viewer.getUniqueId().equals(ownerId)) return;

        event.setCancelled(true);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
