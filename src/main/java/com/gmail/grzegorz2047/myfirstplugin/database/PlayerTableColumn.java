package com.gmail.grzegorz2047.myfirstplugin.database;

public enum PlayerTableColumn {
    POINTS("points"),
    UNIQUE_ID("uniqueId"),
    PLAYER_NAME("playerName");

    private final String columnName;

    PlayerTableColumn(String columnName) {
        this.columnName = columnName;
    }


    public String getColumnName() {
        return columnName;
    }
}
