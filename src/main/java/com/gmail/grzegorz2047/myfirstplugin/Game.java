package com.gmail.grzegorz2047.myfirstplugin;


import com.gmail.grzegorz2047.minigameapi.counter.CounterType;
import com.gmail.grzegorz2047.minigameapi.counter.GameCounter;
import com.gmail.grzegorz2047.minigameapi.team.GameTeams;
import com.gmail.grzegorz2047.myfirstplugin.database.DatabaseQueries;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class Game {
    private final GameConfiguration gameConfiguration;
    private final Plugin pluginReference;
    private final DatabaseQueries queries;
    private GameCounter gameCounter;
    private GameState state = GameState.WAITING;
    private GameTeams teams;

    private GameScoreboard gameScoreboard = new GameScoreboard();

    public Game(Plugin pluginReference, GameConfiguration gameConfiguration, DatabaseQueries queries) {
        this.gameCounter = new GameCounter(pluginReference);
        this.gameConfiguration = gameConfiguration;
        this.teams = new GameTeams(gameConfiguration);
        this.pluginReference = pluginReference;
        this.queries = queries;
    }


    public void addPlayer(Player player) {
        Optional<GamePlayer> returnedPlayerData;
        String kickMsg = "Jest problem z serwerem! Sprobuj ponownie pozniej!";
        try {
            String uuid = player.getUniqueId().toString();
            queries.insertPlayer(uuid, player.getName(), 0);
            returnedPlayerData = queries.getPlayer(uuid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            player.kickPlayer(kickMsg);
            return;
        }
        if (!returnedPlayerData.isPresent()) {
            System.out.println("Nie ma zadnej wartosci w optionalu!");
            player.kickPlayer(kickMsg);
            return;
        }
        GamePlayer gamePlayer = returnedPlayerData.get();
        player.sendMessage(ChatColor.GRAY + "Twoje statystyki:\npunkty:" + ChatColor.GOLD + gamePlayer.getPoints());
        player.setGameMode(GameMode.SURVIVAL);
        teleportPlayerToALobby(player);
        gameScoreboard.create(this, player, gamePlayer);
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

    public void teleportPlayerToALobby(Player player) {
        Location spawnLobby = gameConfiguration.getSpawnLobby();
        player.teleport(spawnLobby);
    }

    public void teleportPlayerToALobbyWithDelay(Player player) {
        Location spawnLobby = gameConfiguration.getSpawnLobby();
        Bukkit.getScheduler().runTaskLater(pluginReference, () -> player.teleport(spawnLobby), 2L);
    }


    public void removePlayer(Player player) {
        String playerName = player.getName();
        teams.removePlayerFromATeam(playerName);

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
            System.out.println("Stan wystartowany");
            if (teams.isOneTeamLeft()) {
                System.out.println("Zostala ostatnia druzyna");
                stopCounting();
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
        Bukkit.broadcastMessage(ChatColor.GOLD + "Ostatnia druzyna wygrala!");
        state = GameState.WAITING;
        for (Player player : Bukkit.getOnlinePlayers()) {
            removePlayer(player);
            addPlayer(player);
        }
        verifyState();
        //Informuj kto wygral
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
        teams.teleportPlayersToTheirTeamSpawns();
        String message = "Gra wystartowala!";
        Bukkit.broadcastMessage(message);
        this.state = GameState.WARMUP;
        //teleport players to their team spawn
        //give em some items
        //give them some special effects
        World world = Bukkit.getWorlds().get(0);
        world.setTime(0);
        world.setStorm(false);
        world.setThundering(false);
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
        System.out.println("Daje efekty graczom");
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
            }
        }
    }


    public GameTeams getTeams() {
        return teams;
    }

    public boolean isOneTeamLeft() {
        return teams.isOneTeamLeft();
    }
}
