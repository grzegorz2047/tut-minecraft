package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game {
    private boolean started;
    private List<String> team1 = new ArrayList<>();
    private List<String> team2 = new ArrayList<>();

    public boolean isStarted() {
        return started;
    }

    public void addPlayer(Player player) {
        int teamNumber = assignPlayerToATeam(player.getName());
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

    private int assignPlayerToATeam(String playerName) {
        int team1Size = team1.size();
        int team2Size = team2.size();
        if (team1Size < team2Size) {
            team1.add(playerName);
            return 1;
        } else {
            team2.add(playerName);
            return 2;
        }
    }

    public void removePlayer(Player player) {
        String playerName = player.getName();
        team1.remove(playerName);
        team2.remove(playerName);
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "Gracz " + player.getName() + " opuscil serwer!");
    }

    public void verifyState() {
        if(isStarted()) {
            if (isOneTeamLeft()) {
                end();
            }
        }
    }
    private void end() {
        //Informuj kto wygral
        //teleportuj gracza na spawn
        //wyczysc gracza
    }

    private boolean isOneTeamLeft() {
        return team1.size() == 0 || team2.size() == 0;
    }
}
