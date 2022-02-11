package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import com.gmail.ibmesp1.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeleteSubCommand extends SubCommand {

    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;

    public DeleteSubCommand(Backpacks plugin,HashMap<UUID,Inventory> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
    }
    @Override
    public String getName() {
        return "delete";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        List<String> pargs = new ArrayList<>();
        pargs.add("confirm");
        return pargs;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            player.sendMessage(ChatColor.RED + "This will remove your backpack and what is inside");
            player.sendMessage(ChatColor.RED + "/bp delete confirm to delete the backpack");
        }else if(args[1].equalsIgnoreCase("confirm")){
            playerBackpack.remove(player.getUniqueId());
            BpCommand.savePlayerBackPacks(player);
            player.sendMessage(ChatColor.RED + "Your backpack has been deleted");
        }else{
            player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
        }
    }
}
