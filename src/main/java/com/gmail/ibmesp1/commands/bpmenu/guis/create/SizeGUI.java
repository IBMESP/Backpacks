package com.gmail.ibmesp1.commands.bpmenu.guis.create;


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

public class SizeGUI implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private HashMap<UUID,Inventory> playerBackpacks;

    public SizeGUI(Backpacks plugin,HashMap<UUID,Inventory> playerBackpacks) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks);
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Size")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();


            switch (e.getSlot()) {
                case 2:{
                    Inventory sizeGUI = guis.sizeGUI("Small");

                    player.openInventory(sizeGUI);
                    break;
                }
                case 4:{
                    Inventory sizeGUI = guis.sizeGUI("Medium");

                    player.openInventory(sizeGUI);
                    break;
                }
                case 6:{
                    Inventory sizeGUI = guis.sizeGUI("Large");

                    player.openInventory(sizeGUI);
                    break;
                }
                case 8:{

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
