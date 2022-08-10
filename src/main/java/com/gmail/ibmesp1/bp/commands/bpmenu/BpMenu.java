package com.gmail.ibmesp1.bp.commands.bpmenu;

import com.gmail.ibmesp1.bp.Backpacks;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BpMenu implements CommandExecutor{

    private final Backpacks plugin;
    private final GUIs guis;

    public BpMenu(Backpacks plugin,GUIs guis){
        this.plugin = plugin;
        this.guis = guis;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player) ){
            return false;
        }

        Player player = (Player) sender;

        if(args.length > 0){
            player.sendMessage(ChatColor.RED + plugin.getLanguageString("config.exist"));
            return false;
        }

        BpEasterEgg bpEasterEgg;
        if(!player.hasPermission("bp.admin")){
            int easterEgg = (int) (Math.random() * 100);

            Inventory gui = Bukkit.createInventory(player,3*9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
            bpEasterEgg = new BpEasterEgg();

            gui = guis.menuGUI(gui, bpEasterEgg,easterEgg);

            player.openInventory(gui);
            return false;
        }

        int easterEgg = (int) (Math.random() * 100);

        Inventory gui = Bukkit.createInventory(player,3*9,ChatColor.translateAlternateColorCodes('&',plugin.getLanguageString("gui.title")));
        bpEasterEgg = new BpEasterEgg();

        gui = guis.menuOPGUI(gui, bpEasterEgg,easterEgg);

        player.openInventory(gui);

        return false;
    }
}