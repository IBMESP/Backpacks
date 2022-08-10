package com.gmail.ibmesp1.bp.commands.bpmenu.guis;

import com.gmail.ibmesp1.bp.Backpacks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BpMenuEvents implements Listener {

    private final Backpacks plugin;
    private final GUIs guis;

    public BpMenuEvents(Backpacks plugin,GUIs guis) {
        this.plugin = plugin;
        this.guis = guis;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if(e.getClickedInventory() == null)
            return;

        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")))){
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();


            switch (e.getSlot()){
                case 11: {
                    Inventory createGUI = guis.createGUI(player);

                    player.openInventory(createGUI);
                    break;
                }
                case 13: {
                    if(!player.hasPermission("bp.admin")){
                        break;
                    }

                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                    break;
                }

                case 15: {
                    Inventory deleteGUI = guis.deleteGUI(0);
                    plugin.playerPage.put(player.getUniqueId(),0);
                    player.openInventory(deleteGUI);
                    break;
                }
            }
        }
    }
}
