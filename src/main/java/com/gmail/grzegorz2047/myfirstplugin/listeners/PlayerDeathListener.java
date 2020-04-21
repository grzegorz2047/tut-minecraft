package com.gmail.grzegorz2047.myfirstplugin.listeners;

import com.gmail.grzegorz2047.myfirstplugin.Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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
        Player player = e.getEntity();
        game.removePlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
        game.teleportPlayerToALobbyWithDelay(player);
        game.verifyState();

    }
}
