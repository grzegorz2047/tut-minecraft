package com.gmail.grzegorz2047.minigameapi.team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

    public void teleportMembersToTeamSpawn() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isMember(player.getName())) {
                teleportMemberToTeamSpawn(player);
            }
        }
    }

    public boolean isMember(String playerName) {
        return members.contains(playerName);
    }

    public void teleportMemberToTeamSpawn(Player player) {
        player.teleport(spawnTeam);
    }
}
