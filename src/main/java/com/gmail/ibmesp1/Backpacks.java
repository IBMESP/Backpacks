package com.gmail.ibmesp1;

import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import com.gmail.ibmesp1.commands.bpsee.BpSee;
import com.gmail.ibmesp1.commands.keepBackpack.keepBackpack;
import com.gmail.ibmesp1.commands.keepBackpack.keepBackpackTab;
import com.gmail.ibmesp1.events.PlayerEvent;
import com.gmail.ibmesp1.utils.Metrics;
import com.gmail.ibmesp1.utils.UpdateChecker;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import com.gmail.ibmesp1.utils.backpacks.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class Backpacks extends JavaPlugin {

    public String version;
    public String name;
    public static HashMap<UUID, Inventory> playerBackpack;
    public BackpackManager bpm;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        name = ChatColor.DARK_RED + "[" + pdffile.getName() + "]";
        Logger log = Bukkit.getLogger();
        playerBackpack = new HashMap<>();

        new Metrics(this,14427);
        bpm = new BackpackManager(this,playerBackpack);

        Bukkit.getConsoleSender().sendMessage("[Backpacks] - Version: " + version + " Enabled - By Ib");
        registrerCommands();
        registerEvents();
        BackpackManager.loadBackPacks();

        getConfig().options().copyDefaults(true);
        saveConfig();

        new UpdateChecker(this,99840).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                log.info("[Backpacks] Backpacks is up to date");
            } else {
                log.warning("[Backpacks] Backpacks has a new update");
            }
        });
    }

    @Override
    public void onDisable() {
        BackpackManager.saveBackPacks();
    }

    public void registrerCommands() {
        getCommand("bp").setExecutor(new BpCommand(this,playerBackpack));
        getCommand("bpsee").setExecutor(new BpSee(this,playerBackpack,bpm));
        getCommand("bgamerule").setExecutor(new keepBackpack(this));
        getCommand("bgamerule").setTabCompleter(new keepBackpackTab());
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(this,playerBackpack),this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(),this);
    }
}
