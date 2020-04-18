package com.gmail.grzegorz2047.myfirstplugin.command;

import com.gmail.grzegorz2047.minigameapi.team.TeamID;
import com.gmail.grzegorz2047.myfirstplugin.GameConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnsCommand implements CommandExecutor {
    private final GameConfiguration gameConfiguration;

    public SpawnsCommand(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = ((Player) commandSender);
        if (!(player.isOp()) && !player.hasPermission("myfirstplugin.spawns.set")) {
            return false;
        }
        if (args.length == 2) {//spawns set team1
            String action = args[0];
            String teamLabel = args[1];
            if (!action.equalsIgnoreCase("set")) {
                return false;
            }
            Location playerLocation = player.getLocation();
            if (teamLabel.equalsIgnoreCase(TeamID.TEAM_1.getTeamRawLabel())) {
                gameConfiguration.setTeamSpawn(TeamID.TEAM_1, playerLocation);
                player.sendMessage(ChatColor.GOLD + "Spawn dla " + TeamID.TEAM_1 + " zostal ustawiony!");
                return true;
            } else if (teamLabel.equalsIgnoreCase(TeamID.TEAM_2.getTeamRawLabel())) {
                gameConfiguration.setTeamSpawn(TeamID.TEAM_2, playerLocation);
                player.sendMessage(ChatColor.GOLD + "Spawn dla " + TeamID.TEAM_2 + " zostal ustawiony!");
                return true;
            } else if(teamLabel.equalsIgnoreCase("lobby")) {
                gameConfiguration.setSpawnLobby(playerLocation);
                player.sendMessage(ChatColor.GOLD + "Spawn lobby zostal ustawiony!");
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}
