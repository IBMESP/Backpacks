package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.BackpackManager;
import com.gmail.ibmesp1.bp.utils.Checkers;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final Backpacks plugin;
    private final DataManager bpcm;
    private final DataManager languageData;
    private final BackpackManager bpm;

    public ReloadSubCommand(Backpacks plugin, DataManager bpcm, DataManager languageData, BackpackManager bpm) {
        this.plugin = plugin;
        this.bpcm = bpcm;
        this.languageData = languageData;
        this.bpm = bpm;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            bpm.saveBackPacks();
            bpcm.reloadConfig();
            languageData.reloadConfig();
            new Checkers(plugin,bpcm,plugin.maxBP,plugin.rowsBP).checkSize();
            bpm.loadBackPacks();
            System.out.println(plugin.getLanguageString("config.reloaded"));
            return;
        }

        Player player = (Player) sender;

        if(args.length == 1){
            if (player.hasPermission("bp.reload")) {
                bpm.saveBackPacks();
                bpcm.reloadConfig();
                languageData.reloadConfig();
                new Checkers(plugin,bpcm,plugin.maxBP,plugin.rowsBP).checkSize();
                bpm.loadBackPacks();
                player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("config.reloaded"));
                System.out.println(plugin.getLanguageString("config.reloaded"));
            }else{
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
            }
        }
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
