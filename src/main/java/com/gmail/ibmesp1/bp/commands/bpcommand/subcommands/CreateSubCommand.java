package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.ibcore.commands.SubCommand;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ibcore.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateSubCommand extends SubCommand {
    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private final DataManager bpcm;

    public CreateSubCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, DataManager bpcm) {
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
    public void perform(CommandSender sender, String[] args) {

        int smallSize = bpcm.getConfig().getInt("smallSize");
        int mediumSize = bpcm.getConfig().getInt("mediumSize");
        int largeSize = bpcm.getConfig().getInt("largeSize");

        if(!(sender instanceof Player)){
            if(args.length != 3){
                Bukkit.broadcastMessage(ChatColor.RED + "/bp create <s/m/l> <player>");
                return;
            }

            if (args[1].equalsIgnoreCase("s")) {// add backpack mechanics
                createConsole(args,"gui.small", smallSize);
            }else if (args[1].equalsIgnoreCase("m")) {
                createConsole(args,"gui.medium", mediumSize);
            }else if (args[1].equalsIgnoreCase("l")) {
                createConsole(args,"gui.large", largeSize);
            }else{
                Bukkit.broadcastMessage(plugin.name + ChatColor.RED + plugin.getLanguageString("config.exist"));
            }

            return;
        }

        Player player = (Player) sender;

        if(args.length > 3){
            player.sendMessage(ChatColor.RED + "/bp create <s/m/l> (player)");
            return;
        }

        if(args.length == 1) {
            player.sendMessage(ChatColor.RED + "/bp create <s/m/l> (player)");
        }else if (args[1].equalsIgnoreCase("s")) {// add backpack mechanics
            create(player,args,"bp.small","gui.small", smallSize);
        }else if (args[1].equalsIgnoreCase("m")) {
            create(player,args,"bp.medium","gui.medium", mediumSize);
        }else if (args[1].equalsIgnoreCase("l")) {
            create(player,args,"bp.large","gui.large", largeSize);
        }else{
            player.sendMessage(plugin.name + ChatColor.RED + plugin.getLanguageString("config.exist"));
        }
    }

    private void create(Player player,String[] args,String perm,String gui,int size){
        if(player.hasPermission(perm)) {
            if(args.length == 3){
                if(player.hasPermission("bp.admin")) {
                    if(!plugin.getConfig().getBoolean("geyser")) {
                        Player target = Bukkit.getPlayer(args[2]);

                        if (target == null) {
                            String targetOffline = args[2];
                            createOfflineBackpack(player, targetOffline, size);
                            return;
                        }

                        int bps = (plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".") == null)
                                ? 0 : plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".").getKeys(false).size();

                        if (bps >= plugin.maxBP) {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                            return;
                        }

                        createTargetBackpack(player, target, size, plugin.getLanguageString(gui));
                        return;
                    }

                    if(Bukkit.getPlayer(args[2]) == null){
                        player.sendMessage(args[2] + " not online");
                        return;
                    }

                    Player target = Bukkit.getPlayer(args[2]);

                    if(player.equals(target)){
                        createBackpack(player,size);
                        return;
                    }

                    createTargetBackpack(player,target,size,plugin.getLanguageString(gui));

                }else{
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
                }
            }

            int bps = (plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") == null)
                    ? 0 : plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false).size();
            if(bps >= plugin.maxBP){
                player.sendMessage(plugin.getLanguageString("create.maxbp"));
                return;
            }

            createBackpack(player,size);

        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
        }
    }

    private void createConsole(String[] args,String gui,int size){
        if(args.length == 3){
            Player target = Bukkit.getPlayer(args[2]);

            if (target == null) {
                String targetOffline = args[2];
                createOfflineBackpack(null,targetOffline,size);
                return;
            }

            int bps = (plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".") == null)
                    ? 0 : plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".").getKeys(false).size();

            if(bps >= plugin.maxBP){
                Bukkit.broadcastMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                return;
            }

            createTargetBackpack(null,target, size, plugin.getLanguageString(gui));
        }
    }

    private void createBackpack(Player player,int size){

        String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title"));

        Inventory inventory = Bukkit.createInventory(null, size * 9,title.replace("%player%",player.getName()));
        if(playerBackpack.containsKey(player.getUniqueId())){
            HashMap<String,Inventory> invs = playerBackpack.get(player.getUniqueId());
            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
            playerBackpack.put(player.getUniqueId(), invs);
            plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
            plugin.backpacks.saveConfig();
        }else{
            HashMap<String,Inventory> invs = new HashMap<>();
            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
            playerBackpack.put(player.getUniqueId(), invs);
            plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
            plugin.backpacks.saveConfig();
        }
        player.openInventory(inventory);
    }

    private void createTargetBackpack(Player player,Player target,int size,String Size){
        String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title"));

        Inventory inventory = Bukkit.createInventory(target, size * 9,title.replace("%player%",target.getName()));
        String created = plugin.getLanguageString("create.target.create");
        target.sendMessage(created.replace("%player%",player.getName()).replace("%size%", Size));
        target.sendMessage(plugin.getLanguageString("config.open"));
        if(playerBackpack.containsKey(target.getUniqueId())){
            HashMap<String,Inventory> invs = playerBackpack.get(target.getUniqueId());
            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
            playerBackpack.put(target.getUniqueId(), invs);
            plugin.backpacks.getConfig().set(target.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
        }else{
            HashMap<String,Inventory> invs = new HashMap<>();
            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
            playerBackpack.put(target.getUniqueId(), invs);
            plugin.backpacks.getConfig().set(target.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
        }
    }

    private void createOfflineBackpack(Player player,String target,int size){
        try {
            UUID targetUUID = UUIDFetcher.getUUIDOf(target);

            int bps = (plugin.backpacks.getConfig().getConfigurationSection(targetUUID + ".") == null)
                    ? 0 : plugin.backpacks.getConfig().getConfigurationSection(targetUUID + ".").getKeys(false).size();

            if(bps >= plugin.maxBP){
                player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                return;
            }

            String title = plugin.getLanguageString("config.title");

            Inventory inventory = Bukkit.createInventory(null,size * 9,title.replace("%player%",target));
            if(playerBackpack.containsKey(targetUUID)){
                HashMap<String,Inventory> invs = playerBackpack.get(targetUUID);
                invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                playerBackpack.put(targetUUID, invs);
                plugin.backpacks.getConfig().set(targetUUID + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
            }else{
                HashMap<String,Inventory> invs = new HashMap<>();
                invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                playerBackpack.put(targetUUID, invs);
                plugin.backpacks.getConfig().set(targetUUID + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
            }
            player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("create.target.created") + target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

