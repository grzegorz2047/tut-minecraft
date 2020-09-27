package com.gmail.grzegorz2047.myfirstplugin.database;

import com.gmail.grzegorz2047.myfirstplugin.GamePlayer;

import java.sql.SQLException;
import java.util.Optional;

public interface DatabaseQueries {
    void insertPlayer(String uuid, String userName, int points) throws SQLException;

    Optional<GamePlayer> getPlayer(String userName);
}
