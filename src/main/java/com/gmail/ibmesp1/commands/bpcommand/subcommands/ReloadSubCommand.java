package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final Backpacks plugin;

    public ReloadSubCommand(Backpacks plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            if (player.hasPermission("bp.reload")) {
                plugin.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "[Backpacks] Config reloaded!");
                System.out.println("[Backpacks] Config reloaded!");
            }
        }
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }
}
