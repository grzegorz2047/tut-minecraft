package com.gmail.grzegorz2047.myfirstplugin.teams;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {

    private final TeamID teamId;
    private List<String> members = new ArrayList<>();

    public GameTeam(TeamID teamId) {
        this.teamId = teamId;
    }

    public void removeTeamMember(String playerName) {
        members.remove(playerName);
    }

    public int getSize() {
        return members.size();
    }

    public List<String> getMembers() {
        return members;
    }

    public TeamID getTeamID() {
        return teamId;
    }

    public void addMember(String playerName) {
        members.add(playerName);
    }
}
