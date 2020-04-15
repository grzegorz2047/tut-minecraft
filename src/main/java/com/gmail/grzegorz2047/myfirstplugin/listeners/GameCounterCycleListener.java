package com.gmail.grzegorz2047.myfirstplugin.listeners;

import com.gmail.grzegorz2047.myfirstplugin.Game;
import com.gmail.grzegorz2047.minigameapi.counter.events.NextCycleOfGameCounter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCounterCycleListener implements Listener {
    private final Game game;

    public GameCounterCycleListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onCount(NextCycleOfGameCounter cycle) {
        game.verifyState();
        if (this.game.isStarting()) {
            System.out.println("Pozostaly czas do startu " + cycle.getCurrentTime());
            this.game.updateScoreboard(cycle.getCurrentTime());
        }
        else if(this.game.isinWarmup()) {
            this.game.updateScoreboard(cycle.getCurrentTime());
        }
        else if(this.game.isInDeathMatch()) {
            this.game.updateScoreboard(cycle.getCurrentTime());
        }
    }
}
