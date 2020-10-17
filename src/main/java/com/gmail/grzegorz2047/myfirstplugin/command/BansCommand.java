package com.gmail.grzegorz2047.myfirstplugin.command;

import com.gmail.grzegorz2047.myfirstplugin.Game;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BansCommand implements CommandExecutor {
    private final Game game;

    public BansCommand(Game game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = ((Player) commandSender);
        if (!(player.isOp()) && !player.hasPermission("myfirstplugin.ban")) {
            return false;
        }
        if (args.length == 2) {//bans ban nick
            String action = args[0];
            String nick = args[1];
            if (action.equalsIgnoreCase("ban")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nick);
                boolean online = offlinePlayer.isOnline();
                if (online) {
                    offlinePlayer.getPlayer().kickPlayer("Ban");
                }
                game.banPlayer(offlinePlayer);
            }
            if (action.equalsIgnoreCase("unban")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nick);
                game.unbanPlayer(offlinePlayer);
                player.sendMessage("Gracz odbanowany!");
            }
        }
        return true;
    }
}
