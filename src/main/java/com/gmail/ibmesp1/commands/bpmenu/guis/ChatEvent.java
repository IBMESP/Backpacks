package com.gmail.ibmesp1.commands.bpmenu.guis;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.utils.UUIDFetcher;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ChatEvent implements Listener {

    private String msg;
    private Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpacks;
    private boolean isOnline;
    private int smallSize;
    private int mediumSize;
    private int largeSize;

    public ChatEvent(Backpacks plugin, HashMap<UUID, Inventory> playerBackpacks) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
    }

    @EventHandler
    public void deleteBrowser(AsyncPlayerChatEvent e){

        smallSize = plugin.getConfig().getInt("smallSize");
        mediumSize = plugin.getConfig().getInt("mediumSize");
        largeSize = plugin.getConfig().getInt("largeSize");

        Player player = e.getPlayer();

        if(player.getCustomName().equalsIgnoreCase("delete")){
            msg = e.getMessage();
            Player target = null;
            UUID uuid = null;
            player.setCustomName(null);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.getName().equals(msg)){
                    isOnline = true;
                    target = p;
                    uuid = target.getUniqueId();
                    break;
                }else{
                    isOnline = false;
                }
            }

            if(!isOnline){
                try {
                    uuid = UUIDFetcher.getUUIDOf(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(!playerBackpacks.containsKey(uuid)){
                player.sendMessage(ChatColor.RED + msg + " does not have a backpack");
                e.setCancelled(true);
                return;
            }

            Inventory inventory = playerBackpacks.get(uuid);


            if(target != null){
                Player finalTarget = target;

                Bukkit.getScheduler().runTask(plugin, () ->{
                    int size = inventory.getSize();

                    for (int i = 0; i < size; i++) {
                        try {
                            finalTarget.getLocation().getWorld().dropItem(finalTarget.getLocation(), inventory.getItem(i));
                        } catch (IllegalArgumentException | NullPointerException ex) {
                        }
                    }
                });

                playerBackpacks.remove(uuid);
                if(target.getUniqueId() == player.getUniqueId()){
                    player.sendMessage(ChatColor.RED + "Your backpack has been deleted");
                }else{
                    target.sendMessage(ChatColor.RED + "Your backpack has been deleted by " + player.getName());
                    player.sendMessage(ChatColor.GREEN + msg +"'s backpack has been deleted");
                }
                BackpackManager.savePlayerBackPacks(uuid);

                e.setCancelled(true);
                return;
            }

            Bukkit.getScheduler().runTask(plugin, () ->{
                int size = inventory.getSize();

                for (int i = 0; i < size; i++) {
                    try {
                        player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(i));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                    }
                }
            });

            playerBackpacks.remove(uuid);
            player.sendMessage(ChatColor.GREEN + msg +"'s backpack has been deleted");
            BackpackManager.savePlayerBackPacks(uuid);

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void create(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        smallSize = plugin.getConfig().getInt("smallSize");
        mediumSize = plugin.getConfig().getInt("mediumSize");
        largeSize = plugin.getConfig().getInt("largeSize");

        if(player.getCustomName().equalsIgnoreCase("createSmall")) {
            msg = e.getMessage();
            Player target = null;
            UUID uuid = null;
            player.setCustomName(null);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.getName().equals(msg)){
                    isOnline = true;
                    target = p;
                    uuid = target.getUniqueId();
                    break;
                }else{
                    isOnline = false;
                }
            }

            if(!isOnline){
                try {
                    uuid = UUIDFetcher.getUUIDOf(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(playerBackpacks.containsKey(uuid)){
                player.sendMessage(ChatColor.RED + msg + " already has a backpack");;
                e.setCancelled(true);
                return;
            }

            Inventory inventory = Bukkit.createInventory(null, smallSize * 9,msg + "s' Backpack");

            playerBackpacks.put(uuid,inventory);
            BackpackManager.savePlayerBackPacks(uuid);
            if(target != null) {
                if(target.getUniqueId() == player.getUniqueId()){
                    player.sendMessage("Use /bp open to open the backpack");
                    e.setCancelled(true);
                    return;
                }
                target.sendMessage(player.getName() + " created you a backpack");
                target.sendMessage("Use /bp open to open the backpack");
            }
            player.sendMessage(ChatColor.GREEN + "You created a backpack to " + msg);
            e.setCancelled(true);
        }else if(player.getCustomName().equalsIgnoreCase("createMedium")) {
            msg = e.getMessage();
            Player target = null;
            UUID uuid = null;
            player.setCustomName(null);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.getName().equals(msg)){
                    isOnline = true;
                    target = p;
                    uuid = target.getUniqueId();
                    break;
                }else{
                    isOnline = false;
                }
            }

            if(!isOnline){
                try {
                    uuid = UUIDFetcher.getUUIDOf(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(playerBackpacks.containsKey(uuid)){
                player.sendMessage(ChatColor.RED + msg + " already has a backpack");
                e.setCancelled(true);
                return;
            }

            Inventory inventory = Bukkit.createInventory(null, mediumSize * 9,msg + "s' Backpack");

            playerBackpacks.put(uuid,inventory);
            BackpackManager.savePlayerBackPacks(uuid);
            if(target != null) {
                if(target.getUniqueId() == player.getUniqueId()){
                    player.sendMessage("Use /bp open to open the backpack");
                    e.setCancelled(true);
                    return;
                }
                target.sendMessage(player.getName() + " created you a backpack");
                target.sendMessage("Use /bp open to open the backpack");
            }
            player.sendMessage(ChatColor.GREEN + "You created a backpack to " + msg);
            e.setCancelled(true);
        }else if(player.getCustomName().equalsIgnoreCase("createLarge")) {
            msg = e.getMessage();
            Player target = null;
            UUID uuid = null;
            player.setCustomName(null);

            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.getName().equals(msg)){
                    isOnline = true;
                    target = p;
                    uuid = target.getUniqueId();
                    break;
                }else{
                    isOnline = false;
                }
            }

            if(!isOnline){
                try {
                    uuid = UUIDFetcher.getUUIDOf(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(playerBackpacks.containsKey(uuid)){
                player.sendMessage(ChatColor.RED + msg + " already has a backpack");
                player.setCustomName(null);
                e.setCancelled(true);
                return;
            }

            Inventory inventory = Bukkit.createInventory(null, largeSize * 9,msg + "s' Backpack");

            playerBackpacks.put(uuid,inventory);
            BackpackManager.savePlayerBackPacks(uuid);
            if(target != null) {
                if(target.getUniqueId() == player.getUniqueId()){
                    player.sendMessage("Use /bp open to open the backpack");
                    e.setCancelled(true);
                    return;
                }
                target.sendMessage(player.getName() + " created you a backpack");
                target.sendMessage("Use /bp open to open the backpack");
            }
            player.sendMessage(ChatColor.GREEN + "You created a backpack to " + msg);
            e.setCancelled(true);
        }
    }

}
