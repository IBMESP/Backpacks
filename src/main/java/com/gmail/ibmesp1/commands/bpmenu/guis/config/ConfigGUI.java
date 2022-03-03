package com.gmail.ibmesp1.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ConfigGUI implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private HashMap<UUID,Inventory> playerBackpacks;

    public ConfigGUI(Backpacks plugin,HashMap<UUID, Inventory> playerBackpacks) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks);
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Configuration")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()) {
                case 13:{
                    if(plugin.getConfig().getBoolean("keepBackpack")){
                        plugin.getConfig().set("keepBackpack",false);
                        plugin.saveConfig();
                        player.sendMessage("Gamerule keepBackpack is now set: false");
                        player.closeInventory();
                    }else if(!plugin.getConfig().getBoolean("keepBackpack")){
                        plugin.getConfig().set("keepBackpack",true);
                        plugin.saveConfig();
                        player.sendMessage("Gamerule keepBackpack is now set: true");
                        player.closeInventory();
                    }
                    break;
                }
                case 20: {
                    Inventory sizeGUI = guis.sizeConfigGUI("small","Small");

                    player.openInventory(sizeGUI);
                    break;
                }
                case 22:{
                    Inventory sizeGUI = guis.sizeConfigGUI("medium","Medium");

                    player.openInventory(sizeGUI);
                    break;

                }
                case 24:{
                    Inventory sizeGUI = guis.sizeConfigGUI("large","Large");

                    player.openInventory(sizeGUI);
                    break;
                }
                case 35:{

                    if(!player.hasPermission("bp.admin")){
                        int easterEgg = (int) (Math.random() * 100);

                        Inventory gui = Bukkit.createInventory(player,3*9,"Backpack Menu");
                        bpEasterEgg = new BpEasterEgg(gui);

                        gui = guis.menuGUI(gui,bpEasterEgg,easterEgg);

                        player.openInventory(gui);
                        break;
                    }

                    int easterEgg = (int) (Math.random() * 100);
                    Inventory gui = Bukkit.createInventory(player,3*9,"Backpack Menu");
                    bpEasterEgg = new BpEasterEgg(gui);

                    gui = guis.menuOPGUI(gui,bpEasterEgg,easterEgg);

                    player.openInventory(gui);
                    break;
                }
            }
        }
    }

}
