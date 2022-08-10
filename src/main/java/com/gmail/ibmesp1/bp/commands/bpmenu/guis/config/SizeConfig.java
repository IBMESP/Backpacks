package com.gmail.ibmesp1.bp.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ibcore.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SizeConfig implements Listener {

    private final Backpacks plugin;
    private final GUIs guis;
    private final DataManager bpcm;

    public SizeConfig(Backpacks plugin, DataManager bpcm,GUIs guis) {
        this.plugin = plugin;
        this.guis = guis;
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if(e.getClickedInventory() == null)
            return;

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.size.small")))) {
            switchCases(e,"smallSize","gui.small","small");
        }

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.size.medium")))) {
            switchCases(e,"mediumSize","gui.medium","medium");
        }

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.size.large")))) {
            switchCases(e, "largeSize", "gui.large", "large");
        }
    }

    private void switchCases(InventoryClickEvent e,String Size,String guiPath,String sizePath)
    {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        switch (e.getSlot()){
            case 1:{
                if(sizeConfigCheck(player, sizePath, 1))
                    return;

                bpcm.getConfig().set(Size,1);
                player.closeInventory();
                String row = plugin.getLanguageString("gui.config.changeSize");
                player.sendMessage(ChatColor.RED + row.replace("%size%", Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath))).replace("%num%", "1"));
                bpcm.saveConfig();
                break;
            }
            case 2:{
                sizeConfig(player,sizePath,2,Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 3:{
                sizeConfig(player,sizePath,3,Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 5:{
                sizeConfig(player,sizePath,4,Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 6:{
                sizeConfig(player,sizePath,5,Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 7:{
                sizeConfig(player,sizePath,6,Utils.capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 8:{
                Inventory configGUI = guis.configGUI(player);

                player.openInventory(configGUI);
            }
        }
    }
    private void sizeConfig(Player player,String path, int rows,String size){
        if(sizeConfigCheck(player, path, rows))
            return;

        bpcm.getConfig().set(path + "Size",rows);
        player.closeInventory();
        String row = plugin.getLanguageString("gui.config.changeSize") + "s";
        player.sendMessage(ChatColor.RED + row.replace("%size%", size).replace("%num%", String.valueOf(rows)));
        bpcm.saveConfig();
    }

    private boolean sizeConfigCheck(Player player, String size,int rows){
        switch (size) {
            case "small":
                if (rows >= bpcm.getConfig().getInt("mediumSize")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.config.small"));
                    return true;
                }
                return false;
            case "medium":
                if (rows >= bpcm.getConfig().getInt("largeSize")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.config.large"));
                    return true;
                }

                if (rows <= bpcm.getConfig().getInt("smallSize")){
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.config.small"));
                    return true;
                }
                    return false;
            case "large":
                if (rows <= bpcm.getConfig().getInt("mediumSize")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.config.large"));
                    return true;
                }
                return false;
        }
        return true;
    }
}
