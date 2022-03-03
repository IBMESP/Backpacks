package com.gmail.ibmesp1.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class SizeConfig implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private HashMap<UUID,Inventory> playerBackpacks;

    public SizeConfig(Backpacks plugin,HashMap<UUID,Inventory> playerBackpacks) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks);
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Small Size")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    plugin.getConfig().set("smallSize",1);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "Small Backpack set to 1 row");
                    plugin.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"small",2,"Small");
                    break;
                }
                case 3:{
                    sizeConfig(player,"small",3,"Small");
                    break;
                }
                case 5:{
                    sizeConfig(player,"small",4,"Small");
                    break;
                }
                case 6:{
                    sizeConfig(player,"small",5,"Small");
                    break;
                }
                case 7:{
                    sizeConfig(player,"small",6,"Small");
                    break;
                }
                case 8:{
                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                }
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase("Medium Size")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    plugin.getConfig().set("mediumSize",1);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "Medium Backpack set to 1 row");
                    plugin.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"medium",2,"Medium");
                    break;
                }
                case 3:{
                    sizeConfig(player,"medium",3,"Medium");
                    break;
                }
                case 5:{
                    sizeConfig(player,"medium",4,"Medium");
                    break;
                }
                case 6:{
                    sizeConfig(player,"medium",5,"Medium");
                    break;
                }
                case 7:{
                    sizeConfig(player,"medium",6,"Medium");
                    break;
                }
                case 8:{
                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                }
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase("Large Size")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    plugin.getConfig().set("largeSize",1);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "Large Backpack set to 1 row");
                    plugin.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"large",2,"Large");
                    break;
                }
                case 3:{
                    sizeConfig(player,"medium",3,"Medium");
                    break;
                }
                case 5:{
                    sizeConfig(player,"medium",4,"Medium");
                    break;
                }
                case 6:{
                    sizeConfig(player,"medium",5,"Medium");
                    break;
                }
                case 7:{
                    sizeConfig(player,"medium",6,"Medium");
                    break;
                }
                case 8:{
                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                }
            }
        }
    }

    private void sizeConfig(Player player,String path, int rows,String size){
        plugin.getConfig().set(path + "Size",rows);
        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + size +" Backpack set to " + rows + " rows");
        plugin.saveConfig();
    }
}
