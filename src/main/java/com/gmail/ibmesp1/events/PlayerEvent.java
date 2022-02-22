package com.gmail.ibmesp1.events;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class PlayerEvent implements Listener {

    private final Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpack;

    public PlayerEvent(Backpacks plugin, HashMap<UUID, Inventory> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
    }
    
    @EventHandler
    public void PlayerJoins(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        Inventory inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        BpCommand.loadPlayerBackPacks(e.getPlayer());
    }

    @EventHandler
    public void PlayerQuits(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        Inventory inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        BpCommand.savePlayerBackPacks(player.getUniqueId());
    }

    @EventHandler
    public void PlayerKicked(PlayerKickEvent e){
        Player player = e.getPlayer();

        Inventory inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        BpCommand.savePlayerBackPacks(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        if(!plugin.getConfig().getBoolean("keepBackpack")){
            Inventory prevInventory = playerBackpack.get(player.getUniqueId());
            int size = prevInventory.getSize();

            for (int i = 0;i<size;i++){
                try {
                    player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
                }catch (IllegalArgumentException e){}
            }

            Inventory inventory = Bukkit.createInventory(null, size);
            playerBackpack.replace(player.getUniqueId(),prevInventory,inventory);

            BpCommand.savePlayerBackPacks(player.getUniqueId());
        }
    }
}
