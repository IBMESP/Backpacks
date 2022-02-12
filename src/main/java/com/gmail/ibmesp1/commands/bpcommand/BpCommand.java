package com.gmail.ibmesp1.commands.bpcommand;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.SubCommand;
import com.gmail.ibmesp1.commands.bpcommand.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class BpCommand  implements TabExecutor {

    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private static File file;
    private static FileConfiguration cfg;
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public BpCommand(Backpacks plugin,HashMap<UUID,Inventory> playerBackpack){
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        file = new File(plugin.getDataFolder(), "backpacks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);

        subCommands.add(new VersionSubCommand(plugin));
        subCommands.add(new HelpSubCommand());
        subCommands.add(new CreateSubCommand(plugin,playerBackpack));
        subCommands.add(new OpenSubCommand(playerBackpack));
        subCommands.add(new DeleteSubCommand(plugin,playerBackpack));
    }

    public static void loadBackPacks() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (cfg.contains(player.getUniqueId().toString())) {
                String inventory = cfg.getString(player.getUniqueId().toString());
                playerBackpack.put(player.getUniqueId(), inventoryFromBase64(inventory));
            }
        }
    }

    public static void loadPlayerBackPacks(OfflinePlayer player) {
        if (cfg.contains(player.getUniqueId().toString())) {
            String inventory = cfg.getString(player.getUniqueId().toString());
            playerBackpack.put(player.getUniqueId(), inventoryFromBase64(inventory));
        }
    }

    public static void saveBackPacks() {
        for (Map.Entry<UUID, Inventory> entry : playerBackpack.entrySet()) {
            cfg.set(entry.getKey().toString(), inventoryToBase64(entry.getValue()));
        }

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayerBackPacks(OfflinePlayer player) {

        cfg.set(player.getUniqueId().toString(),inventoryToBase64(playerBackpack.get(player.getUniqueId())));

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Inventory inventoryFromBase64(String base64) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.createInventory(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender ;

        if (args.length == 0){
            player.sendMessage(plugin.name+ ChatColor.WHITE+" Use /bp help to see the commands");
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
