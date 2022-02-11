package com.gmail.ibmesp1.events;

import com.gmail.ibmesp1.commands.bpcommand.BpCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvent implements Listener {
    
    @EventHandler
    public void PlayerJoins(PlayerJoinEvent e)
    {
        BpCommand.loadPlayerBackPacks(e.getPlayer());
    }

    @EventHandler
    public void PlayerQuits(PlayerQuitEvent e)
    {
        BpCommand.savePlayerBackPacks(e.getPlayer());
    }
}
