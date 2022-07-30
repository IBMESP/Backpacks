package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class OpenSubCommand extends SubCommand implements Listener {

    private Backpacks plugin;
    private HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private int smallSize;
    private int mediumSize;
    private int largeSize;


    public OpenSubCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        smallSize = plugin.bpcm.getConfig().getInt("smallSize");
        mediumSize = plugin.bpcm.getConfig().getInt("mediumSize");
        largeSize = plugin.bpcm.getConfig().getInt("largeSize");
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

    private void GUI(Player player) {
        Inventory inventory = Bukkit.createInventory(player,(plugin.rowsBP+2)*9,plugin.getLanguageString("config.backpacks"));
        int[] glass_slots = new int[18];
        int j = 9;

        for(int h=0;h<9;h++){
            glass_slots[h] = h;
        }

        for(int i=(plugin.rowsBP+2)*9-1;i>(plugin.rowsBP+2)*9-10;i--){
            glass_slots[j] = i;
            j++;
        }

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
            int size = playerBackpack.get(player.getUniqueId()).get(key).getSize();
            ItemStack bp = new ItemStack(Material.CHEST);
            ItemMeta bp_meta = bp.getItemMeta();

            if(size < largeSize*9)
                if(size < mediumSize*9)
                    bp_meta.setDisplayName(plugin.getLanguageString("gui.open.small"));
                else
                    bp_meta.setDisplayName(plugin.getLanguageString("gui.open.medium"));
            else
                bp_meta.setDisplayName(plugin.getLanguageString("gui.open.large"));

            List<String> lore = new ArrayList<>();
            lore.add(0,key + "");
            bp_meta.setLore(lore);
            bp.setItemMeta(bp_meta);
            inventory.setItem(i+9,bp);
            i++;
        }
        player.openInventory(inventory);
    }
}
