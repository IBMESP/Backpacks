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

    private final Backpacks plugin;
    private HashMap<UUID, Inventory> playerBackpacks;
    private HashMap<UUID, String> customName;
    private boolean head;
    private final GUIs guis;
    private int smallSize;
    private int mediumSize;
    private int largeSize;
    private final DataManger bpcm;
    private final int[] glass_slots;

    public CreateGUI(Backpacks plugin, HashMap<UUID, Inventory> playerBackpacks, HashMap<UUID, String> customName, DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.customName = customName;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
        glass_slots  = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52};
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        smallSize = bpcm.getConfig().getInt("smallSize");
        mediumSize = bpcm.getConfig().getInt("mediumSize");
        largeSize = bpcm.getConfig().getInt("largeSize");

        Player player = (Player) e.getWhoClicked();

        String small = plugin.getLanguageString("gui.create.title");

        String medium = plugin.getLanguageString("gui.create.title");

        String large = plugin.getLanguageString("gui.create.title");

        if (e.getView().getTitle().equalsIgnoreCase(small.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.small"))))) {
            create(e,player,"bp.small","createSmall","small",smallSize);
        }

        if (e.getView().getTitle().equalsIgnoreCase(medium.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.medium"))))) {
            create(e,player,"bp.medium","createMedium","medium",mediumSize);
        }

        if (e.getView().getTitle().equalsIgnoreCase(large.replace("%size", capitalizeFirstLetter(plugin.getLanguageString("gui.large"))))) {
            create(e,player,"bp.large","createLarge","large",largeSize);
        }
    }

    private void create(InventoryClickEvent e,Player player,String perm,String cName,String size,int bSize){
        e.setCancelled(true);

        if(e.getSlot() == 53){
            Inventory createGUI = guis.createGUI(player);
            player.openInventory(createGUI);
            return;
        }

        if(player.hasPermission(perm)) {


            if (e.getSlot() == 49) {
                player.closeInventory();
                customName.put(player.getUniqueId(),player.getCustomName());
                player.setCustomName(cName);
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
                try {
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
                            Inventory inventory = Bukkit.createInventory(player, bSize * 9, title.replace("%player", player.getName()));
                            playerBackpacks.put(player.getUniqueId(), inventory);
                            player.closeInventory();
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
                        Inventory inventory = Bukkit.createInventory(null, bSize * 9, title.replace("%player", player.getName()));
                        playerBackpacks.put(player.getUniqueId(), inventory);
                        player.closeInventory();
                        player.openInventory(inventory);
                        return;
                    }

                    String title = plugin.getLanguageString("config.title");

                    Inventory inventory = Bukkit.createInventory(player, bSize * 9, title.replace("%player", target.getName()));
                    playerBackpacks.put(target.getUniqueId(), inventory);
                    String create = plugin.getLanguageString("create.target.create");
                    target.sendMessage(ChatColor.RED + create.replace("%size", size).replace("%player", player.getName()));
                    target.sendMessage(plugin.getLanguageString("config.open"));
                    player.closeInventory();
                } catch (Exception ignored) {}
            }
        }else{
            String create = plugin.getLanguageString("create.perm");
            player.sendMessage(ChatColor.RED + create.replace("%size", size));
            e.setCancelled(true);
        }
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
