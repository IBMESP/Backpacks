package com.gmail.ibmesp1.commands.bpmenu.guis;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.BpEasterEgg;
import com.gmail.ibmesp1.data.DataManger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GUIs {

    private Backpacks plugin;
    private HashMap<UUID,Inventory> playerBackpacks;
    private DataManger bpcm;

    public GUIs(Backpacks plugin, HashMap<UUID, Inventory> playerBackpacks,DataManger bpcm) {
        this.plugin = plugin;
        this.playerBackpacks = playerBackpacks;
        this.bpcm = bpcm;
    }

    private ItemStack glass(){
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glass_meta = glass.getItemMeta();
        glass_meta.setDisplayName(" ");
        glass.setItemMeta(glass_meta);
        return glass;
    }

    public Inventory menuOPGUI(Inventory gui,BpEasterEgg bpEasterEgg,int easterEgg){
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,12,14,16,17,18,19,20,21,22,23,24,25,26};

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glass_meta = glass.getItemMeta();
        glass_meta.setDisplayName(" ");
        glass.setItemMeta(glass_meta);

        ItemStack create = new ItemStack(Material.CHEST);
        ItemMeta create_meta = glass.getItemMeta();
        create_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.create"));
        create.setItemMeta(create_meta);

        ItemStack delete = new ItemStack(Material.TNT);
        ItemMeta delete_meta = glass.getItemMeta();
        delete_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.delete"));
        delete.setItemMeta(delete_meta);

        ItemStack config = new ItemStack(Material.REDSTONE);
        ItemMeta config_meta = config.getItemMeta();
        config_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.config"));
        config.setItemMeta(config_meta);

        /*ItemStack debug = new ItemStack(Material.BARRIER);
        ItemMeta debug_meta = debug.getItemMeta();
        debug_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Debug");
        debug.setItemMeta(config_meta);*/

        for(int slot:slots){
            gui.setItem(slot,glass);
        }

        bpEasterEgg.easterEgg(easterEgg,gui);

        gui.setItem(11,create);
        gui.setItem(13,config);
        gui.setItem(15,delete);
        //gui.setItem(26,debug);

        return gui;
    }

    public Inventory menuGUI(Inventory gui,BpEasterEgg bpEasterEgg,int easterEgg){
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,12,13,14,16,17,18,19,20,21,22,23,24,25,26};

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glass_meta = glass.getItemMeta();
        glass_meta.setDisplayName(" ");
        glass.setItemMeta(glass_meta);

        ItemStack create = new ItemStack(Material.CHEST);
        ItemMeta create_meta = glass.getItemMeta();
        create_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.create"));
        create.setItemMeta(create_meta);

        ItemStack delete = new ItemStack(Material.TNT);
        ItemMeta delete_meta = glass.getItemMeta();
        delete_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.items.delete"));
        delete.setItemMeta(delete_meta);


        for(int slot:slots){
            gui.setItem(slot,glass);
        }

        bpEasterEgg.easterEgg(easterEgg,gui);

        gui.setItem(11,create);
        gui.setItem(15,delete);

        return gui;
    }

    public Inventory configGUI(Player player){

        Inventory configGUI = Bukkit.createInventory(player, 4*9, plugin.getLanguageString("gui.items.configuration"));
        int[] slots = {0,1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,21,23,25,26,27,28,29,30,31,32,33,34};

        ItemStack keepBackpack = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta kbMeta = keepBackpack.getItemMeta();
        kbMeta.setDisplayName(plugin.getLanguageString("gui.items.keepBackpack"));
        ArrayList<String> kbLore = new ArrayList<>();
        kbLore.add(plugin.getLanguageString("gui.items.current") + bpcm.getConfig().getBoolean("keepBackpack"));
        kbMeta.setLore(kbLore);
        keepBackpack.setItemMeta(kbMeta);

        ItemStack small = new ItemStack(Material.CHEST);
        ItemMeta small_meta = small.getItemMeta();
        small_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + plugin.getLanguageString("gui.size.small"));
        small.setItemMeta(small_meta);

        ItemStack medium = new ItemStack(Material.CHEST);
        ItemMeta medium_meta = medium.getItemMeta();
        medium_meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + plugin.getLanguageString("gui.size.medium"));
        medium.setItemMeta(medium_meta);

        ItemStack large = new ItemStack(Material.CHEST);
        ItemMeta large_meta = large.getItemMeta();
        large_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.size.large"));
        large.setItemMeta(large_meta);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        back.setItemMeta(back_meta);

        for (int slot : slots) {
            configGUI.setItem(slot, glass());
        }

        configGUI.setItem(13, keepBackpack);
        configGUI.setItem(20, small);
        configGUI.setItem(22, medium);
        configGUI.setItem(24, large);
        configGUI.setItem(35,back);

        return configGUI;
    }

    public Inventory createGUI(Player player){
        Inventory createGUI = Bukkit.createInventory(player, 9, plugin.getLanguageString("gui.items.size"));
        int[] slots = {0, 1, 3, 5, 7};

        ItemStack small = new ItemStack(Material.CHEST);
        ItemMeta small_meta = small.getItemMeta();
        small_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + capitalizeFirstLetter(plugin.getLanguageString("gui.size.small")));
        small.setItemMeta(small_meta);

        ItemStack medium = new ItemStack(Material.CHEST);
        ItemMeta medium_meta = medium.getItemMeta();
        medium_meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + plugin.getLanguageString("gui.size.medium"));
        medium.setItemMeta(medium_meta);

        ItemStack large = new ItemStack(Material.CHEST);
        ItemMeta large_meta = large.getItemMeta();
        large_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + plugin.getLanguageString("gui.size.large"));
        large.setItemMeta(large_meta);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        back.setItemMeta(back_meta);

        for (int slot : slots) {
            createGUI.setItem(slot, glass());
        }

        createGUI.setItem(2, small);
        createGUI.setItem(4, medium);
        createGUI.setItem(6, large);
        createGUI.setItem(8,back);

        return createGUI;
    }

    public Inventory deleteGUI(){
        Inventory deleteGUI = Bukkit.createInventory(null, 54, plugin.getLanguageString("gui.delete.title"));

        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta anvilMeta = anvil.getItemMeta();
        anvilMeta.setDisplayName(plugin.getLanguageString("gui.items.search"));
        anvil.setItemMeta(anvilMeta);
        deleteGUI.setItem(49,anvil);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        back.setItemMeta(back_meta);

        deleteGUI.setItem(53,back);

        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52};
        for (int slot : glass_slots) {
            deleteGUI.setItem(slot, glass());
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            String name = p.getName();

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            meta.setOwningPlayer(p);

            if(playerBackpacks.containsKey(p.getUniqueId())){
                lore.add(ChatColor.GREEN + name + plugin.getLanguageString("gui.items.has"));
            }else {
                lore.add(ChatColor.RED + name + plugin.getLanguageString("gui.items.hasNot"));
            }
            meta.setLore(lore);
            meta.setDisplayName(ChatColor.GOLD + name);
            skull.setItemMeta(meta);

            deleteGUI.addItem(skull);
        }

        return deleteGUI;
    }

    public Inventory sizeConfigGUI(String path,String title){
        int size = bpcm.getConfig().getInt(path + "Size");

        Inventory sizeGUI = Bukkit.createInventory(null,9, title);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        back.setItemMeta(back_meta);

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
        sizeGUI.setItem(0,glass());
        sizeGUI.setItem(4,glass());
        sizeGUI.setItem(8,back);

        return sizeGUI;
    }

    public Inventory sizeGUI(String size){

        String create = plugin.getLanguageString("gui.create.title");

        Inventory sizeGUI = Bukkit.createInventory(null, 54, create.replace("%size", size));
        int[] glass_slots = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52};

        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta anvilMeta = anvil.getItemMeta();
        anvilMeta.setDisplayName(plugin.getLanguageString("gui.items.search"));
        anvil.setItemMeta(anvilMeta);
        sizeGUI.setItem(49,anvil);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.DARK_RED + plugin.getLanguageString("gui.items.back"));
        back.setItemMeta(back_meta);

        for (int slot : glass_slots) {
            sizeGUI.setItem(slot, glass());
        }
        sizeGUI.setItem(53,back);

        for (Player p : Bukkit.getOnlinePlayers()) {
            String name = p.getName();

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            ArrayList<String> lore = new ArrayList<>();

            meta.setOwningPlayer(p);
            if(playerBackpacks.containsKey(p.getUniqueId())){
                lore.add(ChatColor.GREEN + name + plugin.getLanguageString("gui.items.has"));
            }else{
                lore.add(ChatColor.RED + name + plugin.getLanguageString("gui.items.hasNot"));
            }
            meta.setLore(lore);
            meta.setDisplayName(ChatColor.GOLD + name);
            skull.setItemMeta(meta);

            sizeGUI.addItem(skull);
        }

        return sizeGUI;
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}