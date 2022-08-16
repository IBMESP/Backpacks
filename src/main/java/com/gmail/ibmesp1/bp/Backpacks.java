package com.gmail.ibmesp1.bp;

import com.gmail.ibmesp1.bp.commands.bpcommand.BpCommand;
import com.gmail.ibmesp1.bp.commands.bpcommand.BpEvents;
import com.gmail.ibmesp1.bp.commands.bpcommand.subcommands.OpenSubCommand;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpMenu;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.BpMenuEvents;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.GUIs;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.config.ConfigGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.config.SizeConfig;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.create.CreateGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.create.SizeGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.delete.DeleteGUI;
import com.gmail.ibmesp1.bp.commands.bpsee.BpSee;
import com.gmail.ibmesp1.bp.commands.keepBackpack.keepBackpack;
import com.gmail.ibmesp1.bp.commands.keepBackpack.keepBackpackTab;
import com.gmail.ibmesp1.bp.events.PlayerEvent;
import com.gmail.ibmesp1.bp.utils.BackpackManager;
import com.gmail.ibmesp1.bp.utils.Checkers;
import com.gmail.ibmesp1.bp.utils.MenuListener;
import com.gmail.ibmesp1.ibcore.guis.MenuItems;
import com.gmail.ibmesp1.ibcore.utils.DataManager;
import com.gmail.ibmesp1.ibcore.utils.Metrics;
import com.gmail.ibmesp1.ibcore.utils.UpdateChecker;
import com.gmail.ibmesp1.ibcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class Backpacks extends JavaPlugin {

    public String version;
    public String name;
    public HashMap<UUID, HashMap<String,Inventory>> playerBackpack;
    public HashMap<UUID,Integer> playerPage;
    public BackpackManager bpm;
    public DataManager backpacks;
    public DataManager bpcm;
    public DataManager languageData;
    public MenuItems menuItems;
    public GUIs guis;
    public List<Player> playerList;
    public int maxBP;
    public int rowsBP;

    public final int configFileVersion = 2;
    public final int languageFileVersion = 4;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        Logger log = Bukkit.getLogger();

        bpcm = new DataManager(this,"bpconfig.yml");
        languageData = new DataManager(this,"languages/" +
                getConfig().getString("locale") + ".yml");
        backpacks = new DataManager(this,"backpacks.yml");
        bpcm.getConfig().options().copyDefaults(true);
        bpcm.saveConfig();
        languageData.getConfig().options().copyDefaults(true);
        backpacks.getConfig().options().copyDefaults(true);
        backpacks.saveConfig();

        new Metrics(this,14427);

        menuItems = new MenuItems();

        playerList = new ArrayList<>();
        playerBackpack = new HashMap<>();
        playerPage = new HashMap<>();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        name = ChatColor.translateAlternateColorCodes('&',getConfig().getString("name"));

        bpm = new BackpackManager(this,playerBackpack,backpacks);
        bpm.loadBackPacks();

        guis = new GUIs(this,playerBackpack,bpcm);

        registrerCommands();
        registerEvents();

        Checkers checkers = new Checkers(this,bpcm,maxBP,rowsBP);
        checkers.checkMaxBP();
        checkers.checkSize();
        rowsBP = checkers.getRowsBP();

        Bukkit.getConsoleSender().sendMessage("[Backpacks] - Version: " + version + " Enabled - By Ib");

        new UpdateChecker(this,99840).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                log.info("[Backpacks] " + getLanguageString("config.notUpdate"));
            } else {
                log.warning("[Backpacks] " + getLanguageString("config.update"));
            }
        });

        if (getConfig().getInt("configVersion") < configFileVersion) {
            Utils.urgentConsoleWarning(name,"Your config.yml is outdated!");
            Utils.urgentConsoleWarning(name,"Please update to the latest version " + ChatColor.AQUA + configFileVersion + ChatColor.RED + " to ensure compatibility.");
            Utils.urgentConsoleWarning(name,"Please only update AFTER updating all other data files.");
        }

        if (getConfig().getInt("languageFile") < languageFileVersion) {
            Utils.urgentConsoleWarning(name,"You language files are no longer supported with this version!");
            Utils.urgentConsoleWarning(name,"Please update en_US.yml and update any other language files to version " +
                    ChatColor.AQUA + languageFileVersion + ChatColor.RED + ".");
            Utils.urgentConsoleWarning(name,"Please do not update your config.yml until your language files have been updated.");
        }

    }

    @Override
    public void onDisable() {
        bpm.saveBackPacks();
    }

    public void registrerCommands() {
        getCommand("bp").setExecutor(new BpCommand(this,playerBackpack,bpcm,guis,languageData, bpm));
        getCommand("bpsee").setExecutor(new BpSee(this,playerBackpack, menuItems));
        getCommand("bgamerule").setExecutor(new keepBackpack(this,bpcm));
        getCommand("bgamerule").setTabCompleter(new keepBackpackTab());
        getCommand("bpmenu").setExecutor(new BpMenu(this,guis));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(this,playerBackpack,bpcm,playerList,bpm),this);
        Bukkit.getPluginManager().registerEvents(new BpEvents(this,playerBackpack,bpm),this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(),this);
        Bukkit.getPluginManager().registerEvents(new OpenSubCommand(this,playerBackpack),this);
        Bukkit.getPluginManager().registerEvents(new BpMenuEvents(this,guis),this);
        Bukkit.getPluginManager().registerEvents(new ConfigGUI(this,bpcm,guis),this);
        Bukkit.getPluginManager().registerEvents(new SizeConfig(this,bpcm,guis),this);
        Bukkit.getPluginManager().registerEvents(new SizeGUI(this,guis),this);
        Bukkit.getPluginManager().registerEvents(new DeleteGUI(this,playerBackpack, menuItems,guis),this);
        Bukkit.getPluginManager().registerEvents(new CreateGUI(this,playerBackpack,bpcm,bpm,guis),this);
    }

    public String getLanguageString(String path) {
        return languageData.getConfig().getString(path);
    }
}
