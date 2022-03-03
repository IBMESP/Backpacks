package com.gmail.ibmesp1.commands.keepBackpack;

import com.gmail.ibmesp1.Backpacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class keepBackpack implements CommandExecutor {
    private final Backpacks plugin;

    public keepBackpack(Backpacks plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(plugin.name+ ChatColor.WHITE+" Use /bgamerule keepBackpack <true/false>");
            return true;
        }

        if(!(sender instanceof Player)) {
            if(args[0].equalsIgnoreCase("keepBackpack")){
                if(args[1].equalsIgnoreCase("true")){
                    plugin.getConfig().set("keepBackpack",true);
                    Bukkit.getConsoleSender().sendMessage("Gamerule keepBackpack is now set: true");
                    plugin.saveConfig();
                }else if(args[1].equalsIgnoreCase("false")){
                    plugin.getConfig().set("keepBackpack",false);
                    Bukkit.getConsoleSender().sendMessage("Gamerule keepBackpack is now set: false");
                    plugin.saveConfig();
                }else{
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You do not have permission to use this command");
                }
            }
            return false;
        }

        Player player = (Player) sender ;

        if(args[0].equalsIgnoreCase("keepBackpack") && sender.hasPermission("bp.admin")){
            if(args[1].equalsIgnoreCase("true")){
                plugin.getConfig().set("keepBackpack",true);
                player.sendMessage("Gamerule keepBackpack is now set: true");
                plugin.saveConfig();
            }else if(args[1].equalsIgnoreCase("false") && sender.hasPermission("bp.admin")){
                plugin.getConfig().set("keepBackpack",false);
                player.sendMessage("Gamerule keepBackpack is now set: false");
                plugin.saveConfig();
            }else{
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            }
        }
        return false;
    }
}
