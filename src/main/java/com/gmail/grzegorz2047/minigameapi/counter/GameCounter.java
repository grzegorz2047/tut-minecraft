package com.gmail.grzegorz2047.minigameapi.counter;

import com.gmail.grzegorz2047.minigameapi.counter.events.EndCountingOfGameCounter;
import com.gmail.grzegorz2047.minigameapi.counter.events.NextCycleOfGameCounter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class GameCounter implements Runnable {
    private final Plugin pluginData;
    private CounterType counterType;
    private int time;
    private BukkitTask bukkitTask;
    private boolean isCounting = false;

    public GameCounter(Plugin pluginReference) {
        pluginData = pluginReference;
    }

    public void startCounting(int seconds, CounterType counterType) {
        cancelCounting();
        this.time = seconds;
        this.counterType = counterType;
        bukkitTask = Bukkit.getScheduler().runTaskTimer(pluginData, this, 0L, 20L);
        this.isCounting = true;
        //co sekunde daj zdarzenie odliczam
        //gdy dojdzie do zera, daj zdarzenie skonczylem odliczac
    }

    public void cancelCounting() {
        if (isCounting) {
            bukkitTask.cancel();
            isCounting = false;
        }
    }

    @Override
    public void run() {
        if (shouldCounting()) {
            count();
        } else {
            //call event
            endCounting();
        }

    }

    private void endCounting() {
        cancelCounting();
        EndCountingOfGameCounter endCountingOfGameCounter = new EndCountingOfGameCounter(counterType);
        Bukkit.getPluginManager().callEvent(endCountingOfGameCounter);
    }

    private boolean shouldCounting() {
        return this.time > 0;
    }

    private void count() {
        //call event
        this.time--;
        NextCycleOfGameCounter cycleOfGameCounter = new NextCycleOfGameCounter(this.time);
        Bukkit.getPluginManager().callEvent(cycleOfGameCounter);
    }
}
