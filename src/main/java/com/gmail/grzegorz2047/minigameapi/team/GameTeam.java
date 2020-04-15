package com.gmail.grzegorz2047.minigameapi.team;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {

    private final TeamID teamId;
    private final Location spawnTeam;
    private List<String> members = new ArrayList<>();

    public GameTeam(TeamID teamId, Location spawnTeam) {
        this.teamId = teamId;
        this.spawnTeam = spawnTeam;
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

    public Location getSpawnTeam() {
        return spawnTeam;
    }
}
