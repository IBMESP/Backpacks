package com.gmail.ibmesp1.commands.bpmenu.guis.create;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
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

public class CreateGUI implements Listener {

    private Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpacks;
    private boolean head;
    private GUIs guis;
    private int smallSize;
    private int mediumSize;
    private int largeSize;

    public CreateGUI(Backpacks plugin,HashMap<UUID, Inventory> playerBackpacks) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        smallSize = plugin.getConfig().getInt("smallSize");
        mediumSize = plugin.getConfig().getInt("mediumSize");
        largeSize = plugin.getConfig().getInt("largeSize");

        Player player = (Player) e.getWhoClicked();
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52};

        if (e.getView().getTitle().equalsIgnoreCase("Players Online (Small)")) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(player.hasPermission("bp.small")) {


                if (e.getSlot() == 49) {
                    player.closeInventory();
                    player.setCustomName("createSmall");
                    player.sendMessage(ChatColor.GRAY + "Write the name");
                    return;
                }

                for (int i = 0; i < glass_slots.length; i++) {
                    if (e.getSlot() == glass_slots[i]) {
                        head = false;
                    } else {
                        head = true;
                    }
                }

                if (head) {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "You already have a backpack");
                                return;
                            }
                            Inventory inventory = Bukkit.createInventory(player, smallSize * 9, player.getName() + "'s Backpack");
                            playerBackpacks.put(player.getUniqueId(), inventory);
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to create other backpacks");
                        }
                        return;
                    }

                    if (playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + target.getName() + " already have a backpack");
                        return;
                    }

                    if (target.getUniqueId() == player.getUniqueId()) {
                        Inventory inventory = Bukkit.createInventory(null, smallSize * 9, player.getName() + "'s Backpack");
                        playerBackpacks.put(player.getUniqueId(), inventory);
                        player.openInventory(inventory);
                        return;
                    }

                    Inventory inventory = Bukkit.createInventory(player, smallSize * 9, target.getName() + "'s Backpack");
                    playerBackpacks.put(target.getUniqueId(), inventory);
                    target.sendMessage(player.getName() + " created you a backpack");
                    target.sendMessage("Use /bp open to open the backpack");
                    return;
                }
            }else{
                player.sendMessage(ChatColor.RED + "You do not have permission to create a small backpack");
                e.setCancelled(true);
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase("Players Online (Medium)")) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(player.hasPermission("bp.medium")) {
                if (e.getSlot() == 49) {
                    player.closeInventory();
                    player.setCustomName("createMedium");
                    player.sendMessage(ChatColor.GRAY + "Write the name");
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
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "You already have a backpack");
                                return;
                            }
                            Inventory inventory = Bukkit.createInventory(player, mediumSize * 9, player.getName() + "'s Backpack");
                            playerBackpacks.put(player.getUniqueId(), inventory);
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to create other backpacks");
                        }
                        return;
                    }

                    if (playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + target.getName() + " already have a backpack");
                        return;
                    }

                    if (target.getUniqueId() == player.getUniqueId()) {
                        Inventory inventory = Bukkit.createInventory(null, mediumSize * 9, player.getName() + "'s Backpack");
                        playerBackpacks.put(player.getUniqueId(), inventory);
                        player.openInventory(inventory);
                        return;
                    }

                    Inventory inventory = Bukkit.createInventory(player, mediumSize * 9, target.getName() + "'s Backpack");
                    playerBackpacks.put(target.getUniqueId(), inventory);
                    target.sendMessage(player.getName() + " created you a backpack");
                    target.sendMessage("Use /bp open to open the backpack");
                    player.openInventory(inventory);
                }
            }else{
                player.sendMessage(ChatColor.RED + "You do not have permission to create a medium backpack");
                e.setCancelled(true);
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase("Players Online (Large)")) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(e.getSlot() == 49){
                player.closeInventory();
                player.setCustomName("createLarge");
                player.sendMessage(ChatColor.GRAY + "Write the name");
                return;
            }

            for (int i=0;i<glass_slots.length;i++){
                if(e.getSlot() == glass_slots[i]){
                    head = false;
                }else{
                    head = true;
                }
            }

            if(head){
                SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                Player target = (Player) skullMeta.getOwningPlayer();

                if(!player.hasPermission("bp.admin")) {
                    if(target.getUniqueId() == player.getUniqueId()) {

                        if (playerBackpacks.containsKey(target.getUniqueId())) {
                            e.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "You already have a backpack");
                            return;
                        }
                        Inventory inventory = Bukkit.createInventory(player,largeSize * 9,player.getName() + "'s Backpack");
                        playerBackpacks.put(player.getUniqueId(),inventory);
                        player.openInventory(inventory);
                        return;
                    }else{
                        player.sendMessage(ChatColor.RED + "You do not have permission to create other backpacks");
                    }
                    return;
                }

                if (playerBackpacks.containsKey(target.getUniqueId())) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + target.getName() + " already have a backpack");
                    return;
                }

                if(target.getUniqueId() == player.getUniqueId()){
                    Inventory inventory = Bukkit.createInventory(null, largeSize * 9,player.getName() + "'s Backpack");
                    playerBackpacks.put(player.getUniqueId(), inventory);
                    player.openInventory(inventory);
                    return;
                }

                Inventory inventory = Bukkit.createInventory(player,largeSize * 9,target.getName() + "'s Backpack");
                playerBackpacks.put(target.getUniqueId(),inventory);
                target.sendMessage(player.getName() + " created you a backpack");
                target.sendMessage("Use /bp open to open the backpack");
                player.openInventory(inventory);
            }
        }
    }
}
