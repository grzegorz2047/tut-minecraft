package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.myfirstplugin.teams.GameTeams;
import com.gmail.grzegorz2047.myfirstplugin.teams.TeamID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class GameScoreboard {

    private final String timeLabel = ChatColor.GOLD + "Czas " + ChatColor.GRAY;
    private final String team1Label = ChatColor.RED + "Czerwoni " + ChatColor.GRAY;
    private String team2Label = ChatColor.BLUE + "Niebiescy " + ChatColor.GRAY;

    private void createCurrentTimeScore(int currentTime, Objective minigameObjective, Scoreboard newScoreboard) {
        Team team = newScoreboard.registerNewTeam(timeLabel);
        team.addEntry(timeLabel);
        team.setSuffix(String.valueOf(currentTime));
        Score lineNumber = minigameObjective.getScore(timeLabel);
        lineNumber.setScore(0);
    }

    private void updateCurrentTimeScore(int currentTime, Scoreboard newScoreboard) {
        Team team = newScoreboard.getTeam(timeLabel);
        team.setSuffix(String.valueOf(currentTime));
    }

    private void updateTeamScore(Scoreboard scoreboard, String teamLabel, int teamSize) {
        Team team = scoreboard.getTeam(teamLabel);
        team.setSuffix(String.valueOf(teamSize));

    }

    private void createTeamScore(Objective objective, Scoreboard scoreboard, String teamLabel, int teamSize, int lineHeight) {
        Team label = scoreboard.registerNewTeam(teamLabel);
        label.addEntry(teamLabel);
        objective.getScore(teamLabel).setScore(lineHeight);
        label.setSuffix(String.valueOf(teamSize));
    }


    private void setCurrentTeamsScore(GameTeams gameTeams, Scoreboard scoreboard) {
        updateTeamScore(scoreboard, team1Label, gameTeams.getMembersNumber(TeamID.TEAM_1));
        updateTeamScore(scoreboard, team2Label, gameTeams.getMembersNumber(TeamID.TEAM_2));
    }

    private void createTeamsScore(GameTeams gameTeams, Objective objective, Scoreboard scoreboard) {
        createTeamScore(objective, scoreboard, team1Label, gameTeams.getMembersNumber(TeamID.TEAM_1), 2);
        createTeamScore(objective, scoreboard, team2Label, gameTeams.getMembersNumber(TeamID.TEAM_2), 1);
    }

    public void create(Game game, Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Minigame", "dummy", "Tablica minigry");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        createTeamsScore(game.getTeams(), objective, scoreboard);
        createCurrentTimeScore(
                0, objective, scoreboard);
        updateCurrentTimeScore(0, scoreboard);
        setCurrentTeamsScore(game.getTeams(), scoreboard);
        createGameTeam(scoreboard, ChatColor.RED, TeamID.TEAM_1.name());
        createGameTeam(scoreboard, ChatColor.BLUE, TeamID.TEAM_2.name());
        player.setScoreboard(scoreboard);
    }

    private void createGameTeam(Scoreboard scoreboard, ChatColor color, String teamLabel) {
        Team scoreboardTeam = scoreboard.registerNewTeam(teamLabel);
//        scoreboardTeam.setDisplayName("");
        scoreboardTeam.setColor(color);
        scoreboardTeam.setPrefix("§c§l❤ ");
    }

    public void refresh(Game game, int currentTime) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = player.getScoreboard();
            Objective minigameObjective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
            if (minigameObjective != null) {
                updateCurrentTimeScore(currentTime, scoreboard);
                setCurrentTeamsScore(game.getTeams(), scoreboard);
            }
        }
    }


    public void colorPlayersNicknameAboveHead(GameTeams teams, Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        filloutTeamWithNicks(teams.getTeamMembers(TeamID.TEAM_1), scoreboard, TeamID.TEAM_1.name());
        filloutTeamWithNicks(teams.getTeamMembers(TeamID.TEAM_2), scoreboard, TeamID.TEAM_2.name());
    }

    private void filloutTeamWithNicks(List<String> playersOfTeam1, Scoreboard scoreboard, String teamId) {
        Team teamData = scoreboard.getTeam(teamId);
        for (String playerNick : playersOfTeam1) {
            teamData.addEntry(playerNick);
            System.out.println("teamId " + teamId + ", dodaje nick " + playerNick);
        }
    }
}
