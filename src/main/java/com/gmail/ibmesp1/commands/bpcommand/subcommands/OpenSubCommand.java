package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OpenSubCommand extends SubCommand {

    private Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private BackpackManager bpm;


    public OpenSubCommand(Backpacks plugin, HashMap<UUID, Inventory> playerBackpack,BackpackManager bpm) {
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
            UUID uuid = player.getUniqueId();
            Inventory inventory = playerBackpack.get(uuid);

            player.openInventory(inventory);
        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
        }
    }
}
