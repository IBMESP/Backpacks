package com.gmail.ibmesp1.bp.commands.bpmenu.guis.config;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConfigGUI implements Listener {

    private Backpacks plugin;
    private GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private HashMap<UUID,HashMap<String,Inventory>> playerBackpacks;
    private DataManager bpcm;

    public ConfigGUI(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, DataManager bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.items.configuration"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()) {
                case 13:{
                    if(bpcm.getConfig().getBoolean("keepBackpack")){
                        bpcm.getConfig().set("keepBackpack",false);
                        bpcm.saveConfig();
                        String gamerule = plugin.getLanguageString("gui.config.gamerule");
                        player.sendMessage(ChatColor.RED + gamerule.replace("%bool", "false"));
                        player.closeInventory();
                    }else if(!bpcm.getConfig().getBoolean("keepBackpack")){
                        bpcm.getConfig().set("keepBackpack",true);
                        bpcm.saveConfig();
                        String gamerule = plugin.getLanguageString("gui.config.gamerule");
                        player.sendMessage(ChatColor.RED + gamerule.replace("%bool", "true"));
                        player.closeInventory();
                    }
                    break;
                }
                case 20: {
                    Inventory sizeGUI = guis.sizeConfigGUI("small",plugin.getLanguageString("gui.size.small"));

                    player.openInventory(sizeGUI);
                    break;
                }
                case 22:{
                    Inventory sizeGUI = guis.sizeConfigGUI("medium",plugin.getLanguageString("gui.size.medium"));

                    player.openInventory(sizeGUI);
                    break;

                }
                case 24:{
                    Inventory sizeGUI = guis.sizeConfigGUI("large",plugin.getLanguageString("gui.size.large"));

                    player.openInventory(sizeGUI);
                    break;
                }
                case 35:{

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
}
