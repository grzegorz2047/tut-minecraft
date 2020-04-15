package com.gmail.grzegorz2047.minigameapi.team;

import com.gmail.grzegorz2047.myfirstplugin.GameConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameTeams {
    Map<TeamID, GameTeam> teams = new HashMap<>();

    public GameTeams(GameConfiguration gameConfiguration) {
        teams.put(TeamID.TEAM_1, new GameTeam(TeamID.TEAM_1, gameConfiguration.getSpawnTeam1()));
        teams.put(TeamID.TEAM_2, new GameTeam(TeamID.TEAM_2, gameConfiguration.getSpawnTeam2()));
    }

    public int assignPlayerToATeam(String playerName) {
        TeamID lowestTeamId = null;
        int lowestMembersCount = Integer.MAX_VALUE;
        for (GameTeam gameTeam : teams.values()) {
            if(lowestTeamId == null) {
                lowestMembersCount = gameTeam.getSize();
                lowestTeamId = gameTeam.getTeamID();
            } else {
                if(gameTeam.getSize() < lowestMembersCount) {
                    lowestMembersCount = gameTeam.getSize();
                    lowestTeamId = gameTeam.getTeamID();
                }
            }
        }
        GameTeam gameTeam = teams.get(lowestTeamId);
        gameTeam.addMember(playerName);
        return lowestTeamId.getNumber();
    }

    public void removePlayerFromATeam(String playerName) {
        for (GameTeam gameTeam : teams.values()) {
            gameTeam.removeTeamMember(playerName);
        }

    }

    public boolean isOneTeamLeft() {
        boolean foundTeam = false;
        for (GameTeam gameTeam : teams.values()) {
            if (gameTeam.getSize() > 0) {
                if (foundTeam) {
                    return false;
                } else {
                    foundTeam = true;
                }
            }
        }
        return true;
    }

    public List<String> getTeamMembers(TeamID teamId) {
        return teams.get(teamId).getMembers();
    }


    public int getMembersNumber(TeamID teamID) {
        GameTeam gameTeam = teams.get(teamID);
        return gameTeam.getSize();
    }
}
