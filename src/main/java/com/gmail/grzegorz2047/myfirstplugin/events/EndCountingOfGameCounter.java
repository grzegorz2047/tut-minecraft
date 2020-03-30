package com.gmail.grzegorz2047.myfirstplugin.events;

import com.gmail.grzegorz2047.myfirstplugin.CounterType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EndCountingOfGameCounter extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final CounterType counterType;

    public EndCountingOfGameCounter(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public CounterType getCounterType() {
        return counterType;
    }
}
