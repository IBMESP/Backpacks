package com.gmail.ibmesp1.bp.commands.bpmenu.guis.delete;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.data.DataManager;
import com.gmail.ibmesp1.bp.utils.UUIDFetcher;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import net.wesjd.anvilgui.AnvilGUI;
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

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DeleteGUI implements Listener {

    private final Backpacks plugin;
    private HashMap<UUID, HashMap<String,Inventory>> playerBackpacks;
    private boolean head;
    private final GUIs guis;
    private BpEasterEgg bpEasterEgg;
    private DataManager bpcm;
    private BackpackManager bpm;


    public DeleteGUI(Backpacks plugin, HashMap<UUID, HashMap<String,Inventory>> playerBackpacks, DataManager bpcm, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks,bpcm);
        this.bpcm = bpcm;
        this.bpm = bpm;
    }

    @EventHandler
    public void clickGUI(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("gui.delete.title"))) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            int[] glass_slots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52};

            if (e.getSlot() == 49) {

                if (!player.hasPermission("bp.admin")) {
                    player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.perm"));
                    return;
                }

                player.closeInventory();
                player.closeInventory();
                AnvilGUI.Builder builder = new AnvilGUI.Builder().onComplete((p, s)->{
                            deleteBrowser(s,p);
                            return AnvilGUI.Response.close();
                        }).text(plugin.getLanguageString("gui.browser"))
                        .itemLeft(new ItemStack(Material.PAPER))
                        .plugin(plugin);

                Bukkit.getScheduler().runTask(plugin,()-> builder.open(player));
                return;
            }

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

                if (!player.hasPermission("bp.admin")) {
                    int easterEgg = (int) (Math.random() * 100);

                    Inventory gui = Bukkit.createInventory(player, 3 * 9, plugin.getLanguageString("gui.title"));
                    bpEasterEgg = new BpEasterEgg(gui);

                    gui = guis.menuGUI(gui, bpEasterEgg, easterEgg);

                    player.openInventory(gui);
                    return;
                }

                int easterEgg = (int) (Math.random() * 100);
                Inventory gui = Bukkit.createInventory(player, 3 * 9, plugin.getLanguageString("gui.title"));
                bpEasterEgg = new BpEasterEgg(gui);

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
                        player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("delete.target.notBackpack"));
                        return;
                    }
                    GUI(player,target.getUniqueId(),target.getName());
                } catch (Exception ignored) {
                }
            }
        }

    }

    private void deleteBrowser(String s,Player player){
        boolean isOnline = false;
        Player target = null;
        UUID uuid = null;

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getName().equals(s)) {
                isOnline = true;
                target = p;
                uuid = target.getUniqueId();
                break;
            } else {
                isOnline = false;
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
            player.sendMessage(ChatColor.RED + s + plugin.getLanguageString("delete.target.notBackpack"));
            return;
        }
        GUI(player,uuid,s);
    }

    private void GUI(Player player,UUID uuid,String name)
    {
        Inventory inventory = Bukkit.createInventory(player,3*9,"Delete %player backpack".replace("%player",name));
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26};
        for(int slot:glass_slots){
            ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta glass_meta = glass.getItemMeta();
            glass_meta.setDisplayName(" ");
            glass.setItemMeta(glass_meta);
            inventory.setItem(slot,glass);
        }
        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(uuid + ".").getKeys(false);
        int i = 0;

        for (String key:set){
            ItemStack bp = new ItemStack(Material.CHEST);
            ItemMeta bp_meta = bp.getItemMeta();
            bp_meta.setDisplayName(key + "");
            bp.setItemMeta(bp_meta);
            inventory.setItem(i+9,bp);
            i++;
        }
        player.openInventory(inventory);
    }
}
