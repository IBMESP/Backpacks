package com.gmail.ibmesp1.bp.utils.backpacks;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class BackpackManager {
    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private DataManager backpackM;
    private boolean list;

    public BackpackManager(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, DataManager backpacks) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.backpackM = backpacks;
    }

    public Inventory getInventory(UUID uuid,String key) {
        return playerBackpack.get(uuid).get(key);
    }

    public void saveInventory(UUID uuid, Inventory inventory,String key) {
        playerBackpack.get(uuid).replace(key,inventory);
        this.playerBackpack.put(uuid,playerBackpack.get(uuid));
    }

    public void loadBackPacks() {
        if(backpackM.isEmpty()){return;}

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            list = false;
            HashMap<String,Inventory> invs = new HashMap<>();
            if(plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") != null) {
                Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
                for (String key:set) {
                    if (backpackM.getConfig().contains(player.getUniqueId() + "." + key)) {
                        String inventory = backpackM.getConfig().getString(player.getUniqueId() + "." + key);
                        String title = plugin.getLanguageString("config.title");
                        invs.put(key,inventoryFromBase64(inventory,title.replace("%player",player.getName())));
                        list = true;
                    }
                }
                if (list) {
                    playerBackpack.put(player.getUniqueId(), invs);
                }
            }
        }
    }

    public HashMap<String,Inventory> loadPlayerBackPacks(OfflinePlayer player) {
        list = false;
        HashMap<String,Inventory> invs = new HashMap<>();
        if(plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") != null) {
            Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
            for (String key:set) {
                if (backpackM.getConfig().contains(player.getUniqueId() + "." + key)) {
                    String inventory = backpackM.getConfig().getString(player.getUniqueId() + "." + key);
                    String title = plugin.getLanguageString("config.title");
                    invs.put(key,inventoryFromBase64(inventory,title.replace("%player",player.getName())));
                    list = true;
                }
            }
            if (list) {
                playerBackpack.put(player.getUniqueId(), invs);
            }
        }
        return invs;
    }

    public void saveBackPacks() {
        for (Map.Entry<UUID, HashMap<String,Inventory>> entry : playerBackpack.entrySet()) {
            Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(entry.getKey().toString() + ".").getKeys(false);
            for (String key:set) {
                backpackM.getConfig().set(entry.getKey() + "." + key, inventoryToBase64(playerBackpack.get(entry.getKey()).get(key)));
            }
        }
        backpackM.saveConfig();
    }

    public void savePlayerBackPacks(UUID uuid) {
        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(uuid + ".").getKeys(false);
        HashMap<String,Inventory> invs = playerBackpack.get(uuid);

        for (String key:set) {
            Inventory inventory = invs.get(key);

            backpackM.getConfig().set(uuid + "." + key,inventoryToBase64(inventory));
        }
        backpackM.saveConfig();
    }

    public void deletePlayerBackPacks(UUID uuid,String key){
        backpackM.getConfig().set(uuid + "." + key,null);
        savePlayerBackPacks(uuid);
    }

    public String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception ignored) {
        }
        return null;
    }

    public Inventory inventoryFromBase64(String base64,String title) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.createInventory(null, dataInput.readInt(),title);

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (Exception ignored) {
        }
        return null;
    }

}
