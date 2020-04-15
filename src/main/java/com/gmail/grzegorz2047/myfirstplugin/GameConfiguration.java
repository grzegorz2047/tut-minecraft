package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.minigameapi.LocationParser;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class GameConfiguration {
    private final LocationParser locationParser = new LocationParser();
    private int DEATHMATCH_TIME;
    private int AFTERMATCH_TIME;
    private int MIN_PLAYERS_TO_KEEP_COUNTING;
    private int NUMBER_OF_PLAYERS_TO_START_COUNTING;
    private int TIME_TO_START_A_GAME;
    private int WARMUP_TIME;
    private Location spawnTeam1;
    private Location spawnTeam2;

    public GameConfiguration(FileConfiguration config) {
        this.TIME_TO_START_A_GAME = config.getInt("czasDoOdliczeniaStartuGry");
        this.NUMBER_OF_PLAYERS_TO_START_COUNTING = config.getInt("minimalnaLiczbaGraczyDoStartuGry");
        this.MIN_PLAYERS_TO_KEEP_COUNTING = config.getInt("minimalnaLiczbaGraczyDoUtrzymaniaOdliczaniaStartuGry");
        this.WARMUP_TIME = config.getInt("dlugoscTrwaniaWarmupu");
        this.DEATHMATCH_TIME = config.getInt("dlugoscTrwaniaDeathMatchu");
        this.AFTERMATCH_TIME = config.getInt("dlugoscTrwaniaAfterMatchu");
        this.spawnTeam1 = locationParser.parseLocation(config.getString("druzyny.druzyna1.spawn"));
        this.spawnTeam2 = locationParser.parseLocation(config.getString("druzyny.druzyna2.spawn"));
    }

    public int getDEATHMATCH_TIME() {
        return DEATHMATCH_TIME;
    }

    public int getAFTERMATCH_TIME() {
        return AFTERMATCH_TIME;
    }

    public int getMIN_PLAYERS_TO_KEEP_COUNTING() {
        return MIN_PLAYERS_TO_KEEP_COUNTING;
    }

    public int getNUMBER_OF_PLAYERS_TO_START_COUNTING() {
        return NUMBER_OF_PLAYERS_TO_START_COUNTING;
    }

    public int getTIME_TO_START_A_GAME() {
        return TIME_TO_START_A_GAME;
    }

    public int getWARMUP_TIME() {
        return WARMUP_TIME;
    }

    public Location getSpawnTeam1() {
        return null;
    }

    public Location getSpawnTeam2() {
        return null;
    }
}
