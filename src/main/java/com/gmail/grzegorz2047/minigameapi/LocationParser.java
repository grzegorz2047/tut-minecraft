package com.gmail.grzegorz2047.minigameapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationParser {
    public LocationParser() {
    }

    public Location parseLocation(String teamSpawnValue) {
        String[] spawnValues = teamSpawnValue.split(",");
        if (spawnValues.length != 6) {
            System.out.println("Posiadasz niepelna konfiguracje spawnu druzyny!");
        }
        String worldName = spawnValues[0];
        double x = Double.parseDouble(spawnValues[1]);
        double y = Double.parseDouble(spawnValues[2]);
        double z = Double.parseDouble(spawnValues[3]);
        float yaw = Float.parseFloat(spawnValues[4]);
        float pitch = Float.parseFloat(spawnValues[5]);

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}