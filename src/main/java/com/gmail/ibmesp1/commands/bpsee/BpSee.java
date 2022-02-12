package com.gmail.ibmesp1.commands.bpsee;

import com.gmail.ibmesp1.Backpacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class BpSee implements CommandExecutor {

    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;

    public BpSee(Backpacks plugin,HashMap<UUID,Inventory> playerBackpack){
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
            OfflinePlayer target = Bukkit.getPlayer(args[0]);

            if(target == null || !target.isOnline()){
                player.sendMessage("This player is offline");
                return true;
            }

            player.openInventory(playerBackpack.get(target.getUniqueId()));
        }else{
            player.sendMessage(ChatColor.RED + "/bpsee <player>");
        }
        return false;
    }
}
