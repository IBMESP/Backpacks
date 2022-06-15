package com.gmail.ibmesp1.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.data.DataManger;
import com.gmail.ibmesp1.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateSubCommand extends SubCommand {
    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private int smallSize;
    private int mediumSize;
    private int largeSize;
    private DataManger bpcm;

    public CreateSubCommand(Backpacks plugin, HashMap<UUID,Inventory> playerBackpack, DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpcm = bpcm;
    }

    @Override
    public String getName() {
        return "create";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        List<String> pargs = new ArrayList<>();
        pargs.add("s");
        pargs.add("m");
        pargs.add("l");
        return pargs;
    }

    @Override
    public void perform(Player player, String[] args) {
        checkSize();
        if(args.length == 1) {
            player.sendMessage(ChatColor.RED + "/bp create <s/m>");
        }else if (args[1].equalsIgnoreCase("s")) { // add backpack mechanics
            create(player,args,"bp.small","gui.small",smallSize);
        }else if (args[1].equalsIgnoreCase("m")) {
            create(player,args,"bp.medium","gui.medium",mediumSize);
        }else if (args[1].equalsIgnoreCase("l")) {
            create(player,args,"bp.large","gui.large",largeSize);
        }else{
            player.sendMessage(plugin.name + ChatColor.RED + plugin.getLanguageString("config.exist"));
        }
    }

    private void create(Player player,String[] args,String perm,String gui,int size){
        if(player.hasPermission(perm)) {
            if(args.length == 3){
                if(player.hasPermission("bp.admin")) {
                    Player target = Bukkit.getPlayer(args[2]);

                    if (target == null) {
                        String targetOffline = args[2];
                        createOfflineBackpack(player,targetOffline,size);
                        return;
                    }

                    createTargetBackpack(player, target, smallSize, plugin.getLanguageString(gui));
                    return;
                }else{
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
                }
            }

            createBackpack(player,size);

        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
        }
    }

    private void createBackpack(Player player,int size){
        if (playerBackpack.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.already"));
            return;
        }

        String title = plugin.getLanguageString("config.title");

        Inventory inventory = Bukkit.createInventory(null, size * 9,title.replace("%player",player.getName()));
        playerBackpack.put(player.getUniqueId(), inventory);
        player.openInventory(inventory);
    }

    private void createTargetBackpack(Player player,Player target,int size,String Size){
        if (playerBackpack.containsKey(target.getUniqueId())) {
            player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("create.target.already"));
            return;
        }

        String title = plugin.getLanguageString("config.title");

        Inventory inventory = Bukkit.createInventory(target, size * 9,title.replace("%player",target.getName()));
        String created = plugin.getLanguageString("create.target.create");
        target.sendMessage(created.replace("%player",player.getName()).replace("%size", Size));
        target.sendMessage(plugin.getLanguageString("config.open"));
        playerBackpack.put(target.getUniqueId(), inventory);
    }

    private void createOfflineBackpack(Player player,String target,int size){
        try {
            UUID targetUUID = UUIDFetcher.getUUIDOf(target);
            if(playerBackpack.containsKey(targetUUID)){
                player.sendMessage(ChatColor.RED + target + plugin.getLanguageString("create.target.already"));
                return;
            }

            String title = plugin.getLanguageString("config.title");

            Inventory inventory = Bukkit.createInventory(null,size * 9,title.replace("%player",target));
            playerBackpack.put(targetUUID,inventory);
            player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("create.target.created") + target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkSize(){
        smallSize = bpcm.getConfig().getInt("smallSize");
        mediumSize = bpcm.getConfig().getInt("mediumSize");
        largeSize = bpcm.getConfig().getInt("largeSize");

        if(smallSize > 6 || smallSize < 1){
            if(smallSize < 1){
                smallSize = 1;
                bpcm.getConfig().set("smallSize",1);
                return;
            }
            smallSize = 6;
            bpcm.getConfig().set("smallSize",6);
        }
        if(mediumSize > 6 || mediumSize < 1){
            if(mediumSize < 1){
                mediumSize = 1;
                plugin.getConfig().set("mediumSize",1);
                return;
            }
            mediumSize = 6;
            bpcm.getConfig().set("mediumSize",6);
        }
        if (largeSize > 6|| largeSize < 1){
            if(largeSize < 1){
                largeSize = 1;
                bpcm.getConfig().set("largeSize",1);
                return;
            }
            largeSize = 6;
            bpcm.getConfig().set("largeSize",6);
        }
    }
}

