package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final Game game;

    public PlayerDeathListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        
    }
}
