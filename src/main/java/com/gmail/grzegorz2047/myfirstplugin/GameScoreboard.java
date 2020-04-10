package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class GameScoreboard {

    private final String timeLabel = ChatColor.GOLD + "Czas " + ChatColor.GRAY;
    private final String team1Label = ChatColor.RED + "Czerwoni " + ChatColor.GRAY;
    private String team2Label = ChatColor.BLUE + "Niebiescy " + ChatColor.GRAY;
    private String team1ScoreboardId = "team1";
    private String team2ScoreboardId = "team2";

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


    private void setCurrentTeamsScore(Game game, Scoreboard scoreboard) {
        updateTeamScore(scoreboard, team1Label, game.getTeam1Size());
        updateTeamScore(scoreboard, team2Label, game.getTeam2Size());
    }

    private void createTeamsScore(Game game, Objective objective, Scoreboard scoreboard) {
        createTeamScore(objective, scoreboard, team1Label, game.getTeam1Size(), 2);
        createTeamScore(objective, scoreboard, team2Label, game.getTeam2Size(), 1);
    }

    public void create(Game game, Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Minigame", "dummy", "Tablica minigry");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        createTeamsScore(game, objective, scoreboard);
        createCurrentTimeScore(
                0, objective, scoreboard);
        updateCurrentTimeScore(0, scoreboard);
        setCurrentTeamsScore(game, scoreboard);
        createGameTeam(scoreboard, ChatColor.RED, team1ScoreboardId);
        createGameTeam(scoreboard, ChatColor.BLUE, team2ScoreboardId);
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
                setCurrentTeamsScore(game, scoreboard);
            }
        }
    }


    public void colorPlayersNicknameAboveHead(List<String> playersOfTeam1, List<String> playersOfTeam2, Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        filloutTeamWithNicks(playersOfTeam1, scoreboard, team1ScoreboardId);
        filloutTeamWithNicks(playersOfTeam2, scoreboard, team2ScoreboardId);
    }

    private void filloutTeamWithNicks(List<String> playersOfTeam1, Scoreboard scoreboard, String teamId) {
        Team teamData = scoreboard.getTeam(teamId);
        for (String playerNick : playersOfTeam1) {
            teamData.addEntry(playerNick);
            System.out.println("teamId " + teamId + ", dodaje nick " + playerNick);
        }
    }
}
