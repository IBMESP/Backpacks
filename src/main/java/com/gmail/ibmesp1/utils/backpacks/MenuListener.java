package com.gmail.ibmesp1.utils.backpacks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MenuListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu holder) {
            holder.onClick(event);
        }
    }

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof Menu holder) {
            holder.onOpen(event);
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Menu holder) {
            holder.onClose(event);
        }
    }
}
