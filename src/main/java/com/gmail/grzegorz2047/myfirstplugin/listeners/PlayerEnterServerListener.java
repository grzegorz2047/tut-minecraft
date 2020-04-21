package com.gmail.grzegorz2047.myfirstplugin.listeners;

import com.gmail.grzegorz2047.myfirstplugin.Game;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerEnterServerListener implements Listener {
    private final Game game;

    public PlayerEnterServerListener(Game game) {
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

    @EventHandler
    private void onLogin(PlayerLoginEvent e) {
        game.teleportPlayerToALobby(e.getPlayer());
    }
}
