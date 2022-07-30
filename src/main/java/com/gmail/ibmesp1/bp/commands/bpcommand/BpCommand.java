package com.gmail.ibmesp1.bp.commands.bpcommand;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.SubCommand;
import com.gmail.ibmesp1.bp.commands.bpcommand.subcommands.*;
import com.gmail.ibmesp1.bp.utils.DataManager;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BpCommand implements TabExecutor {

    private final Backpacks plugin;
    private HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    private DataManager bpcm;
    private BackpackManager bpm;

    public BpCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, DataManager bpcm, BackpackManager bpm){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpcm = bpcm;
        this.bpm = bpm;

        subCommands.add(new VersionSubCommand(plugin));
        subCommands.add(new HelpSubCommand());
        subCommands.add(new CreateSubCommand(plugin,playerBackpack,bpcm));
        subCommands.add(new OpenSubCommand(plugin,playerBackpack));
        subCommands.add(new DeleteSubCommand(plugin,playerBackpack,bpm));
        subCommands.add(new ReloadSubCommand(plugin,bpcm));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 0){
            player.sendMessage(plugin.name + ChatColor.WHITE + " " + plugin.getLanguageString("config.help"));
            return true;
        }

        for (int i = 0; i< getSubCommands().size(); i++){
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                getSubCommands().get(i).perform(player,args);
            }
        }
        return true;
    }

    private ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            ArrayList<String> subCommandsArgs = new ArrayList<>();

            for (int i = 0; i< getSubCommands().size(); i++){
                subCommandsArgs.add(getSubCommands().get(i).getName());
            }
            return subCommandsArgs;
        }else if(args.length == 2){
            for (int i = 0; i< getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubCommandsArgs(args);
                }
            }
        }
        return null;
    }
}
