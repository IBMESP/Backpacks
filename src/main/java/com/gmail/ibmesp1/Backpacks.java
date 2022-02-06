package com.gmail.ibmesp1;

import com.gmail.ibmesp1.commands.BpCommand;
import com.gmail.ibmesp1.events.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Backpacks extends JavaPlugin {

    public String version;
    public String name;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        name = ChatColor.DARK_RED + "[" + pdffile.getName() + "]";

        Bukkit.getConsoleSender().sendMessage("[Backpacks] - Version: " + version + " Enabled - By Ib");
        registrerCommands();
        registerEvents();
        BpCommand.loadBackPacks();
    }

    @Override
    public void onDisable() {
        BpCommand.saveBackPacks();
    }

    public void registrerCommands() {
        getCommand("bp").setExecutor(new BpCommand(this));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(),this);
    }
}
