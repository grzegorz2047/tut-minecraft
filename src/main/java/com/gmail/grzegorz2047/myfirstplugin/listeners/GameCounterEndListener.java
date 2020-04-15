package com.gmail.grzegorz2047.myfirstplugin.listeners;

import com.gmail.grzegorz2047.minigameapi.counter.CounterType;
import com.gmail.grzegorz2047.myfirstplugin.Game;
import com.gmail.grzegorz2047.minigameapi.counter.events.EndCountingOfGameCounter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameCounterEndListener implements Listener {
    private final Game game;

    public GameCounterEndListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onEndCounting(EndCountingOfGameCounter event) {
        CounterType counterType = event.getCounterType();
        if(counterType.equals(CounterType.COUNTING_TO_START)) {
            if(this.game.isStarting()) {
                game.start();
            }
        }
        else if (counterType.equals(CounterType.COUNTING_TO_DEATHMATCH)) {
            if(this.game.isinWarmup()) {
                this.game.startDeathmatch();
            }
        }
        else if(counterType.equals(CounterType.COUNTING_TO_END)) {
            if(!this.game.isOneTeamLeft()) {
                this.game.startAfterMatch();
            }
        }

    }
}
