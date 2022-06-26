package com.gmail.ibmesp1.bp.commands.bpcommand;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.utils.UUIDFetcher;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackGUI;
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

import java.util.HashMap;
import java.util.UUID;

public class BpEvents implements Listener {

    private final Backpacks plugin;
    private final HashMap<UUID,HashMap<String,Inventory>> playerBackpack;
    private final BackpackManager bpm;

    public BpEvents(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpm = bpm;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(plugin.getLanguageString("config.backpacks"))){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            String key = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName();

            Inventory inventory = playerBackpack.get(player.getUniqueId()).get(key);
            player.openInventory(inventory);
            return;
        }

        String title = "Delete %player backpack";
        String[] parts = title.split(" ");

        if(e.getView().getTitle().startsWith(parts[0])){
            e.setCancelled(true);
            String key = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName();
            Player player = (Player) e.getWhoClicked();
            AnvilGUI.Builder builder = new AnvilGUI.Builder().onComplete((p,s) ->{
                if(s.equals("confirm")){
                    try {
                        String invTitle = e.getView().getTitle();
                        String[] invParts = invTitle.split(" ");
                        UUID uuid = UUIDFetcher.getUUIDOf(invParts[1]);
                        if(player.getUniqueId().equals(uuid)){
                            Player target = Bukkit.getPlayer(uuid);

                            Inventory prevInventory = playerBackpack.get(uuid).get(key);
                            if(prevInventory == null){
                                player.sendMessage(ChatColor.RED + player.getName() + plugin.getLanguageString("delete.target.notBackpack"));
                                return AnvilGUI.Response.close();
                            }
                            int size = prevInventory.getSize();

                            for (int i = 0; i < size; i++) {
                                try {
                                    player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
                                } catch (IllegalArgumentException | NullPointerException ignored) {
                                }
                            }

                            HashMap<String,Inventory> invs = playerBackpack.get(target.getUniqueId());
                            invs.remove(key);
                            playerBackpack.put(target.getUniqueId(),invs);
                            bpm.deletePlayerBackPacks(uuid,key);
                            player.sendMessage(ChatColor.GREEN + target.getName() + plugin.getLanguageString("delete.target.deleted"));
                        }else if(Bukkit.getPlayer(uuid) != null){
                            if(!player.hasPermission("bp.admin")){
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
                                e.setCancelled(true);
                                return AnvilGUI.Response.close();
                            }
                            Player target = Bukkit.getPlayer(uuid);
                            Inventory prevInventory = playerBackpack.get(uuid).get(key);
                            if(prevInventory == null){
                                player.sendMessage(ChatColor.RED + target.getName() + plugin.getLanguageString("delete.target.notBackpack"));
                                return AnvilGUI.Response.close();
                            }

                            if(target.getOpenInventory().getTitle().equals(title.replace("%player",target.getName()))){
                                target.closeInventory();
                            }

                            int size = prevInventory.getSize();

                            for (int i = 0; i < size; i++) {
                                try {
                                    target.getLocation().getWorld().dropItem(target.getLocation(), prevInventory.getItem(i));
                                } catch (IllegalArgumentException | NullPointerException ignored) {
                                }
                            }

                            HashMap<String,Inventory> invs = playerBackpack.get(target.getUniqueId());
                            invs.remove(key);
                            playerBackpack.put(target.getUniqueId(),invs);
                            bpm.deletePlayerBackPacks(uuid,key);
                            target.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.target.deletedBy") + player.getName());
                            player.sendMessage(ChatColor.GREEN + target.getName() + plugin.getLanguageString("delete.target.deleted"));
                        }else {
                            String deleteTitle = e.getView().getTitle();
                            String[] detParts = deleteTitle.split(" ");
                            if(!player.hasPermission("bp.admin")){
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.perms"));
                                return AnvilGUI.Response.close();
                            }

                            Inventory prevInventory = playerBackpack.get(uuid).get(key);
                            if(prevInventory == null){
                                player.sendMessage(ChatColor.RED + detParts[1] + plugin.getLanguageString("delete.target.notBackpack"));
                                return AnvilGUI.Response.close();
                            }
                            int size = prevInventory.getSize();

                            for (int i = 0; i < size; i++) {
                                try {
                                    player.getLocation().getWorld().dropItem(player.getLocation(), prevInventory.getItem(i));
                                } catch (IllegalArgumentException | NullPointerException ignored) {
                                }
                            }

                            HashMap<String,Inventory> invs = playerBackpack.get(uuid);
                            invs.remove(key);
                            playerBackpack.put(uuid,invs);
                            bpm.deletePlayerBackPacks(uuid,key);
                            player.sendMessage(ChatColor.GREEN + detParts[1] + plugin.getLanguageString("delete.target.deleted"));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return AnvilGUI.Response.close();
            }).text(plugin.getLanguageString("delete.confirm"))
                    .itemLeft(new ItemStack(Material.PAPER))
                    .plugin(plugin);

            Bukkit.getScheduler().runTask(plugin,()-> builder.open(player));
        }

        String see = "%player's Backpacks";
        String[] seeParts = see.split(" ");

        if(e.getView().getTitle().endsWith(seeParts[1])){
            e.setCancelled(true);
            String key = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName();
            Player player = (Player) e.getWhoClicked();

            String[] name = e.getView().getTitle().split("'");

            Player target = Bukkit.getPlayer(name[0]);

            if(target == null) {
                try {
                    UUID targetUUID = UUIDFetcher.getUUIDOf(name[0]);

                    Inventory inventory = playerBackpack.get(targetUUID).get(key);
                    int size = inventory.getSize();
                    String getTitle = plugin.getLanguageString("config.title");
                    String bpTitle = getTitle.replace("%player",name[0]);
                    player.openInventory(new BackpackGUI(size,bpTitle,player,targetUUID,key,bpm,plugin).getInventory());
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(player.getUniqueId() == target.getUniqueId())
            {
                Inventory inventory = playerBackpack.get(player.getUniqueId()).get(key);
                int size = inventory.getSize();
                String getTitle = plugin.getLanguageString("config.title");
                String bpTitle = getTitle.replace("%player",player.getName());
                player.openInventory(new BackpackGUI(size,bpTitle,player,player.getUniqueId(),key,bpm,plugin).getInventory());
                return;
            }

            Inventory inventory = playerBackpack.get(target.getUniqueId()).get(key);
            int size = inventory.getSize();
            String getTitle = plugin.getLanguageString("config.title");
            String bpTitle = getTitle.replace("%player",target.getName());
            player.openInventory(new BackpackGUI(size,bpTitle,player,target.getUniqueId(),key,bpm,plugin).getInventory());
        }
    }
}
