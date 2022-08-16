package com.gmail.ibmesp1.bp.commands.bpmenu.guis.delete;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.ibcore.guis.MenuItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class DeleteGUI implements Listener {

    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpacks;
    private boolean head;
    private final GUIs guis;
    private final int mediumSize;
    private final int largeSize;
    private final MenuItems menuItems;


    public DeleteGUI(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, MenuItems menuItems,GUIs guis) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.menuItems = menuItems;
        this.guis = guis;

        mediumSize = plugin.bpcm.getConfig().getInt("mediumSize");
        largeSize = plugin.bpcm.getConfig().getInt("largeSize");
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if(e.getClickedInventory() == null)
            return;

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.delete.title")))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            int[] glass_slots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52};

           /* if (e.getSlot() == 49) {

                if (!player.hasPermission("bp.admin")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.perm"));
                    return;
                }

                player.closeInventory();
                AnvilGUI.Builder builder = new AnvilGUI.Builder().onComplete((p, s)->{
                            deleteBrowser(s,p);
                            return AnvilGUI.Response.close();
                        }).title(plugin.getLanguageString("gui.browser"))
                        .itemLeft(new ItemStack(Material.PAPER))
                        .text("")
                        .plugin(plugin);

                Bukkit.getScheduler().runTask(plugin,()-> builder.open(player));
                return;
            }*/

            if(e.getSlot() == 50){
                int page = plugin.playerPage.get(player.getUniqueId());
                if(page >= guis.getPages()){
                    return;
                }
                page++;
                Inventory deleteGUI = guis.deleteGUI(page);
                player.openInventory(deleteGUI);
                plugin.playerPage.put(player.getUniqueId(),page);
                return;
            }

            if(e.getSlot() == 48){
                int page = plugin.playerPage.get(player.getUniqueId());
                if(page < 1){
                    return;
                }
                page--;
                Inventory deleteGUI = guis.deleteGUI(page);
                player.openInventory(deleteGUI);
                plugin.playerPage.put(player.getUniqueId(),page);
                return;
            }

            if (e.getSlot() == 53) {

                BpEasterEgg bpEasterEgg;
                if (!player.hasPermission("bp.admin")) {
                    int easterEgg = (int) (Math.random() * 100);

                    Inventory gui = Bukkit.createInventory(player, 3 * 9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
                    bpEasterEgg = new BpEasterEgg();

                    gui = guis.menuGUI(gui, bpEasterEgg, easterEgg);

                    player.openInventory(gui);
                    return;
                }

                int easterEgg = (int) (Math.random() * 100);
                Inventory gui = Bukkit.createInventory(player, 3 * 9, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
                bpEasterEgg = new BpEasterEgg();

                gui = guis.menuOPGUI(gui, bpEasterEgg, easterEgg);

                player.openInventory(gui);
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
                try {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();

                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            if (!playerBackpacks.containsKey(target.getUniqueId())) {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.notBackpack"));
                                return;
                            }
                            GUI(player,player.getUniqueId(),player.getName());
                        } else {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.perm"));
                        }
                        return;
                    }

                    if (!playerBackpacks.containsKey(target.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.notBackpack").replace("%player%",target.getName()));
                        return;
                    }
                    GUI(player,target.getUniqueId(),target.getName());
                } catch (Exception ignored) {
                }
            }
        }

    }

    /*private void deleteBrowser(String s,Player player){
        boolean isOnline = false;
        Player target;
        UUID uuid = null;

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getName().equals(s)) {
                isOnline = true;
                target = p;
                uuid = target.getUniqueId();
                break;
            }
        }

        if (!isOnline) {
            try {
                uuid = UUIDFetcher.getUUIDOf(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (!playerBackpacks.containsKey(uuid)) {
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.notBackpack").replace("%player%",s));
            return;
        }
        GUI(player,uuid,s);
    }*/

    private void GUI(Player player,UUID uuid,String name) {
        Inventory inventory = Bukkit.createInventory(player,(plugin.rowsBP+2)*9,"Delete %player% backpack".replace("%player%",name));
        int[] glass_slots = new int[18];
        int j = 9;

        for(int h=0;h<9;h++){
            glass_slots[h] = h;
        }

        for(int i=(plugin.rowsBP+2)*9-1;i>(plugin.rowsBP+2)*9-10;i--){
            glass_slots[j] = i;
            j++;
        }
        for(int slot:glass_slots){
            inventory.setItem(slot,menuItems.glass());
        }

        if(plugin.backpacks.getConfig().getConfigurationSection(uuid + ".") == null)
            return;

        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(uuid + ".").getKeys(false);
        int i = 0;

        for (String key:set){
            int size = plugin.playerBackpack.get(uuid).get(key).getSize();
            ItemStack bp = new ItemStack(Material.CHEST);
            ItemMeta bp_meta = bp.getItemMeta();

            if(size < largeSize*9)
                if(size < mediumSize*9)
                    bp_meta.setDisplayName(plugin.getLanguageString("gui.open.small"));
                else
                    bp_meta.setDisplayName(plugin.getLanguageString("gui.open.medium"));
            else
                bp_meta.setDisplayName(plugin.getLanguageString("gui.open.large"));

            List<String> lore = new ArrayList<>();
            lore.add(0,key + "");
            bp_meta.setLore(lore);
            bp.setItemMeta(bp_meta);
            inventory.setItem(i+9,bp);
            i++;
        }
        player.openInventory(inventory);
    }
}
