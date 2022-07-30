package com.gmail.ibmesp1.bp.commands.bpmenu.guis.create;


import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SizeGUI implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private HashMap<UUID,HashMap<String,Inventory>> playerBackpacks;
    private DataManager bpcm;
    private List<Player> playerList;

    public SizeGUI(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, DataManager bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
        this.playerList = plugin.playerList;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.items.size"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()) {
                case 2:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.small")),0);

                    player.openInventory(sizeGUI);
                    break;
                }
                case 4:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.medium")),0);

                    player.openInventory(sizeGUI);
                    break;
                }
                case 6:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(capitalizeFirstLetter(plugin.getLanguageString("gui.large")),0);

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
