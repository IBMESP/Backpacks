package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class CreateSubCommand extends SubCommand {
    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;

    public CreateSubCommand(Backpacks plugin,HashMap<UUID,Inventory> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
    }

    @Override
    public String getName() {
        return "create";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        List<String> pargs = new ArrayList<>();
        pargs.add("s");
        pargs.add("m");
        return pargs;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1) {
            player.sendMessage(ChatColor.RED + "/bp create <s/m>");
        }else if (args[1].equalsIgnoreCase("s")) { // add backpack mechanics
            if(player.hasPermission("bp.small")) {
                if (playerBackpack.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You already have a backpack");
                    return;
                }

                Inventory inventory = Bukkit.createInventory(null, 3 * 9);
                playerBackpack.put(player.getUniqueId(), inventory);
                player.openInventory(inventory);
            }else {
                player.sendMessage("You do not have permission to use this command");
            }
        }else if (args[1].equalsIgnoreCase("m")) {
            if(player.hasPermission("bp.medium")) {
                if (playerBackpack.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You already have a backpack");
                    return;
                }

                Inventory inventory = Bukkit.createInventory(null, 6 * 9);
                playerBackpack.put(player.getUniqueId(), inventory);
                player.openInventory(inventory);
            }else {
                player.sendMessage("You do not have permission to use this command");
            }
        }else{
            player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
        }
    }
}
