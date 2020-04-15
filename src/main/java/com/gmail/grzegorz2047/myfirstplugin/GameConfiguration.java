package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.configuration.file.FileConfiguration;

public class GameConfiguration {
    private int DEATHMATCH_TIME;
    private int AFTERMATCH_TIME;
    private int MIN_PLAYERS_TO_KEEP_COUNTING;
    private int NUMBER_OF_PLAYERS_TO_START_COUNTING;
    private int TIME_TO_START_A_GAME;
    private int WARMUP_TIME;


    public GameConfiguration(FileConfiguration config) {
        TIME_TO_START_A_GAME = config.getInt("czasDoOdliczeniaStartuGry");
        NUMBER_OF_PLAYERS_TO_START_COUNTING = config.getInt("minimalnaLiczbaGraczyDoStartuGry");
        MIN_PLAYERS_TO_KEEP_COUNTING = config.getInt("minimalnaLiczbaGraczyDoUtrzymaniaOdliczaniaStartuGry");
        System.out.println(MIN_PLAYERS_TO_KEEP_COUNTING);
        WARMUP_TIME = config.getInt("dlugoscTrwaniaWarmupu");
        DEATHMATCH_TIME = config.getInt("dlugoscTrwaniaDeathMatchu");
        AFTERMATCH_TIME = config.getInt("dlugoscTrwaniaAfterMatchu");
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
}
