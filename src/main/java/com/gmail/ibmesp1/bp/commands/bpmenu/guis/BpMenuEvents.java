package com.gmail.ibmesp1.bp.commands.bpmenu.guis;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class BpMenuEvents implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private HashMap<UUID,HashMap<String,Inventory>> playerBackpacks;
    private DataManager bpcm;

    public BpMenuEvents(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, DataManager bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.bpcm = bpcm;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.title"))){
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
