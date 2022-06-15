package com.gmail.ibmesp1.commands.bpmenu.guis.delete;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.data.DataManger;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.UUID;

public class DeleteGUI implements Listener {

    private final Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpacks;
    private HashMap<UUID, String> customName;
    private boolean head;
    private final GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private DataManger bpcm;


    public DeleteGUI(Backpacks plugin,HashMap<UUID,Inventory> playerBackpacks,HashMap<UUID, String> customName,DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.customName = customName;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.delete.title"))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            int[] glass_slots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52};

            if (e.getSlot() == 49) {

                if (!player.hasPermission("bp.admin")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.perm"));
                    return;
                }

                player.closeInventory();
                customName.put(player.getUniqueId(), player.getCustomName());
                player.setCustomName("delete");
                player.sendMessage(ChatColor.GRAY + plugin.getLanguageString("gui.browser"));
                return;
            }

            if (e.getSlot() == 53) {

                if (!player.hasPermission("bp.admin")) {
                    int easterEgg = (int) (Math.random() * 100);

                    Inventory gui = Bukkit.createInventory(player, 3 * 9, plugin.getLanguageString("gui.title"));
                    bpEasterEgg = new BpEasterEgg(gui);

                    gui = guis.menuGUI(gui, bpEasterEgg, easterEgg);

                    player.openInventory(gui);
                    return;
                }

                int easterEgg = (int) (Math.random() * 100);
                Inventory gui = Bukkit.createInventory(player, 3 * 9, plugin.getLanguageString("gui.title"));
                bpEasterEgg = new BpEasterEgg(gui);

                gui = guis.menuOPGUI(gui, bpEasterEgg, easterEgg);

                player.openInventory(gui);
                return;
            }

            for (int i = 0; i < glass_slots.length; i++) {
                if (e.getSlot() == glass_slots[i]) {
                    head = false;
                    i = glass_slots.length;
                } else {
                    head = true;
                }
            }
            if (head) {
                try {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (!playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
                                return;
                            }

                            Inventory prevInventory = playerBackpacks.get(target.getUniqueId());

                            int size = prevInventory.getSize();

                            for (int i = 0; i < size; i++) {
                                try {
                                    target.getLocation().getWorld().dropItem(target.getLocation(), prevInventory.getItem(i));
                                } catch (IllegalArgumentException | NullPointerException ex) {
                                }
                            }

                            playerBackpacks.remove(target.getUniqueId());
                            BackpackManager.savePlayerBackPacks(target.getUniqueId());
                            player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("delete.deleted"));
                            player.closeInventory();
                        } else {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.perm"));
                        }
                        return;
                    }

                    if (!playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("delete.target.notBackpack"));
                        return;
                    }

                    Inventory prevInventory = playerBackpacks.get(target.getUniqueId());
                    String title = plugin.getLanguageString("config.title");

                    if(target.getOpenInventory().getTitle().equals(title.replace("%player",target.getName()))){
                        target.closeInventory();
                    }

                    int size = prevInventory.getSize();

                    for (int i = 0; i < size; i++) {
                        try {
                            target.getLocation().getWorld().dropItem(target.getLocation(), prevInventory.getItem(i));
                        } catch (IllegalArgumentException | NullPointerException ex) {
                        }
                    }

                    playerBackpacks.remove(target.getUniqueId());
                    BackpackManager.savePlayerBackPacks(target.getUniqueId());
                    if (!(player.getUniqueId() == target.getUniqueId())) {
                        target.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.deletedBy") + player.getName());
                        player.sendMessage(ChatColor.GREEN + target.getName() + plugin.getLanguageString("delete.target.deleted"));
                    } else if (player.getUniqueId() == target.getUniqueId()) {
                        player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("delete.deleted"));
                    }
                    player.closeInventory();

                } catch (Exception ignored) {
                }
            }
        }

    }
}
