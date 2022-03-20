package com.gmail.ibmesp1.commands.bpmenu.guis.create;


import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.data.DataManger;
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
    private DataManger bpcm;

    public SizeGUI(Backpacks plugin,HashMap<UUID,Inventory> playerBackpacks,DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.items.size"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();


            switch (e.getSlot()) {
                case 2:{
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.small")));

                    player.openInventory(sizeGUI);
                    break;
                }
                case 4:{
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.medium")));

                    player.openInventory(sizeGUI);
                    break;
                }
                case 6:{
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.large")));

                    player.openInventory(sizeGUI);
                    break;
                }
                case 8:{

                    if(!player.hasPermission("bp.admin")){
                        int easterEgg = (int) (Math.random() * 100);

                        Inventory gui = Bukkit.createInventory(player,3*9,plugin.getLanguageString("gui.title"));
                        bpEasterEgg = new BpEasterEgg(gui);

                        gui = guis.menuGUI(gui,bpEasterEgg,easterEgg);

                        player.openInventory(gui);
                        break;
                    }

                    int easterEgg = (int) (Math.random() * 100);
                    Inventory gui = Bukkit.createInventory(player,3*9,plugin.getLanguageString("gui.title"));
                    bpEasterEgg = new BpEasterEgg(gui);

                    gui = guis.menuOPGUI(gui,bpEasterEgg,easterEgg);

                    player.openInventory(gui);
                    break;
                }
            }
        }
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
