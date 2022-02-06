package com.gmail.ibmesp1.commands;

import com.gmail.ibmesp1.Backpacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BpCommand  implements CommandExecutor {

    private final Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private static File file;
    private static FileConfiguration cfg;

    public BpCommand(Backpacks plugin){
        this.plugin = plugin;
        playerBackpack = new HashMap<>();
        file = new File(plugin.getDataFolder(), "backpacks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
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

        if(args[0].equalsIgnoreCase("version")) {
            player.sendMessage(plugin.name+ChatColor.WHITE+" Version "+plugin.version);
            return true;
        }else if(args[0].equalsIgnoreCase("help")){
            player.sendMessage(ChatColor.YELLOW+"--------------------"+ChatColor.WHITE+"Backpacks Help"+ChatColor.YELLOW+"--------------------");
            player.sendMessage(ChatColor.YELLOW+"/bp version:"+ChatColor.WHITE+" Backpacks version");
            player.sendMessage(ChatColor.YELLOW+"/bp create <s/m>:"+ChatColor.WHITE+" To create a backpack");
            player.sendMessage(ChatColor.YELLOW+"/bp open:"+ChatColor.WHITE+" To open a backpack");
            player.sendMessage(ChatColor.YELLOW+"/bp delete:"+ChatColor.WHITE+" To delete a backpack");
            return true;
        }else if(args[0].equalsIgnoreCase("delete")){
            if(args.length == 1){
                player.sendMessage(ChatColor.RED + "This will remove your backpack and what is inside");
                player.sendMessage(ChatColor.RED + "/bp delete confirm to delete the backpack");
            }else if(args[1].equalsIgnoreCase("confirm")){
                playerBackpack.remove(player.getUniqueId());
                savePlayerBackPacks(player);
                player.sendMessage(ChatColor.RED + "Your backpack has been deleted");
            }else{
                player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
            }
            return true;
        }else if(args[0].equalsIgnoreCase("create")) {
            if(args.length == 1) {
                player.sendMessage(ChatColor.RED + "/bp create <s/m>");
            }else if (args[1].equalsIgnoreCase("s") && player.hasPermission("bp.small")) { // add backpack mechanics
                if (playerBackpack.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You already have a backpack");
                    return true;
                }

                Inventory inventory = Bukkit.createInventory(null, 3 * 9);
                playerBackpack.put(player.getUniqueId(), inventory);
                player.openInventory(inventory);
                return true;
            }else if (args[1].equalsIgnoreCase("m") && player.hasPermission("bp.medium")) {
                if (playerBackpack.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You already have a backpack");
                    return true;
                }

                Inventory inventory = Bukkit.createInventory(null, 6 * 9);
                playerBackpack.put(player.getUniqueId(), inventory);
                player.openInventory(inventory);
                return true;
            }else{
                player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("open")) {
            if (playerBackpack.containsKey(player.getUniqueId())) {
                player.openInventory(playerBackpack.get(player.getUniqueId()));
            }else{
                player.sendMessage(ChatColor.RED + "You don't have a backpack");
            }
            return true;
        } else {
            player.sendMessage(plugin.name + ChatColor.RED + " This command doesn't exists");
            return true;
        }
    }
}
