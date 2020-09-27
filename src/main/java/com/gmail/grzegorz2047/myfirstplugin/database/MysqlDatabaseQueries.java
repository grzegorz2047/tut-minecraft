package com.gmail.grzegorz2047.myfirstplugin.database;

import com.gmail.grzegorz2047.minigameapi.database.Database;
import com.gmail.grzegorz2047.myfirstplugin.GamePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class MysqlDatabaseQueries implements DatabaseQueries {
    private final Database database;

    public MysqlDatabaseQueries(Database database) {
        this.database = database;
    }

    public void insertPlayer(String uuid, String userName, int points) throws SQLException {
        System.out.println("Parametr wejsciowy insertPlayer to " + uuid);
        Connection connection = database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO players (uniqueId, playerName, points) VALUES (?, ?, ?)");
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, userName);
        preparedStatement.setInt(3, points);
        preparedStatement.execute();
        preparedStatement.clearParameters();
        preparedStatement.close();
    }


    @Override
    public Optional<GamePlayer> getPlayer(String uuid) {
        try {
            System.out.println("Parametr wejsciowy getplayer to " + uuid);
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uniqueId=?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                return Optional.empty();
            }

            UUID id = UUID.fromString(resultSet.getString("uniqueId"));
            String playerName = resultSet.getString("playerName");
            int points = resultSet.getInt("points");
            System.out.println(id + "" + playerName + " " + points);
            return Optional.of(new GamePlayer(
                    id,
                    playerName,
                    points
            ));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }
}
