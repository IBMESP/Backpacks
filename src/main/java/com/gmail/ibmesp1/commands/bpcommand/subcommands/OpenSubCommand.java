package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OpenSubCommand extends SubCommand {

    private Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;


    public OpenSubCommand(Backpacks plugin, HashMap<UUID, Inventory> playerBackpack) {
        this.plugin = plugin;
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
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
        }
    }
}
