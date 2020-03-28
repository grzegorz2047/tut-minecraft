package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.myfirstplugin.events.EndCountingOfGameCounter;
import com.gmail.grzegorz2047.myfirstplugin.events.NextCycleOfGameCounter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class GameCounter implements Runnable {
    private int time;
    private BukkitTask bukkitTask;
    private boolean isCounting = false;

    public void startCounting(int seconds) {
        cancelCounting();
        this.time = seconds;
        bukkitTask = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("MyFirstPlugin"), this, 20L, 0L);
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
        EndCountingOfGameCounter endCountingOfGameCounter = new EndCountingOfGameCounter();
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
