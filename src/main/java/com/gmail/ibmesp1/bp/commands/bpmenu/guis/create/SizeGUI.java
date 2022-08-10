package com.gmail.ibmesp1.bp.commands.bpmenu.guis.create;


import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.ibcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SizeGUI implements Listener {

    private final Backpacks plugin;
    private final GUIs guis;

    public SizeGUI(Backpacks plugin,GUIs guis) {
        this.plugin = plugin;
        this.guis = guis;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.items.size")))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();

            switch (e.getSlot()) {
                case 2:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.small")),0);

                    player.openInventory(sizeGUI);
                    break;
                }
                case 4:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.medium")),0);

                    player.openInventory(sizeGUI);
                    break;
                }
                case 6:{
                    plugin.playerPage.put(player.getUniqueId(),0);
                    Inventory sizeGUI = guis.sizeGUI(Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.large")),0);

                    player.openInventory(sizeGUI);
                    break;
                }
                case 8:{

                    BpEasterEgg bpEasterEgg;
                    if(!player.hasPermission("bp.admin")){
                        int easterEgg = (int) (Math.random() * 100);

                        Inventory gui = Bukkit.createInventory(player,3*9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
                        bpEasterEgg = new BpEasterEgg();

                        gui = guis.menuGUI(gui, bpEasterEgg,easterEgg);

                        player.openInventory(gui);
                        break;
                    }

                    int easterEgg = (int) (Math.random() * 100);
                    Inventory gui = Bukkit.createInventory(player,3*9, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
                    bpEasterEgg = new BpEasterEgg();

                    gui = guis.menuOPGUI(gui, bpEasterEgg,easterEgg);

                    player.openInventory(gui);
                    break;
                }
            }
        }
    }
}
