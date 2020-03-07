package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final Game game;

    public PlayerQuitListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        game.removePlayer(player);
        game.verifyState();
    }
}
