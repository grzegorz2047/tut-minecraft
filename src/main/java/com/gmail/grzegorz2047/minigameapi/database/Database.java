package com.gmail.grzegorz2047.minigameapi.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {


    void connect();
    void disconnect();

    Connection getConnection() throws SQLException;

}
