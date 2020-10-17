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
        connection.close();
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
            connection.close();
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

    @Override
    public void incrementStat(String uuid, PlayerTableColumn column, int value) {
        try {
            Connection connection = database.getConnection();
            String columnName = column.getColumnName();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE players  SET " + columnName + " = " + columnName + "+ ? WHERE uniqueId = ?");
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, uuid);
            int result = preparedStatement.executeUpdate();
            System.out.println("Aktualizacja rekordu zwróciła " + result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removePlayer(String uuid) {
        try {
            Connection connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM players WHERE uniqueId=?");
            preparedStatement.setString(1, uuid);
            int execute = preparedStatement.executeUpdate();
            if (execute == 0) {
                System.out.println("Nie usunieto rekordu!");
            } else {
                System.out.println("Usunieto wpis!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
