package com.gmail.ibmesp1.bp.events;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.data.DataManager;
import com.gmail.ibmesp1.bp.utils.UUIDFetcher;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.time.LocalDateTime;
import java.util.*;

public class PlayerEvent implements Listener {

    private final Backpacks plugin;
    private HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private DataManager bpcm;
    private List<Player> playerList;
    private BackpackManager bpm;

    public PlayerEvent(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, DataManager bpcm, List<Player> playerList, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpcm = bpcm;
        this.playerList = playerList;
        this.bpm = bpm;
    }

    @EventHandler
    public void onJoins(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        if(Bukkit.getOnlinePlayers().size() == 0){
            playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
            playerList.add(player);
        }else{
            playerList.add(player);
        }

        HashMap<String,Inventory> inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        bpm.loadPlayerBackPacks(e.getPlayer());
    }

    @EventHandler
    public void PlayerQuits(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        playerList.remove(player);

        HashMap<String,Inventory> inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        bpm.savePlayerBackPacks(player.getUniqueId());
    }

    @EventHandler
    public void PlayerKicked(PlayerKickEvent e){
        Player player = e.getPlayer();
        playerList.remove(player);

        HashMap<String,Inventory> inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        bpm.savePlayerBackPacks(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        if(!bpcm.getConfig().getBoolean("keepBackpack")){
            HashMap<String,Inventory> invs = playerBackpack.get(player.getUniqueId());
            Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
            String title = plugin.getLanguageString("config.title");

            for (String key:set) {
                Inventory inventory = playerBackpack.get(player.getUniqueId()).get(key);
                int size = inventory.getSize();
                for (int j = 0; j < size; j++) {
                    try {
                        player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(j));
                    } catch (IllegalArgumentException e) {
                    }
                }

                Inventory inv = Bukkit.createInventory(null, size, title.replace("%player", player.getName()));
                invs.replace(key,inv);
            }

            playerBackpack.put(player.getUniqueId(),invs);
            bpm.savePlayerBackPacks(player.getUniqueId());
        }
    }

    @EventHandler
    public void playerCloseInv(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        String create = plugin.getLanguageString("gui.create.title");

        if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size", "Small"))){
            plugin.playerPage.remove(player.getUniqueId());
        }else if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size", "Medium"))){
            plugin.playerPage.remove(player.getUniqueId());
        }else if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size", "Large"))) {
            plugin.playerPage.remove(player.getUniqueId());
        } else if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.delete.title"))) {
            plugin.playerPage.remove(player.getUniqueId());
        }
    }

}
