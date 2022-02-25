package com.gmail.ibmesp1.utils.backpacks;

import com.gmail.ibmesp1.Backpacks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

public class BackpackManager {
    private static Backpacks plugin;
    private static HashMap<UUID, Inventory> playerBackpack;
    private static File file;
    private static FileConfiguration cfg;

    public BackpackManager(Backpacks plugin,HashMap<UUID, Inventory> playerBackpack) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        file = new File(plugin.getDataFolder(), "backpacks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public Inventory getInventory(UUID uuid) {
        return this.playerBackpack.get(uuid);
    }

    public void saveInventory(UUID uuid, Inventory inventory) {
        this.playerBackpack.put(uuid,inventory);
    }

    public static void loadBackPacks() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (cfg.contains(player.getUniqueId().toString())) {
                String inventory = cfg.getString(player.getUniqueId().toString());
                playerBackpack.put(player.getUniqueId(), inventoryFromBase64(inventory,player.getName() + "'s Backpack"));
            }
        }
    }

    public static void loadPlayerBackPacks(OfflinePlayer player) {
        if (cfg.contains(player.getUniqueId().toString())) {
            String inventory = cfg.getString(player.getUniqueId().toString());
            playerBackpack.put(player.getUniqueId(), inventoryFromBase64(inventory,player.getName() + "'s Backpack"));
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

    public static void savePlayerBackPacks(UUID uuid) {

        cfg.set(uuid.toString(),inventoryToBase64(playerBackpack.get(uuid)));

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

    private static Inventory inventoryFromBase64(String base64,String title) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.createInventory(null, dataInput.readInt(),title);

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

}
