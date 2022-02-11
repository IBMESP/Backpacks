package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OpenSubCommand extends SubCommand {

    private static HashMap<UUID, Inventory> playerBackpack;


    public OpenSubCommand(HashMap<UUID,Inventory> playerBackpack) {
        this.playerBackpack = playerBackpack;
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
            player.openInventory(playerBackpack.get(player.getUniqueId()));
        }else{
            player.sendMessage(ChatColor.RED + "You don't have a backpack");
        }
    }
}
