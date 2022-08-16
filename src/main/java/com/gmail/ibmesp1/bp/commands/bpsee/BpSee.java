package com.gmail.ibmesp1.bp.commands.bpsee;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.ibcore.guis.MenuItems;
import com.gmail.ibmesp1.ibcore.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BpSee implements CommandExecutor {

    private final Backpacks plugin;
    private final HashMap<UUID,  HashMap<String,Inventory>> playerBackpack;
    private final MenuItems menuItems;
    private final int mediumSize;
    private final int largeSize;

    public BpSee(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpack, MenuItems menuItems){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.menuItems = menuItems;

        mediumSize = plugin.bpcm.getConfig().getInt("mediumSize");
        largeSize = plugin.bpcm.getConfig().getInt("largeSize");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 1){
            if(!plugin.getConfig().getBoolean("geyser")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    try {
                        UUID targetUUID = UUIDFetcher.getUUIDOf(args[0]);

                        if (playerBackpack.get(targetUUID) == null) {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.items.hasNot").replace("%player%", args[0]));
                            return true;
                        }
                        GUI(player, targetUUID, args[0]);

                    } catch (Exception ignored) {
                    }

                    return true;
                }
            }

            if(Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(args[0] + " not online");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(playerBackpack.get(target.getUniqueId()) == null){
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("gui.items.hasNot").replace("%player%",args[0]));
                return true;
            }

            if(player.getUniqueId() == target.getUniqueId())
            {
                GUI(player,player.getUniqueId(),player.getName());
            }

            GUI(player,target.getUniqueId(),target.getName());

        }else{
            player.sendMessage(ChatColor.RED + "/bpsee <player>");
        }
        return false;
    }

    private void GUI(Player player,UUID uuid,String name) {
        Inventory inventory = Bukkit.createInventory(player,(plugin.rowsBP+2)*9,"%player%'s Backpacks".replace("%player%",name));
        int[] glass_slots = new int[18];
        int j = 9;

        if(plugin.backpacks.getConfig().getConfigurationSection(uuid + ".") == null)
            return;

        for(int h=0;h<9;h++)
            glass_slots[h] = h;

        for(int i=(plugin.rowsBP+2)*9-1;i>(plugin.rowsBP+2)*9-10;i--){
            glass_slots[j] = i;
            j++;
        }

        for(int slot:glass_slots){
            inventory.setItem(slot, menuItems.glass());
        }
        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(uuid + ".").getKeys(false);
        int i = 0;

        for (String key:set){
            int size = playerBackpack.get(uuid).get(key).getSize();
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
