package com.gmail.ibmesp1.bp.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.data.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SizeConfig implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private HashMap<UUID,HashMap<String,Inventory>> playerBackpacks;
    private DataManager bpcm;

    public SizeConfig(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, DataManager bpcm, List<Player> playerList) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {

        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.small"))) {
            switchCases(e,"smallSize","gui.small","small");
        }

        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.medium"))) {
            switchCases(e,"mediumSize","gui.medium","medium");
        }

        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.size.large"))) {
            switchCases(e, "largeSize", "gui.large", "large");
        }
    }

    private void switchCases(InventoryClickEvent e,String Size,String guiPath,String sizePath)
    {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        switch (e.getSlot()){
            case 1:{
                bpcm.getConfig().set(Size,1);
                player.closeInventory();
                String row = plugin.getLanguageString("gui.config.changeSize");
                player.sendMessage(ChatColor.RED + row.replace("%size",capitalizeFirstLetter(plugin.getLanguageString(guiPath))).replace("%num", "1"));
                bpcm.saveConfig();
                break;
            }
            case 2:{
                sizeConfig(player,sizePath,2,capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 3:{
                sizeConfig(player,sizePath,3,capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 5:{
                sizeConfig(player,sizePath,4,capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 6:{
                sizeConfig(player,sizePath,5,capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 7:{
                sizeConfig(player,sizePath,6,capitalizeFirstLetter(plugin.getLanguageString(guiPath)));
                break;
            }
            case 8:{
                Inventory configGUI = guis.configGUI(player);

                player.openInventory(configGUI);
            }
        }
    }
    private void sizeConfig(Player player,String path, int rows,String size){
        bpcm.getConfig().set(path + "Size",rows);
        player.closeInventory();
        String row = plugin.getLanguageString("gui.config.changeSize") + "s";
        player.sendMessage(ChatColor.RED + row.replace("%size", size).replace("%num", String.valueOf(rows)));
        bpcm.saveConfig();
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
