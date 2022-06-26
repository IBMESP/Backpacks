package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage(ChatColor.YELLOW+"--------------------"+ChatColor.WHITE+"Backpacks Help"+ChatColor.YELLOW+"--------------------");
        player.sendMessage(ChatColor.YELLOW+"/bp version:"+ChatColor.WHITE+" Backpacks version");
        player.sendMessage(ChatColor.YELLOW+"/bp create <s/m/l> (player):"+ChatColor.WHITE+" To create a backpack");
        player.sendMessage(ChatColor.YELLOW+"/bp reload:"+ChatColor.WHITE+" To reload backpack config files");
        player.sendMessage(ChatColor.YELLOW+"/bp open:"+ChatColor.WHITE+" To open a backpack");
        player.sendMessage(ChatColor.YELLOW+"/bp delete (player):"+ChatColor.WHITE+" To delete a backpack");
        player.sendMessage(ChatColor.YELLOW+"/bgamerule keepBackpack <true/false>:"+ChatColor.WHITE+" To keep or drop your backpack when a player dies");
        player.sendMessage(ChatColor.YELLOW+"/bpsee:"+ChatColor.WHITE+" To open other player backpack");
        player.sendMessage(ChatColor.YELLOW+"/bpmenu:"+ChatColor.WHITE+" To open the backpack GUI");
    }
}
