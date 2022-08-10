package com.gmail.ibmesp1.bp.commands.bpcommand;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpcommand.subcommands.*;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.utils.BackpackManager;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.commands.SubCommandExecutor;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BpCommand implements SubCommandExecutor {

    private final Backpacks plugin;
    private final ArrayList<SubCommand> subCommands;

    public BpCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, DataManager bpcm, GUIs guis, DataManager languageData, BackpackManager bpm){
        this.plugin = plugin;

        subCommands = new ArrayList<>();

        subCommands.add(new VersionSubCommand(plugin));
        subCommands.add(new HelpSubCommand());
        subCommands.add(new CreateSubCommand(plugin,playerBackpack,bpcm));
        subCommands.add(new OpenSubCommand(plugin,playerBackpack));
        subCommands.add(new DeleteSubCommand(plugin,guis));
        subCommands.add(new ReloadSubCommand(plugin,bpcm, languageData, bpm));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 0){
            player.sendMessage(plugin.name + ChatColor.WHITE + plugin.getLanguageString("config.help"));
            return true;
        }

        for (int i = 0; i< getSubCommands().size(); i++){
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                getSubCommands().get(i).perform(player,args);
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
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
