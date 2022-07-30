package com.gmail.ibmesp1.bp.commands.bpsee;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.UUIDFetcher;
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

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class BpSee implements CommandExecutor {

    private final Backpacks plugin;
    private static HashMap<UUID,  HashMap<String,Inventory>> playerBackpack;

    public BpSee(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpack){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 1)
        {
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                try {
                    UUID targetUUID = UUIDFetcher.getUUIDOf(args[0]);

                    if(playerBackpack.get(targetUUID) == null){
                        player.sendMessage(ChatColor.RED + args[0] + plugin.getLanguageString("gui.items.hasNot"));
                        return true;
                    }
                    GUI(player,targetUUID,args[0]);

                } catch (Exception ignored) {}

                return true;
            }

            if(playerBackpack.get(target.getUniqueId()) == null){
                player.sendMessage(ChatColor.RED + args[0] + " " + plugin.getLanguageString("gui.items.hasNot"));
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
        Inventory inventory = Bukkit.createInventory(player,3*9,"%player%'s Backpacks".replace("%player%",name));
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26};
        for(int slot:glass_slots){
            ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta glass_meta = glass.getItemMeta();
            glass_meta.setDisplayName(" ");
            glass.setItemMeta(glass_meta);
            inventory.setItem(slot,glass);
        }
        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(uuid + ".").getKeys(false);
        int i = 0;

        for (String key:set){
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
