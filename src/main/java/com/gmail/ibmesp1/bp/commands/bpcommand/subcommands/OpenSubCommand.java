package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OpenSubCommand extends SubCommand implements Listener {

    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private final GUIs guis;


    public OpenSubCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        guis = new GUIs(plugin,playerBackpack,plugin.bpcm);
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
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            return;
        }

        Player player = (Player) sender;

        if (playerBackpack.containsKey(player.getUniqueId())) {
            guis.open(player);
        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
        }
    }
}
