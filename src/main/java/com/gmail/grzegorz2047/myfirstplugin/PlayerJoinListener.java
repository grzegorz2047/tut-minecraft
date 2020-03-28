package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Game game;

    public PlayerJoinListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (game.isStarted()) {
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            game.addPlayer(player);
            game.verifyState();
        }
    }
}
