package com.gmail.ibmesp1.bp;

import com.gmail.ibmesp1.bp.commands.bpcommand.BpCommand;
import com.gmail.ibmesp1.bp.commands.bpcommand.BpEvents;
import com.gmail.ibmesp1.bp.commands.bpcommand.subcommands.CreateSubCommand;
import com.gmail.ibmesp1.bp.commands.bpcommand.subcommands.OpenSubCommand;
import com.gmail.ibmesp1.bp.commands.bpmenu.BpMenu;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.BpMenuEvents;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.config.ConfigGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.config.SizeConfig;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.create.CreateGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.create.SizeGUI;
import com.gmail.ibmesp1.bp.commands.bpmenu.guis.delete.DeleteGUI;
import com.gmail.ibmesp1.bp.commands.bpsee.BpSee;
import com.gmail.ibmesp1.bp.commands.keepBackpack.keepBackpack;
import com.gmail.ibmesp1.bp.commands.keepBackpack.keepBackpackTab;
import com.gmail.ibmesp1.bp.data.DataManager;
import com.gmail.ibmesp1.bp.events.PlayerEvent;
import com.gmail.ibmesp1.bp.utils.Metrics;
import com.gmail.ibmesp1.bp.utils.UpdateChecker;
import com.gmail.ibmesp1.bp.utils.backpacks.BackpackManager;
import com.gmail.ibmesp1.bp.utils.backpacks.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
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
    public List<Player> playerList;
    public int maxBP;

    public final int configFileVersion = 1;
    public final int languageFileVersion = 3;

    @Override
    public void onEnable() {
        PluginDescriptionFile pdffile = getDescription();
        version = pdffile.getVersion();
        name = ChatColor.GOLD + "[" + pdffile.getName() + "]";
        Logger log = Bukkit.getLogger();

        playerBackpack = new HashMap<>();
        playerPage = new HashMap<>();
        bpcm = new DataManager(this,"bpconfig.yml");
        languageData = new DataManager(this,"languages/" +
                getConfig().getString("locale") + ".yml");
        backpacks = new DataManager(this,"backpacks.yml");


        new Metrics(this,14427);
        bpm = new BackpackManager(this,playerBackpack,backpacks);

        playerList = new ArrayList<>();

        Bukkit.getConsoleSender().sendMessage("[Backpacks] - Version: " + version + " Enabled - By Ib");
        registrerCommands();
        registerEvents();
        checkMaxBP();
        bpm.loadBackPacks();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        bpcm.getConfig().options().copyDefaults(true);
        bpcm.saveConfig();
        getLanguageData().options().copyDefaults(true);
        backpacks.getConfig().options().copyDefaults(true);

        new UpdateChecker(this,99840).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                log.info("[Backpacks] " + getLanguageString("config.notUpdate"));
            } else {
                log.warning("[Backpacks] " + getLanguageString("config.update"));
            }
        });

        if (getConfig().getInt("configVersion") < configFileVersion) {
            urgentConsoleWarning("Your config.yml is outdated!");
            urgentConsoleWarning("Please update to the latest version " + ChatColor.AQUA + configFileVersion + ChatColor.RED + " to ensure compatibility.");
            urgentConsoleWarning("Please only update AFTER updating all other data files.");
        }

        if (getConfig().getInt("languageFile") < languageFileVersion) {
            urgentConsoleWarning("You language files are no longer supported with this version!");
            urgentConsoleWarning("Please update en_US.yml and update any other language files to version " +
                    ChatColor.AQUA + languageFileVersion + ChatColor.RED + ".");
            urgentConsoleWarning("Please do not update your config.yml until your language files have been updated.");
        }

    }

    @Override
    public void onDisable() {
        bpm.saveBackPacks();
    }

    public void registrerCommands() {
        getCommand("bp").setExecutor(new BpCommand(this,playerBackpack,bpcm,bpm));
        getCommand("bpsee").setExecutor(new BpSee(this,playerBackpack));
        getCommand("bgamerule").setExecutor(new keepBackpack(this,bpcm));
        getCommand("bgamerule").setTabCompleter(new keepBackpackTab());
        getCommand("bpmenu").setExecutor(new BpMenu(this,bpm,playerBackpack,bpcm));
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(this,playerBackpack,bpcm,playerList,bpm),this);
        Bukkit.getPluginManager().registerEvents(new BpEvents(this,playerBackpack,bpm),this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(),this);
        Bukkit.getPluginManager().registerEvents(new OpenSubCommand(this,playerBackpack,bpm),this);
        Bukkit.getPluginManager().registerEvents(new BpMenuEvents(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new ConfigGUI(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new SizeConfig(this,playerBackpack,bpcm,playerList),this);
        Bukkit.getPluginManager().registerEvents(new SizeGUI(this,playerBackpack,bpcm),this);
        Bukkit.getPluginManager().registerEvents(new DeleteGUI(this,playerBackpack,bpcm,bpm),this);
        Bukkit.getPluginManager().registerEvents(new CreateGUI(this,playerBackpack,bpcm,bpm),this);
    }

    public FileConfiguration getLanguageData() {
        return languageData.getConfig();
    }

    public String getLanguageString(String path) {
        return languageData.getConfig().getString(path);
    }

    private void urgentConsoleWarning(String msg) {
        Bukkit.getConsoleSender().sendMessage("[Backpacks] " + ChatColor.RED + msg);
    }

    private void checkMaxBP()
    {
        maxBP = getConfig().getInt("maxBP");
        System.out.println(maxBP);
        if(maxBP > 9 || maxBP < 1){
            if(maxBP < 1){
                maxBP = 1;
                getConfig().set("maxBP",1);
                saveConfig();
            }else{
                maxBP = 9;
                getConfig().set("maxBP", 9);
                saveConfig();
            }

        }
    }
}
