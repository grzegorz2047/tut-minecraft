package com.gmail.grzegorz2047.minigameapi.team;

public enum TeamID {
    TEAM_1(1, "team1"),
    TEAM_2(2, "team2");

    private final int teamNumber;
    private final String teamRawLabel;

    TeamID(int teamNumber, String teamRawLabel) {
        this.teamNumber = teamNumber;
        this.teamRawLabel = teamRawLabel;
    }

    public int getNumber() {
        return teamNumber;
    }

    public String getTeamRawLabel() {
        return teamRawLabel;
    }
}
