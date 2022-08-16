package com.gmail.ibmesp1.bp.commands.bpmenu.guis;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.ibcore.guis.MenuItems;
import com.gmail.ibmesp1.ibcore.skull.Skulls;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GUIs {

    private final Backpacks plugin;
    private final HashMap<UUID,HashMap<String, Inventory>> playerBackpacks;
    private final DataManager bpcm;
    private final List<Player> playerList;
    private final int mediumSize;
    private final int largeSize;
    private final MenuItems menuItems;
    private final Skulls skulls;


    public GUIs(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpacks, DataManager bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.bpcm = bpcm;
        this.playerList = plugin.playerList;
        menuItems = new MenuItems();
        skulls = new Skulls();

        mediumSize = plugin.bpcm.getConfig().getInt("mediumSize");
        largeSize = plugin.bpcm.getConfig().getInt("largeSize");
    }

    public Inventory menuOPGUI(Inventory gui, BpEasterEgg bpEasterEgg, int easterEgg){
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,12,14,16,17,18,19,20,21,22,23,24,25,26};

        ItemStack create = menuItems.createItem(Material.CHEST,ChatColor.GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.create"),null);

        ItemStack delete = menuItems.createItem(Material.TNT,ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.delete"),null);

        ItemStack config = menuItems.createItem(Material.REDSTONE,ChatColor.RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.config"),null);

        for(int slot:slots){
            gui.setItem(slot,menuItems.glass());
        }

        bpEasterEgg.easterEgg(easterEgg,gui);

        gui.setItem(11,create);
        gui.setItem(13,config);
        gui.setItem(15,delete);

        return gui;
    }

    public Inventory menuGUI(Inventory gui,BpEasterEgg bpEasterEgg,int easterEgg){
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,12,13,14,16,17,18,19,20,21,22,23,24,25,26};

        ItemStack create = menuItems.createItem(Material.CHEST,ChatColor.GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.create"),null);

        ItemStack delete = menuItems.createItem(Material.CHEST,ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.delete"),null);


        for(int slot:slots){
            gui.setItem(slot,menuItems.glass());
        }

        bpEasterEgg.easterEgg(easterEgg,gui);

        gui.setItem(11,create);
        gui.setItem(15,delete);

        return gui;
    }

    public Inventory configGUI(Player player){
        Inventory configGUI = Bukkit.createInventory(player, 4*9, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.items.configuration")));
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,21,23,25,26,27,28,29,30,31,32,33,34};

        ArrayList<String> kbLore = new ArrayList<>();
        kbLore.add(plugin.getLanguageString("gui.items.current") + bpcm.getConfig().getBoolean("keepBackpack"));
        ItemStack keepBackpack = menuItems.createItem(Material.TOTEM_OF_UNDYING,plugin.getLanguageString("gui.items.keepBackpack"),kbLore);

        ItemStack small = menuItems.createItem(Material.CHEST, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.small")),null);

        ItemStack medium = menuItems.createItem(Material.CHEST,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.medium")),null);

        ItemStack large = menuItems.createItem(Material.CHEST,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.large")),null);

        ItemStack back = menuItems.back(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));

        for (int slot : slots) {
            configGUI.setItem(slot, menuItems.glass());
        }

        configGUI.setItem(13, keepBackpack);
        configGUI.setItem(20, small);
        configGUI.setItem(22, medium);
        configGUI.setItem(24, large);
        configGUI.setItem(35,back);

        return configGUI;
    }

    public Inventory createGUI(Player player){
        Inventory createGUI = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.items.size")));
        int[] slots = {0, 1, 3, 5, 7};

        ItemStack small = menuItems.createItem(Material.CHEST,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.small")),null);

        ItemStack medium = menuItems.createItem(Material.CHEST,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.medium")),null);

        ItemStack large = menuItems.createItem(Material.CHEST,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString( "gui.size.large") ),null);

        ItemStack back = menuItems.back(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));

        for (int slot : slots) {
            createGUI.setItem(slot, menuItems.glass());
        }

        createGUI.setItem(2, small);
        createGUI.setItem(4, medium);
        createGUI.setItem(6, large);
        createGUI.setItem(8,back);

        return createGUI;
    }

    public Inventory deleteGUI(int page){
        Inventory deleteGUI = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.delete.title")));

        /*ItemStack anvil = menuItems.search(plugin.getLanguageString("gui.items.search"));
        deleteGUI.setItem(49,anvil);*/

        ItemStack back = menuItems.back(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        deleteGUI.setItem(53,back);

        deleteGUI.setItem(48,menuItems.glass());
        deleteGUI.setItem(50,menuItems.glass());

        if(page != 0 && plugin.getConfig().getBoolean("paginatedGUI")) {
            ItemStack previousPage = menuItems.createItem(Material.ARROW,plugin.getLanguageString("gui.previousPage"),null);
            deleteGUI.setItem(48, previousPage);
        }

        if(page < getPages() && plugin.getConfig().getBoolean("paginatedGUI")) {
            ItemStack nextPage = menuItems.createItem(Material.ARROW,plugin.getLanguageString("gui.nextPage"),null);
            deleteGUI.setItem(50, nextPage);
        }

        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,49,51,52};
        for (int slot : glass_slots) {
            deleteGUI.setItem(slot, menuItems.glass());
        }

        int startingIndex = page * 28;

        if(playerList.size() < 29 || !plugin.getConfig().getBoolean("paginatedGUI")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                String name = p.getName();

                ArrayList<String> lore = new ArrayList<>();
                if (playerBackpacks.containsKey(p.getUniqueId())) {
                    lore.add(ChatColor.GREEN + plugin.getLanguageString("gui.items.has").replace("%player%",name));
                } else {
                    lore.add(ChatColor.RED +  plugin.getLanguageString("gui.items.hasNot").replace("%player%",name));
                }

                deleteGUI.addItem(skulls.playerSkull(p,lore));
            }
            return deleteGUI;
        }

        if(plugin.getConfig().getBoolean("paginatedGUI")){
            for(int i = startingIndex;i<startingIndex + 28 && i < playerList.size();i++){

                Player p = playerList.get(i);

                String name = p.getName();

                ArrayList<String> lore = new ArrayList<>();
                if(playerBackpacks.containsKey(p.getUniqueId())){
                    lore.add(ChatColor.GREEN + plugin.getLanguageString("gui.items.has").replace("%player%",name));
                }else{
                    lore.add(ChatColor.RED + plugin.getLanguageString("gui.items.hasNot").replace("%player%",name));
                }

                deleteGUI.addItem(skulls.playerSkull(p,lore));
            }
        }

        return deleteGUI;
    }

    public Inventory sizeConfigGUI(String path,String title){
        int size = bpcm.getConfig().getInt(path + "Size");

        Inventory sizeGUI = Bukkit.createInventory(null,9, ChatColor.translateAlternateColorCodes('&',title));

        ItemStack back = menuItems.back(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));

        for(int i=1;i<7;i++){
            ItemStack min = new ItemStack(Material.PAPER,i);
            ItemMeta min_meta = min.getItemMeta();
            min_meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + i);
            if(i==size){
                min_meta.addEnchant(Enchantment.DURABILITY,1,true);
            }
            min.setItemMeta(min_meta);
            if(i>=5){
                sizeGUI.setItem(i+1,min);
            }else if(i==4){
                sizeGUI.setItem(i+1,min);
            }else{
                sizeGUI.setItem(i,min);
            }
        }
        sizeGUI.setItem(0,menuItems.glass());
        sizeGUI.setItem(4,menuItems.glass());
        sizeGUI.setItem(8,back);

        return sizeGUI;
    }

    public Inventory sizeGUI(String size,int page){

        String create = plugin.getLanguageString("gui.create.title");

        Inventory sizeGUI = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&',create.replace("%size%", size)));
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,49,51,52};

        /*ItemStack anvil = menuItems.search(plugin.getLanguageString("gui.items.search"));
        sizeGUI.setItem(49,anvil);*/

        ItemStack back = menuItems.back(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        sizeGUI.setItem(53,back);

        sizeGUI.setItem(48,menuItems.glass());
        sizeGUI.setItem(50,menuItems.glass());

        if(page != 0 && plugin.getConfig().getBoolean("paginatedGUI")) {
            ItemStack previousPage = menuItems.createItem(Material.ARROW,plugin.getLanguageString("gui.previousPage"),null);
            sizeGUI.setItem(48, previousPage);
        }

        if(page < getPages() && plugin.getConfig().getBoolean("paginatedGUI")) {
            ItemStack nextPage = menuItems.createItem(Material.ARROW,plugin.getLanguageString("gui.nextPage"),null);
            sizeGUI.setItem(50, nextPage);
        }

        for (int slot : glass_slots) {
            sizeGUI.setItem(slot, menuItems.glass());
        }

        int startingIndex = page * 28;

        if(playerList.size() < 29 || !plugin.getConfig().getBoolean("paginatedGUI"))
        {
            for (Player p : Bukkit.getOnlinePlayers()) {
                String name = p.getName();

                ArrayList<String> lore = new ArrayList<>();
                if(playerBackpacks.containsKey(p.getUniqueId())){
                    lore.add(ChatColor.GREEN + plugin.getLanguageString("gui.items.has").replace("%player%",name));
                }else{
                    lore.add(ChatColor.RED + plugin.getLanguageString("gui.items.hasNot").replace("%player%",name));
                }

                sizeGUI.addItem(skulls.playerSkull(p,lore));
            }
            return sizeGUI;
        }

        if(plugin.getConfig().getBoolean("paginatedGUI")){
            for(int i = startingIndex;i<startingIndex + 28 && i < playerList.size();i++){

                Player p = playerList.get(i);

                String name = p.getName();

                ArrayList<String> lore = new ArrayList<>();
                if(playerBackpacks.containsKey(p.getUniqueId())){
                    lore.add(ChatColor.GREEN + plugin.getLanguageString("gui.items.has").replace("%player%",name));
                }else{
                    lore.add(ChatColor.RED + plugin.getLanguageString("gui.items.hasNot").replace("%player%",name));
                }

                sizeGUI.addItem(skulls.playerSkull(p,lore));
            }
        }
        return sizeGUI;
    }

    public void delete(Player player,UUID uuid,String name) {
        Inventory inventory = Bukkit.createInventory(player,(plugin.rowsBP+2)*9,"Delete %player% backpack".replace("%player%",name));
        int[] glass_slots = new int[18];
        int j = 9;

        if(plugin.backpacks.getConfig().getConfigurationSection(uuid + ".") == null)
            return;

        for(int h=0;h<9;h++)
            glass_slots[h] = h;

        for(int i=(plugin.rowsBP+2)*9-1;i>(plugin.rowsBP+2)*9-10;i--){
            glass_slots[j] = i;
            j++;
        }

        for(int slot:glass_slots){
            inventory.setItem(slot, menuItems.glass());
        }

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

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player,(plugin.rowsBP+2)*9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("config.backpacks")));
        int[] glass_slots = new int[18];
        int j = 9;

        if(plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".") == null)
            return;

        for(int h=0;h<9;h++){
            glass_slots[h] = h;
        }

        for(int i=(plugin.rowsBP+2)*9-1;i>(plugin.rowsBP+2)*9-10;i--){
            glass_slots[j] = i;
            j++;
        }

        for(int slot:glass_slots){
            inventory.setItem(slot, menuItems.glass());
        }

        Set<String> set = plugin.backpacks.getConfig().getConfigurationSection(player.getUniqueId() + ".").getKeys(false);
        int i = 0;

        for(String key:set){
            int size = plugin.playerBackpack.get(player.getUniqueId()).get(key).getSize();
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

    public int getPages(){
        if(!plugin.getConfig().getBoolean("paginatedGUI") || playerList.size() < 29){
            return 0;
        }
        return Bukkit.getOnlinePlayers().size()/28;
    }
}