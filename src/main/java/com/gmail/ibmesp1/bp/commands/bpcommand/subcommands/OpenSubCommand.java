package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.SubCommand;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OpenSubCommand extends SubCommand implements Listener {

    private Backpacks plugin;
    private HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private BackpackManager bpm;


    public OpenSubCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpm = bpm;
    }

    @Override
    public String getName() {
        return "open";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (playerBackpack.containsKey(player.getUniqueId())) {
            GUI(player);
        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
        }
    }

    private void GUI(Player player){
        Inventory inventory = Bukkit.createInventory(player,3*9,plugin.getLanguageString("config.backpacks"));
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26};
        for(int slot:glass_slots){
            ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta glass_meta = glass.getItemMeta();
            glass_meta.setDisplayName(" ");
            glass.setItemMeta(glass_meta);
            inventory.setItem(slot,glass);
        }
        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
        int i = 0;

        for(String key:set){
            ItemStack bp = new ItemStack(Material.CHEST);
            ItemMeta bp_meta = bp.getItemMeta();
            bp_meta.setDisplayName(key + "");
            bp.setItemMeta(bp_meta);

            inventory.setItem(i+9,bp);
            i++;
        }
        player.openInventory(inventory);
    }
}
