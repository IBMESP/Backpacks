package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.SubCommand;
import com.gmail.ibmesp1.bp.utils.DataManager;
import com.gmail.ibmesp1.bp.utils.backpacks.Checkers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final Backpacks plugin;
    private final DataManager bpcm;

    public ReloadSubCommand(Backpacks plugin, DataManager bpcm) {
        this.plugin = plugin;
        this.bpcm = bpcm;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            if (player.hasPermission("bp.reload")) {
                bpcm.reloadConfig();
                new Checkers(plugin,bpcm,plugin.maxBP,plugin.rowsBP).checkSize();
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
