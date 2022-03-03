package com.gmail.ibmesp1.commands.bpmenu;

import com.gmail.ibmesp1.Backpacks;
import com.gmail.ibmesp1.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class BpMenu implements CommandExecutor {

    private final Backpacks plugin;
    private BackpackManager backpackManager;
    private BpEasterEgg bpEasterEgg;
    private GUIs guis;
    private HashMap<UUID,Inventory> playerBackpacks;

    public BpMenu(Backpacks plugin,BackpackManager backpackManager,HashMap<UUID, Inventory> playerBackpacks){
        this.plugin = plugin;
        this.backpackManager = backpackManager;
        this.playerBackpacks = playerBackpacks;
        this.guis = new GUIs(plugin,playerBackpacks);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player) ){
            return false;
        }

        Player player = (Player) sender;

        if(args.length > 0){
            player.sendMessage(ChatColor.RED + " This command doesn't exists");
            return false;
        }

        if(!player.isOp() || !player.hasPermission("bp.admin")){
            int easterEgg = (int) (Math.random() * 100);

            Inventory gui = Bukkit.createInventory(player,3*9,"Backpack Menu");
            bpEasterEgg = new BpEasterEgg(gui);

            gui = guis.menuGUI(gui,bpEasterEgg,easterEgg);

            player.openInventory(gui);
            return false;
        }

        int easterEgg = (int) (Math.random() * 100);

        Inventory gui = Bukkit.createInventory(player,3*9,"Backpack Menu");
        bpEasterEgg = new BpEasterEgg(gui);

        gui = guis.menuOPGUI(gui,bpEasterEgg,easterEgg);

        player.openInventory(gui);

        return false;
    }
}