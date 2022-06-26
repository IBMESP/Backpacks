package com.gmail.ibmesp1.bp.utils.backpacks;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;

    public Menu(int size, String title){
        this.inventory = Bukkit.createInventory(this,size,title);
    }

    public abstract void onOpen(InventoryOpenEvent event);
    public abstract void onClose(InventoryCloseEvent event);
    public abstract void onClick(InventoryClickEvent event);

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}