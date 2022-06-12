package com.gmail.ibmesp1.utils.backpacks.open;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class MenuOpen implements InventoryHolder {
    protected Inventory inventory;

    public MenuOpen(int size, String title){
        this.inventory = Bukkit.createInventory(this,size,title);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}