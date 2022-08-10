package com.gmail.ibmesp1.bp.commands.bpmenu;

import com.gmail.ibmesp1.ibcore.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BpEasterEgg {

    public BpEasterEgg() {}

    public void easterEgg(int easterEgg, Inventory gui){

        if(easterEgg == 99){
            try {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                headMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUIDFetcher.getUUIDOf("5fb335ed-14b5-4ec3-b079-eef528168ecd")));
                headMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Thanks KingDucky_TTV");
                head.setItemMeta(headMeta);
                gui.setItem(26,head);
            } catch (Exception ignored) {}

        }
    }
}
