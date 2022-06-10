package com.gmail.ibmesp1;

import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import com.gmail.ibmesp1.commands.bpmenu.BpMenu;
import com.gmail.ibmesp1.commands.bpmenu.guis.*;
import com.gmail.ibmesp1.commands.bpmenu.guis.config.ConfigGUI;
import com.gmail.ibmesp1.commands.bpmenu.guis.config.SizeConfig;
import com.gmail.ibmesp1.commands.bpmenu.guis.create.CreateGUI;
import com.gmail.ibmesp1.commands.bpmenu.guis.create.SizeGUI;
import com.gmail.ibmesp1.commands.bpmenu.guis.ChatEvent;
import com.gmail.ibmesp1.commands.bpmenu.guis.delete.DeleteGUI;
import com.gmail.ibmesp1.commands.bpsee.BpSee;
import com.gmail.ibmesp1.commands.keepBackpack.keepBackpack;
import com.gmail.ibmesp1.commands.keepBackpack.keepBackpackTab;
import com.gmail.ibmesp1.data.DataManger;
import com.gmail.ibmesp1.events.PlayerEvent;
import com.gmail.ibmesp1.utils.Metrics;
import com.gmail.ibmesp1.utils.UpdateChecker;
import com.gmail.ibmesp1.utils.backpacks.BackpackManager;
import com.gmail.ibmesp1.utils.backpacks.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
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
    public HashMap<UUID,String> customName;
    public BackpackManager bpm;
    public DataManger bpcm;
    public DataManger languageData;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        name = ChatColor.DARK_RED + "[" + pdffile.getName() + "]";
        Logger log = Bukkit.getLogger();

        playerBackpack = new HashMap<>();
        customName = new HashMap<>();
        bpcm = new DataManger(this,"bpconfig.yml");
        languageData = new DataManger(this,"languages/" +
                getConfig().getString("locale") + ".yml");


        new Metrics(this,14427);
        bpm = new BackpackManager(this,playerBackpack);

        Bukkit.getConsoleSender().sendMessage("[Backpacks] - Version: " + version + " Enabled - By Ib");
        registrerCommands();
        registerEvents();
        BackpackManager.loadBackPacks();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        bpcm.getConfig().options().copyDefaults(true);
        bpcm.saveConfig();
        getLanguageData().options().copyDefaults(true);


        new UpdateChecker(this,99840).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                log.info("[Backpacks] " + getLanguageString("config.notUpdate") );
            } else {
                log.warning("[Backpacks] " + getLanguageString("config.update"));
            }
        });
    }

    @Override
    public void onDisable() {
        BackpackManager.saveBackPacks();
    }

    public void registrerCommands() {
        getCommand("bp").setExecutor(new BpCommand(this,playerBackpack,bpcm,bpm));
        getCommand("bpsee").setExecutor(new BpSee(this,playerBackpack,bpm));
        getCommand("bgamerule").setExecutor(new keepBackpack(this,bpcm));
        getCommand("bgamerule").setTabCompleter(new keepBackpackTab());
        getCommand("bpmenu").setExecutor(new BpMenu(this,bpm,playerBackpack,bpcm));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(),this);
        Bukkit.getPluginManager().registerEvents(new BpMenuEvents(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new ConfigGUI(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new SizeConfig(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new SizeGUI(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new DeleteGUI(this,playerBackpack,customName,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new CreateGUI(this,playerBackpack,customName,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(this,playerBackpack,customName,bpcm),this);
    }

    public FileConfiguration getLanguageData() {
        return languageData.getConfig();
    }

    public String getLanguageString(String path) {
        return languageData.getConfig().getString(path);
    }

}
