package com.gmail.ibmesp1.utils.backpacks.open;

import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class BackpackOpen extends MenuOpen {

    private Player viewer;
    private UUID ownerId;
    private BackpackManager backpackManager;
    private int size;
    private String title;

    public BackpackOpen(int size, String title, Player viewer, UUID ownerId, BackpackManager backpackManager) {
        super(size, title);
        this.viewer = viewer;
        this.ownerId = ownerId;
        this.backpackManager = backpackManager;

        Inventory backpack = this.backpackManager.getInventory(ownerId);
        this.inventory.setContents(backpack.getContents());
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
