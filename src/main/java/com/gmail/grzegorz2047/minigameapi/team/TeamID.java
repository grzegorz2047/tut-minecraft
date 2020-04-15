package com.gmail.grzegorz2047.minigameapi.team;

public enum TeamID {
    TEAM_1(1),
    TEAM_2(2);

    private final int teamNumber;

    TeamID(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getNumber() {
        return teamNumber;
    }
}
