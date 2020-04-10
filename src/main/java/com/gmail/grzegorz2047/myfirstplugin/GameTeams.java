package com.gmail.grzegorz2047.myfirstplugin;

import java.util.ArrayList;
import java.util.List;

public class GameTeams {
    private List<String> team1 = new ArrayList<>();
    private List<String> team2 = new ArrayList<>();

    public int assignPlayerToATeam(String playerName) {
        int team1Size = team1.size();
        int team2Size = team2.size();
        if (team1Size < team2Size) {
            team1.add(playerName);
            return 1;
        } else {
            team2.add(playerName);
            return 2;
        }
    }

    public void removePlayerFromATeam(String playerName) {
        team1.remove(playerName);
        team2.remove(playerName);
    }

    public boolean isOneTeamLeft() {
        return team1.size() == 0 || team2.size() == 0;
    }

    public List<String> getTeamMembers(String teamId) {
        if (teamId.equals(TeamID.TEAM_1.name())) {
            return team1;
        } else {
            return team2;
        }
    }

    public int getTeam1Size() {
        return team1.size();
    }

    public int getTeam2Size() {
        return team2.size();
    }
}
