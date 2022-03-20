package com.gmail.ibmesp1.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.data.DataManger;
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
    private DataManger bpcm;

    public SizeConfig(Backpacks plugin, HashMap<UUID,Inventory> playerBackpacks, DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.small"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    bpcm.getConfig().set("smallSize",1);
                    player.closeInventory();
                    String row = plugin.getLanguageString("gui.config.changeSize");
                    player.sendMessage(ChatColor.RED + row.replace("%size",capitalizeFirstLetter(plugin.getLanguageString("gui.small"))).replace("%num", "1"));
                    bpcm.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"small",2,capitalizeFirstLetter(plugin.getLanguageString("gui.small")));
                    break;
                }
                case 3:{
                    sizeConfig(player,"small",3,capitalizeFirstLetter(plugin.getLanguageString("gui.small")));
                    break;
                }
                case 5:{
                    sizeConfig(player,"small",4,capitalizeFirstLetter(plugin.getLanguageString("gui.small")));
                    break;
                }
                case 6:{
                    sizeConfig(player,"small",5,capitalizeFirstLetter(plugin.getLanguageString("gui.small")));
                    break;
                }
                case 7:{
                    sizeConfig(player,"small",6,capitalizeFirstLetter(plugin.getLanguageString("gui.small")));
                    break;
                }
                case 8:{
                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                }
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.medium"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    bpcm.getConfig().set("mediumSize",1);
                    player.closeInventory();
                    String row = plugin.getLanguageString("gui.config.changeSize");
                    player.sendMessage(ChatColor.RED + row.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.medium"))).replace("%num", "1"));
                    bpcm.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"medium",2,capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));
                    break;
                }
                case 3:{
                    sizeConfig(player,"medium",3,capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));
                    break;
                }
                case 5:{
                    sizeConfig(player,"medium",4,capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));
                    break;
                }
                case 6:{
                    sizeConfig(player,"medium",5,capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));
                    break;
                }
                case 7:{
                    sizeConfig(player,"medium",6,capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));
                    break;
                }
                case 8:{
                    Inventory configGUI = guis.configGUI(player);

                    player.openInventory(configGUI);
                }
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.large"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()){
                case 1:{
                    bpcm.getConfig().set("largeSize",1);
                    player.closeInventory();
                    String row = plugin.getLanguageString("gui.config.changeSize");
                    player.sendMessage(ChatColor.RED + row.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.large").replace("%num", "1"))));
                    bpcm.saveConfig();
                    break;
                }
                case 2:{
                    sizeConfig(player,"large",2,capitalizeFirstLetter(plugin.getLanguageString("gui.large")));
                    break;
                }
                case 3:{
                    sizeConfig(player,"large",3,capitalizeFirstLetter(plugin.getLanguageString("gui.large")));
                    break;
                }
                case 5:{
                    sizeConfig(player,"large",4,capitalizeFirstLetter(plugin.getLanguageString("gui.large")));
                    break;
                }
                case 6:{
                    sizeConfig(player,"large",5,capitalizeFirstLetter(plugin.getLanguageString("gui.large")));
                    break;
                }
                case 7:{
                    sizeConfig(player,"large",6,capitalizeFirstLetter(plugin.getLanguageString("gui.large")));
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
        bpcm.getConfig().set(path + "Size",rows);
        player.closeInventory();
        String row = plugin.getLanguageString("gui.config.changeSize") + "s";
        player.sendMessage(size);
        player.sendMessage(ChatColor.RED + row.replace("%size", size).replace("%num", String.valueOf(rows)));
        bpcm.saveConfig();
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
