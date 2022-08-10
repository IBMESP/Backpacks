package com.gmail.ibmesp1.bp.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MenuListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            final Menu menu = (Menu) event.getInventory().getHolder();
            menu.onClick(event);
        }
    }

    @EventHandler
    private void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            final Menu menu = (Menu) event.getInventory().getHolder();
            menu.onOpen(event);
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            final Menu menu = (Menu) event.getInventory().getHolder();
            menu.onClose(event);
        }
    }
}
