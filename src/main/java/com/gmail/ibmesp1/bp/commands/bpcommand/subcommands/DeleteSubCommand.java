package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeleteSubCommand extends SubCommand {

    private final Backpacks plugin;
    private final GUIs guis;

    public DeleteSubCommand(Backpacks plugin,GUIs guis) {
        this.plugin = plugin;
        this.guis = guis;
    }
    @Override
    public String getName() {
        return "delete";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        List<String> pargs = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
        {
            pargs.add(p.getName());
        }
        return pargs;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if(args.length == 1){
            guis.delete(player,player.getUniqueId(),player.getName());
        }else if(args.length == 2){
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                try {
                    UUID uuid = UUIDFetcher.getUUIDOf(args[1]);

                    guis.delete(player,uuid,args[1]);
                } catch (Exception ignored) {}
            }else{
                guis.delete(player,target.getUniqueId(),target.getName());
            }
        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.config.exist"));
        }
    }
}
