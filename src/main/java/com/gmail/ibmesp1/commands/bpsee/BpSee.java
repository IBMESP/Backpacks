package com.gmail.ibmesp1.commands.bpsee;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.utils.UUIDFetcher;
import com.gmail.ibmesp1.utils.backpacks.BackpackGUI;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private BackpackManager backpackManager;

    public BpSee(Backpacks plugin,HashMap<UUID,Inventory> playerBackpack,BackpackManager backpackManager){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.backpackManager = backpackManager;
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
                    int size = playerBackpack.get(targetUUID).getSize();
                    String title = args[0] + "'s Backpacks";

                    if(playerBackpack.get(targetUUID) == null){
                        player.sendMessage(ChatColor.RED + args[0] + " no tiene mochila");
                        return true;
                    }

                    player.openInventory(new BackpackGUI(size,title,player,targetUUID,backpackManager).getInventory());


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            int size = playerBackpack.get(target.getUniqueId()).getSize();
            String title = target.getName() + "'s Backpacks";
            player.openInventory(new BackpackGUI(size,title,player,target.getUniqueId(),backpackManager).getInventory());
        }else{
            player.sendMessage(ChatColor.RED + "/bpsee <player>");
        }
        return false;
    }
}
