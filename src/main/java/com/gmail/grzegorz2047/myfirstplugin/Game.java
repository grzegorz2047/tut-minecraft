package com.gmail.grzegorz2047.myfirstplugin;


import com.gmail.grzegorz2047.minigameapi.counter.CounterType;
import com.gmail.grzegorz2047.minigameapi.counter.GameCounter;
import com.gmail.grzegorz2047.minigameapi.team.GameTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class Game {
    private final GameConfiguration gameConfiguration;
    private GameCounter gameCounter;
    private GameState state = GameState.WAITING;
    private GameTeams teams = new GameTeams();

    private GameScoreboard gameScoreboard = new GameScoreboard();

    public Game(Plugin pluginReference, GameConfiguration gameConfiguration) {
        this.gameCounter = new GameCounter(pluginReference);
        this.gameConfiguration = gameConfiguration;
    }


    public void addPlayer(Player player) {
        gameScoreboard.create(this, player);
        int teamNumber = teams.assignPlayerToATeam(player.getName());
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setContents(new ItemStack[4]);
        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        for (PotionEffect effect : activePotionEffects) {
            player.removePotionEffect(effect.getType());
        }
        player.setFoodLevel(20);
        player.setExp(0);
        player.setTotalExperience(0);
        player.setHealth(player.getHealthScale());
        player.sendMessage(ChatColor.GREEN + "Dołączyłeś do druzyny " + teamNumber);
    }


    public void removePlayer(Player player) {
        String playerName = player.getName();
        teams.removePlayerFromATeam(playerName);
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "Gracz " + player.getName() + " opuscil serwer!");
    }

    private void stopCounting() {
        state = GameState.WAITING;
        gameCounter.cancelCounting();
    }

    private void startCounting() {
        state = GameState.STARTING;
        gameCounter.startCounting(gameConfiguration.getTIME_TO_START_A_GAME(), CounterType.COUNTING_TO_START);
    }

    public boolean isStarted() {
        return state == GameState.WARMUP || state == GameState.DEATHMATCH;
    }


    public void verifyState() {
        int numberOfPlayerOnServer = Bukkit.getOnlinePlayers().size();
        System.out.println("Stan " + state.name());
        if (isStarted()) {
            if (teams.isOneTeamLeft()) {
                end();
            }
        } else if (state == GameState.STARTING) {
            if (numberOfPlayerOnServer < gameConfiguration.getMIN_PLAYERS_TO_KEEP_COUNTING()) {
                stopCounting();
                System.out.println("zatrzymuje");
            }
        } else {
            if (numberOfPlayerOnServer == gameConfiguration.getNUMBER_OF_PLAYERS_TO_START_COUNTING()) {
                startCounting();
            }
        }
    }


    private void end() {
        //Informuj kto wygral
        //teleportuj gracza na spawn
        //wyczysc gracza
    }


    public boolean isStarting() {
        return state.equals(GameState.STARTING);
    }

    public boolean isinWarmup() {
        return this.state.equals(GameState.WARMUP);
    }

    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            gameScoreboard.colorPlayersNicknameAboveHead(teams, player);
        }
        String message = "Gra wystartowala!";
        System.out.println(message);
        Bukkit.broadcastMessage(message);
        this.state = GameState.WARMUP;
        //teleport players to their team spawn
        //give em some items
        //give them some special effects
        this.gameCounter.startCounting(gameConfiguration.getWARMUP_TIME(), CounterType.COUNTING_TO_DEATHMATCH);
    }

    public void updateScoreboard(int currentTime) {
        this.gameScoreboard.refresh(this, currentTime);
        //updates time in players scoreboard
    }


    public boolean isInDeathMatch() {
        return this.state.equals(GameState.DEATHMATCH);
    }

    public void startDeathmatch() {
        //teleport players to deathmatch team spawn;
        this.state = GameState.DEATHMATCH;
        this.gameCounter.startCounting(gameConfiguration.getDEATHMATCH_TIME(), CounterType.COUNTING_TO_AFTERMATCH);
    }

    public void startAfterMatch() {
        this.gameCounter.startCounting(gameConfiguration.getAFTERMATCH_TIME(), CounterType.COUNTING_TO_END);
        //give players damage effect indefinitely
        //count 2 minutes and end game
    }


    public GameTeams getTeams() {
        return teams;
    }

    public boolean isOneTeamLeft() {
        return teams.isOneTeamLeft();
    }
}
