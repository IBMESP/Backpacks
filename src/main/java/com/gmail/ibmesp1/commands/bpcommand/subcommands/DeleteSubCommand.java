package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.utils.UUIDFetcher;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
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
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.firstmsg") );
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.secondmsg"));
        }else if(args[1].equalsIgnoreCase("confirm")){
            if(inventory == null){
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
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
            BackpackManager.savePlayerBackPacks(player.getUniqueId());
            player.sendMessage(ChatColor.RED +  plugin.getLanguageString("delete.deleted"));
        }else if(args.length == 2 || args.length == 3) {

            if(player.hasPermission("bp.admin")) {

                if(args.length == 2){
                    String delete1 = plugin.getLanguageString("delete.target.firtsmsg");
                    String delete2 = plugin.getLanguageString("delete.target.secondmsg");
                    player.sendMessage(ChatColor.RED + delete1.replace("%target", args[1]));
                    player.sendMessage(ChatColor.RED + delete2.replace("%target", args[1]));
                }else if(args[2].equalsIgnoreCase("confirm")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        try {
                            UUID targetUUID = UUIDFetcher.getUUIDOf(args[1]);
                            Inventory prevInventory = playerBackpack.get(targetUUID);
                            if(prevInventory == null){
                                player.sendMessage(ChatColor.RED + args[1] + plugin.getLanguageString("delete.target.notBackpack"));
                                return;
                            }
                            int size = prevInventory.getSize();

                            for (int i = 0; i < size; i++) {
                                try {
                                    player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
                                } catch (IllegalArgumentException | NullPointerException e) {
                                }
                            }

                            playerBackpack.remove(targetUUID);
                            BackpackManager.savePlayerBackPacks(targetUUID);
                            player.sendMessage(ChatColor.GREEN + args[1] + plugin.getLanguageString("delete.target.deleted"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }

                    Inventory prevInventory = playerBackpack.get(target.getUniqueId());
                    if(prevInventory == null){
                        player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("delete.target.notBackpack"));
                        return;
                    }
                    int size = prevInventory.getSize();

                    for (int i = 0; i < size; i++) {
                        try {
                            target.getLocation().getWorld().dropItem(target.getLocation(), prevInventory.getItem(i));
                        } catch (IllegalArgumentException | NullPointerException e) {
                        }
                    }

                    playerBackpack.remove(target.getUniqueId());
                    BackpackManager.savePlayerBackPacks(target.getUniqueId());
                    target.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.deletedBy") + player.getName());
                    player.sendMessage(ChatColor.GREEN + args[1] + plugin.getLanguageString("delete.target.deleted"));
                }
            }else{
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.delete"));
            }

        }else{
            player.sendMessage(plugin.name + ChatColor.RED + plugin.getLanguageString("delete.config.exist"));
        }
    }
}
