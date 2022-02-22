package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import org.bukkit.Bukkit;
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
        for (Player p : Bukkit.getOnlinePlayers())
        {
            pargs.add(p.getName());
        }
        return pargs;
    }

    @Override
    public void perform(Player player, String[] args) {
        Inventory inventory = playerBackpack.get(player.getUniqueId());

        if(args.length == 1){
            player.sendMessage(ChatColor.RED + "This will remove your backpack and your items will be dropped");
            player.sendMessage(ChatColor.RED + "/bp delete confirm to delete the backpack");
        }else if(args[1].equalsIgnoreCase("confirm")){
            if(inventory == null){
                player.sendMessage(ChatColor.RED + "You do not have a backpack");
                return;
            }
            Inventory prevInventory = playerBackpack.get(player.getUniqueId());
            int size = prevInventory.getSize();

            for (int i = 0;i<size;i++){
                try {
                    player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
                }catch (IllegalArgumentException | NullPointerException e){}
            }

            playerBackpack.remove(player.getUniqueId());
            BpCommand.savePlayerBackPacks(player.getUniqueId());
            player.sendMessage(ChatColor.RED + "Your backpack has been deleted");
        }else if(args.length == 2 || args.length == 3) {

            if(player.hasPermission("bp.admin")) {

                if(args.length == 2){
                    player.sendMessage(ChatColor.RED + "This will remove " + args[1] + " backpack and " + args[1] +  " items will be dropped");
                    player.sendMessage(ChatColor.RED + "/bp delete " + args[1] + " confirm to delete the backpack");
                }else if(args[2].equalsIgnoreCase("confirm")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        player.sendMessage(ChatColor.RED + args[1] + " is offline");
                        return;
                    }

                    Inventory prevInventory = playerBackpack.get(target.getUniqueId());
                    int size = prevInventory.getSize();

                    for (int i = 0; i < size; i++) {
                        try {
                            target.getLocation().getWorld().dropItem(target.getLocation(), prevInventory.getItem(i));
                        } catch (IllegalArgumentException | NullPointerException e) {
                        }
                    }

                    playerBackpack.remove(target.getUniqueId());
                    BpCommand.savePlayerBackPacks(target.getUniqueId());
                    target.sendMessage(ChatColor.RED + "Your backpack has been deleted by " + player.getName() );
                }
            }

        }else{
            player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
        }
    }
}
