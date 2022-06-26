package com.gmail.ibmesp1.bp.commands.bpcommand.subcommands;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.SubCommand;
import com.gmail.ibmesp1.bp.utils.UUIDFetcher;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DeleteSubCommand extends SubCommand {

    private final Backpacks plugin;
    private final HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    private BackpackManager bpm;

    public DeleteSubCommand(Backpacks plugin, HashMap<UUID, HashMap<String, Inventory>> playerBackpack, BackpackManager bpm) {
        this.plugin = plugin;
        this.playerBackpack = playerBackpack;
        this.bpm = bpm;
    }
    @Override
    public String getName() {
        return "delete";
    }


    @Override
    public List<String> getSubCommandsArgs(String[] args) {
        List<String> pargs = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers())
        {
            pargs.add(p.getName());
        }
        return pargs;
    }

    @Override
    public void perform(Player player, String[] args) {

        if(args.length == 1){
            GUI(player,player.getUniqueId(),player.getName());
        }else if(args.length == 2){
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                try {
                    UUID uuid = UUIDFetcher.getUUIDOf(args[1]);

                    GUI(player,uuid,args[1]);
                } catch (Exception ignored) {}
            }else{
                GUI(player,target.getUniqueId(),target.getName());
            }
        }else{
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("delete.config.exist"));
        }
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
