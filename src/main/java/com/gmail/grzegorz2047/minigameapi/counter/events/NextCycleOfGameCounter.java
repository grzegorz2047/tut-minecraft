package com.gmail.grzegorz2047.minigameapi.counter.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NextCycleOfGameCounter extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final int currentTime;

    public NextCycleOfGameCounter(int currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
