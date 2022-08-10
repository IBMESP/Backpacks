package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VersionSubCommand extends SubCommand {
    private final Backpacks plugin;

    public VersionSubCommand(Backpacks plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(plugin.name+ ChatColor.WHITE+"Version "+plugin.version);
            return;
        }

        Player player = (Player) sender;

        player.sendMessage(plugin.name+ ChatColor.WHITE+"Version "+plugin.version);
    }
}
