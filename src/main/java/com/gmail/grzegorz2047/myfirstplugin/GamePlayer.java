package com.gmail.grzegorz2047.myfirstplugin;

import java.util.UUID;

public class GamePlayer {
    private final UUID uuid;
    private final String playerName;
    private final int points;

    public GamePlayer(UUID uuid, String playerName, int points) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.points = points;
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getPoints() {
        return points;
    }
}
