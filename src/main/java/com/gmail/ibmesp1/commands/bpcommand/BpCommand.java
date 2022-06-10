package com.gmail.ibmesp1.commands.bpcommand;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.commands.bpcommand.subcommands.*;
import com.gmail.ibmesp1.data.DataManger;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class BpCommand  implements TabExecutor {

    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    private DataManger bpcm;
    private BackpackManager bpm;

    public BpCommand(Backpacks plugin, HashMap<UUID,Inventory> playerBackpack, DataManger bpcm,BackpackManager bpm){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;

        subCommands.add(new VersionSubCommand(plugin));
        subCommands.add(new HelpSubCommand());
        subCommands.add(new CreateSubCommand(plugin,playerBackpack,bpcm));
        subCommands.add(new OpenSubCommand(plugin,playerBackpack,bpm));
        subCommands.add(new DeleteSubCommand(plugin,playerBackpack));
        subCommands.add(new ReloadSubCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 0){
            player.sendMessage(plugin.name+ ChatColor.WHITE+ plugin.getLanguageString("config.help"));
            return true;
        }

        if(args.length > 0){
            for (int i = 0; i< getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    getSubCommands().get(i).perform(player,args);
                }
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands(){return subCommands;}

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
