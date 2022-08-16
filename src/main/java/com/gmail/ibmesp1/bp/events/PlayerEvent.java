package com.gmail.ibmesp1.bp.events;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.BackpackManager;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PlayerEvent implements Listener {

    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private final HashMap<UUID,UUID> delBP;
    private final HashMap<UUID,String> delete;
    private final DataManager bpcm;
    private List<Player> playerList;
    private final BackpackManager bpm;

    public PlayerEvent(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, HashMap<UUID, UUID> delBP, HashMap<UUID, String> delete, DataManager bpcm, List<Player> playerList, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.delBP = delBP;
        this.delete = delete;
        this.bpcm = bpcm;
        this.playerList = playerList;
        this.bpm = bpm;
    }

    @EventHandler
    public void onJoins(PlayerJoinEvent e) {
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
        delete.remove(player.getUniqueId());
    }

    @EventHandler
    public void onQuits(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        playerList.remove(player);

        HashMap<String,Inventory> inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        bpm.savePlayerBackPacks(player.getUniqueId());
        delBP.remove(player.getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        Player player = e.getPlayer();
        playerList.remove(player);

        HashMap<String,Inventory> inventory = playerBackpack.get(player.getUniqueId());
        if(inventory == null){
            return;
        }

        bpm.savePlayerBackPacks(e.getPlayer().getUniqueId());
        delBP.remove(player.getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        if(!bpcm.getConfig().getBoolean("keepBackpack")){
            HashMap<String,Inventory> invs = playerBackpack.get(player.getUniqueId());
            Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
            String title = plugin.getLanguageString("config.title");
            if(plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") != null) {
                for (String key : set) {
                    Inventory inventory = playerBackpack.get(player.getUniqueId()).get(key);
                    int size = inventory.getSize();
                    for (int j = 0; j < size; j++) {
                        try {
                            player.getLocation().getWorld().dropItem(player.getLocation(), inventory.getItem(j));
                        } catch (IllegalArgumentException ignored) {
                        }
                    }

                    Inventory inv = Bukkit.createInventory(null, size, title.replace("%player%", player.getName()));
                    invs.replace(key, inv);
                }

                playerBackpack.put(player.getUniqueId(), invs);
                bpm.savePlayerBackPacks(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if(!plugin.getConfig().getBoolean("geyser"))
            return;

        if(!delBP.containsKey(player.getUniqueId()))
            return;

        e.setCancelled(true);
        UUID uuid = delBP.get(player.getUniqueId());
        String key = delete.get(uuid);
        Player target = Bukkit.getPlayer(uuid);

        Inventory prevInventory = playerBackpack.get(uuid).get(key);
        if(prevInventory == null)
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.notBackpack").replace("%player%",player.getName()));

        int size = prevInventory.getSize();

        for (int i = 0; i < size; i++) {
            try {
                player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
            } catch (IllegalArgumentException | NullPointerException ignored) {}
        }

        HashMap<String,Inventory> invs = playerBackpack.get(uuid);
        invs.remove(key);
        playerBackpack.put(uuid,invs);
        bpm.deletePlayerBackPacks(uuid,key);
        player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("delete.target.deleted").replace("%player%",target.getName()));
        delBP.get(player.getUniqueId());
        delete.remove(uuid);
    }

    @EventHandler
    public void playerCloseInv(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        String create = plugin.getLanguageString("gui.create.title");

        if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size%", "Small"))){
            plugin.playerPage.remove(player.getUniqueId());
        }else if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size%", "Medium"))){
            plugin.playerPage.remove(player.getUniqueId());
        }else if(e.getView().getTitle().equalsIgnoreCase(create.replace("%size%", "Large"))) {
            plugin.playerPage.remove(player.getUniqueId());
        } else if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.delete.title"))) {
            plugin.playerPage.remove(player.getUniqueId());
        }
    }
}
