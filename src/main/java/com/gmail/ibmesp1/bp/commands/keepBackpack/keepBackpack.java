package com.gmail.ibmesp1.bp.commands.keepBackpack;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class keepBackpack implements CommandExecutor {
    private final Backpacks plugin;
    private final DataManager bpcm;

    public keepBackpack(Backpacks plugin, DataManager bpcm) {
        this.plugin = plugin;
        this.bpcm = bpcm;
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
                    bpcm.getConfig().set("keepBackpack",true);
                    String gamerule = plugin.getLanguageString("gui.config.gamerule");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + gamerule.replace("%bool%", "true"));
                    bpcm.saveConfig();
                }else if(args[1].equalsIgnoreCase("false")){
                    bpcm.getConfig().set("keepBackpack",false);
                    String gamerule = plugin.getLanguageString("gui.config.gamerule");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + gamerule.replace("%bool%", "false"));
                    bpcm.saveConfig();
                }else{
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You do not have permission to use this command");
                }
            }else{
                Bukkit.getConsoleSender().sendMessage("keepBackpack = " + bpcm.getConfig().getBoolean("keepBackpack"));
            }
            return false;
        }

        Player player = (Player) sender ;

        if(args[0].equalsIgnoreCase("keepBackpack") && sender.hasPermission("bp.admin")){
            if(args[1].equalsIgnoreCase("true")){
                bpcm.getConfig().set("keepBackpack",true);
                String gamerule = plugin.getLanguageString("gui.config.gamerule");
                player.sendMessage(ChatColor.RED + gamerule.replace("%bool%", "true"));
                bpcm.saveConfig();
            }else if(args[1].equalsIgnoreCase("false") && sender.hasPermission("bp.admin")){
                bpcm.getConfig().set("keepBackpack",false);
                String gamerule = plugin.getLanguageString("gui.config.gamerule");
                player.sendMessage(ChatColor.RED + gamerule.replace("%bool%", "false"));
                bpcm.saveConfig();
            }else{
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("keepBackpack = " + bpcm.getConfig().getBoolean("keepBackpack"));
        }
        return false;
    }
}
