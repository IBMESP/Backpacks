package com.gmail.ibmesp1.bp.commands.bpmenu.guis.create;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.utils.BackpackManager;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ibcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class CreateGUI implements Listener {

    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpacks;
    private boolean head;
    private final GUIs guis;
    private final BackpackManager bpm;
    private final DataManager bpcm;
    private final int[] glass_slots;

    public CreateGUI(Backpacks plugin, HashMap<UUID,HashMap<String,Inventory>> playerBackpacks, DataManager bpcm, BackpackManager bpm, GUIs guis) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.guis = guis;
        this.bpm = bpm;
        this.bpcm = bpcm;
        glass_slots  = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52};
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null)
            return;

        int smallSize = bpcm.getConfig().getInt("smallSize");
        int mediumSize = bpcm.getConfig().getInt("mediumSize");
        int largeSize = bpcm.getConfig().getInt("largeSize");


        Player player = (Player) e.getWhoClicked();

        String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.create.title"));

        if (e.getView().getTitle().equalsIgnoreCase(title.replace("%size%", Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.small"))))) {
            create(e,player,"bp.small","small", smallSize);
        }

        if (e.getView().getTitle().equalsIgnoreCase(title.replace("%size%", Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.medium"))))) {
            create(e,player,"bp.medium","medium", mediumSize);
        }

        if (e.getView().getTitle().equalsIgnoreCase(title.replace("%size%", Utils.capitalizeFirstLetter(plugin.getLanguageString("gui.large"))))) {
            create(e,player,"bp.large","large", largeSize);
        }
    }

    private void create(InventoryClickEvent e,Player player,String perm,String size,int bSize){
        e.setCancelled(true);

        if(e.getSlot() == 53){
            Inventory createGUI = guis.createGUI(player);
            player.openInventory(createGUI);
            return;
        }

        if(e.getSlot() == 50){
            int page = plugin.playerPage.get(player.getUniqueId());
            if(page >= guis.getPages()){
                return;
            }
            page++;
            Inventory sizeGUI = guis.sizeGUI(Utils.capitalizeFirstLetter(size), page);
            player.openInventory(sizeGUI);
            plugin.playerPage.put(player.getUniqueId(),page);
            return;
        }

        if(e.getSlot() == 48){
            int page = plugin.playerPage.get(player.getUniqueId());
            if(page < 1){
                return;
            }
            page--;
            Inventory sizeGUI = guis.sizeGUI(Utils.capitalizeFirstLetter(size), page);
            player.openInventory(sizeGUI);
            plugin.playerPage.put(player.getUniqueId(),page);
            return;
        }

        if(player.hasPermission(perm)) {
            /*if (e.getSlot() == 49) {
                player.closeInventory();
                AnvilGUI.Builder builder = new AnvilGUI.Builder().onComplete((p,s)->{
                    createEvent(s,p,bSize,size);
                    return AnvilGUI.Response.close();
                }).title(plugin.getLanguageString("gui.browser"))
                        .itemLeft(new ItemStack(Material.PAPER))
                        .text("")
                        .plugin(plugin);

                Bukkit.getScheduler().runTask(plugin,()-> builder.open(player));
                return;
            }*/

            for (int glass_slot : glass_slots) {
                head = e.getSlot() != glass_slot;
            }

            if (head) {
                try {
                    SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();


                    if (!player.hasPermission("bp.admin")) {
                        if (target.getUniqueId() == player.getUniqueId()) {

                            int bps = (plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") == null)
                                    ? 0 : plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false).size();

                            if(bps >= plugin.maxBP){
                                player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                                return;
                            }

                            String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title"));

                            Inventory inventory = Bukkit.createInventory(null, bSize * 9,title.replace("%player%",player.getName()));
                            if(playerBackpacks.containsKey(player.getUniqueId())){
                                HashMap<String,Inventory> invs = playerBackpacks.get(player.getUniqueId());
                                invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                                playerBackpacks.put(player.getUniqueId(), invs);
                                plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                                plugin.backpacks.saveConfig();
                            }else{
                                HashMap<String,Inventory> invs = new HashMap<>();
                                invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                                playerBackpacks.put(player.getUniqueId(), invs);
                                plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                                plugin.backpacks.saveConfig();
                            }
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.target.perm"));
                        }
                        return;
                    }

                    if (target.getUniqueId() == player.getUniqueId()) {
                        int bps = (plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") == null)
                                ? 0 : plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false).size();

                        if(bps >= plugin.maxBP){
                            player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                            return;
                        }

                        String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title"));

                        Inventory inventory = Bukkit.createInventory(null, bSize * 9,title.replace("%player%",player.getName()));
                        if(playerBackpacks.containsKey(player.getUniqueId())){
                            HashMap<String,Inventory> invs = playerBackpacks.get(player.getUniqueId());
                            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                            playerBackpacks.put(player.getUniqueId(), invs);
                            plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                            plugin.backpacks.saveConfig();
                        }else{
                            HashMap<String,Inventory> invs = new HashMap<>();
                            invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                            playerBackpacks.put(player.getUniqueId(), invs);
                            plugin.backpacks.getConfig().set(player.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                            plugin.backpacks.saveConfig();
                        }
                        player.openInventory(inventory);
                        return;
                    }

                    int bps = (plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".") == null)
                            ? 0 : plugin.backpacks.getConfig().getConfigurationSection(target.getUniqueId() + ".").getKeys(false).size();

                    if(bps >= plugin.maxBP){
                        player.sendMessage(ChatColor.RED + plugin.getLanguageString("create.maxbp"));
                        return;
                    }

                    String title = ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title"));

                    Inventory inventory = Bukkit.createInventory(null, bSize * 9,title.replace("%player%",target.getName()));
                    if(playerBackpacks.containsKey(target.getUniqueId())){
                        HashMap<String,Inventory> invs = playerBackpacks.get(target.getUniqueId());
                        invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                        playerBackpacks.put(target.getUniqueId(), invs);
                        plugin.backpacks.getConfig().set(target.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                        plugin.backpacks.saveConfig();
                    }else{
                        HashMap<String,Inventory> invs = new HashMap<>();
                        invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
                        playerBackpacks.put(target.getUniqueId(), invs);
                        plugin.backpacks.getConfig().set(target.getUniqueId() + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
                        plugin.backpacks.saveConfig();
                    }
                    String create = plugin.getLanguageString("create.target.create");
                    target.sendMessage(ChatColor.RED + create.replace("%size%", size).replace("%player%", player.getName()));
                    target.sendMessage(plugin.getLanguageString("config.open"));
                    player.closeInventory();

                } catch (Exception ignored) {}
            }
        }else{
            String create = plugin.getLanguageString("create.perm");
            player.sendMessage(ChatColor.RED + create.replace("%size%", size));
            e.setCancelled(true);
        }
    }

    /*private void createEvent(String s, Player player, int size, String Size){
        boolean isOnline = false;
        Player target = null;
        UUID uuid = null;

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getName().equals(s)){
                isOnline = true;
                target = p;
                uuid = target.getUniqueId();
                break;
            }
        }

        if(!isOnline){
            try {
                uuid = UUIDFetcher.getUUIDOf(s);
            } catch (Exception ignored) {}
        }

        Inventory inventory = Bukkit.createInventory(null, size * 9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.title").replace("%player%",s)));

        HashMap<String, Inventory> invs;
        if(playerBackpacks.containsKey(uuid)){
            invs = playerBackpacks.get(uuid);
        }else {
            invs = new HashMap<>();
        }
        invs.put(LocalDateTime.now().withNano(0).toString(),inventory);
        playerBackpacks.put(uuid, invs);
        plugin.backpacks.getConfig().set(uuid + "." + LocalDateTime.now().withNano(0),plugin.bpm.inventoryToBase64(inventory));
        plugin.backpacks.saveConfig();
        bpm.savePlayerBackPacks(uuid);

        if(target != null) {
            if(target.getUniqueId() == player.getUniqueId()){
                player.sendMessage(plugin.getLanguageString("config.open"));
                return;
            }
            String create = plugin.getLanguageString("create.target.create");
            target.sendMessage(create.replace("%player%",player.getName().replace("%size%",Size)));
            target.sendMessage(plugin.getLanguageString("config.open"));
        }
        player.sendMessage(ChatColor.GREEN + plugin.getLanguageString("create.target.created") + s);
    }*/
}
