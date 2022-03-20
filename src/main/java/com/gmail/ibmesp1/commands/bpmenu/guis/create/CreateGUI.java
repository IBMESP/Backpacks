package com.gmail.ibmesp1.commands.bpmenu.guis.create;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.data.DataManger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.UUID;

public class CreateGUI implements Listener {

    private Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpacks;
    private HashMap<UUID, String> customName;
    private boolean head;
    private GUIs guis;
    private int smallSize;
    private int mediumSize;
    private int largeSize;
    private DataManger bpcm;

    public CreateGUI(Backpacks plugin, HashMap<UUID, Inventory> playerBackpacks, HashMap<UUID, String> customName, DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.customName = customName;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        smallSize = bpcm.getConfig().getInt("smallSize");
        mediumSize = bpcm.getConfig().getInt("mediumSize");
        largeSize = bpcm.getConfig().getInt("largeSize");

        Player player = (Player) e.getWhoClicked();
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52};

        String small = plugin.getLanguageString("gui.create.title");

        String medium = plugin.getLanguageString("gui.create.title");

        String large = plugin.getLanguageString("gui.create.title");

        if (e.getView().getTitle().equalsIgnoreCase(small.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.small"))))) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(player.hasPermission("bp.small")) {


                if (e.getSlot() == 49) {
                    player.closeInventory();
                    customName.put(player.getUniqueId(),player.getCustomName());
                    player.setCustomName("createSmall");
                    player.sendMessage(ChatColor.GRAY + plugin.getLanguageString("gui.browser"));
                    return;
                }

                for (int i = 0; i < glass_slots.length; i++) {
                    if (e.getSlot() == glass_slots[i]) {
                        head = false;
                    } else {
                        head = true;
                    }
                }

                if (head) {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.already"));
                                return;
                            }
                            String title = plugin.getLanguageString("config.title");
                            Inventory inventory = Bukkit.createInventory(player, smallSize * 9, title.replace("%player",player.getName()) );
                            playerBackpacks.put(player.getUniqueId(), inventory);
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.target.perm"));
                        }
                        return;
                    }

                    if (playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("create.target.already"));
                        return;
                    }

                    if (target.getUniqueId() == player.getUniqueId()) {
                        String title = plugin.getLanguageString("config.title");
                        Inventory inventory = Bukkit.createInventory(null, smallSize * 9, title.replace("%player",player.getName()));
                        playerBackpacks.put(player.getUniqueId(), inventory);
                        player.openInventory(inventory);
                        return;
                    }

                    String title = plugin.getLanguageString("config.title");

                    Inventory inventory = Bukkit.createInventory(player, smallSize * 9, title.replace("%player",target.getName()));
                    playerBackpacks.put(target.getUniqueId(), inventory);
                    String create = plugin.getLanguageString("create.target.create");
                    target.sendMessage(ChatColor.RED + create.replace("%size", "small").replace("%player", player.getName()));
                    target.sendMessage(plugin.getLanguageString("config.open"));
                    return;
                }
            }else{
                String create = plugin.getLanguageString("create.perm");
                player.sendMessage(ChatColor.RED + create.replace("%size", "small"));
                e.setCancelled(true);
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase(medium.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.medium"))))) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(player.hasPermission("bp.medium")) {
                if (e.getSlot() == 49) {
                    player.closeInventory();
                    customName.put(player.getUniqueId(),player.getCustomName());
                    player.setCustomName("createMedium");
                    player.sendMessage(ChatColor.GRAY + plugin.getLanguageString("gui.browser"));
                    return;
                }

                for (int i = 0; i < glass_slots.length; i++) {
                    if (e.getSlot() == glass_slots[i]) {
                        head = false;
                        i = glass_slots.length;
                    } else {
                        head = true;
                    }
                }

                if (head) {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.already"));
                                return;
                            }
                            String title = plugin.getLanguageString("config.title");

                            Inventory inventory = Bukkit.createInventory(player, mediumSize * 9, title.replace("%player",player.getName()));
                            playerBackpacks.put(player.getUniqueId(), inventory);
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.target.perm"));
                        }
                        return;
                    }

                    if (playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("create.target.already"));
                        return;
                    }

                    if (target.getUniqueId() == player.getUniqueId()) {
                        String title = plugin.getLanguageString("config.title");
                        Inventory inventory = Bukkit.createInventory(null, mediumSize * 9, title.replace("%player",player.getName()));
                        playerBackpacks.put(player.getUniqueId(), inventory);
                        player.openInventory(inventory);
                        return;
                    }

                    String title = plugin.getLanguageString("config.title");

                    Inventory inventory = Bukkit.createInventory(player, mediumSize * 9, title.replace("%palyer",target.getName()));
                    playerBackpacks.put(target.getUniqueId(), inventory);
                    String create = plugin.getLanguageString("create.target.create");
                    target.sendMessage(ChatColor.RED + create.replace("%size", "medium").replace("%player", player.getName()));
                    target.sendMessage(plugin.getLanguageString("config.open"));
                    player.openInventory(inventory);
                }
            }else{
                String create = plugin.getLanguageString("create.perm");
                player.sendMessage(ChatColor.RED + create.replace("%size", "medium"));
                e.setCancelled(true);
            }
        }

        if (e.getView().getTitle().equalsIgnoreCase(large.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.large"))))) {
            e.setCancelled(true);

            if(e.getSlot() == 53){
                Inventory createGUI = guis.createGUI(player);
                player.openInventory(createGUI);
                return;
            }

            if(e.getSlot() == 49){
                player.closeInventory();
                customName.put(player.getUniqueId(),player.getCustomName());
                player.setCustomName("createLarge");
                player.sendMessage(ChatColor.GRAY + plugin.getLanguageString("gui.browser"));
                return;
            }

            for (int i=0;i<glass_slots.length;i++){
                if(e.getSlot() == glass_slots[i]){
                    head = false;
                }else{
                    head = true;
                }
            }

            if(head){
                SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                Player target = (Player) skullMeta.getOwningPlayer();

                if(!player.hasPermission("bp.admin")) {
                    if(target.getUniqueId() == player.getUniqueId()) {

                        if (playerBackpacks.containsKey(target.getUniqueId())) {
                            e.setCancelled(true);
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.already"));
                            return;
                        }
                        String title = plugin.getLanguageString("config.title");
                        Inventory inventory = Bukkit.createInventory(player,largeSize * 9,title.replace("%player",player.getName()));
                        playerBackpacks.put(player.getUniqueId(),inventory);
                        player.openInventory(inventory);
                        return;
                    }else{
                        player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.perm"));
                    }
                    return;
                }

                if (playerBackpacks.containsKey(target.getUniqueId())) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("create.target.already"));
                    return;
                }

                if(target.getUniqueId() == player.getUniqueId()){
                    String title = plugin.getLanguageString("config.title");
                    Inventory inventory = Bukkit.createInventory(null, largeSize * 9,title.replace("%player",player.getName()));
                    playerBackpacks.put(player.getUniqueId(), inventory);
                    player.openInventory(inventory);
                    return;
                }

                String title = plugin.getLanguageString("config.title");

                Inventory inventory = Bukkit.createInventory(player,largeSize * 9,title.replace("%player",target.getName()));
                playerBackpacks.put(target.getUniqueId(),inventory);
                String create = plugin.getLanguageString("create.target.create");
                target.sendMessage(ChatColor.RED + create.replace("%size", "large").replace("%player", player.getName()));
                target.sendMessage(plugin.getLanguageString("config.open"));
                player.openInventory(inventory);
            }else{
                String create = plugin.getLanguageString("create.perm");
                player.sendMessage(ChatColor.RED + create.replace("%size", "large"));
                e.setCancelled(true);
            }
        }
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
