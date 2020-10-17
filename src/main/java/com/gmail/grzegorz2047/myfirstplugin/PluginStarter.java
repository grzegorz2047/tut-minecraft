package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.minigameapi.database.Database;
import com.gmail.grzegorz2047.minigameapi.database.MysqlDatabase;
import com.gmail.grzegorz2047.myfirstplugin.command.BansCommand;
import com.gmail.grzegorz2047.myfirstplugin.command.SpawnsCommand;
import com.gmail.grzegorz2047.myfirstplugin.database.DatabaseQueries;
import com.gmail.grzegorz2047.myfirstplugin.database.MysqlDatabaseQueries;
import com.gmail.grzegorz2047.myfirstplugin.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginStarter extends JavaPlugin {

    private Database mysqlDatabase;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        GameConfiguration gameConfiguration = new GameConfiguration(this, this.getConfig());

        mysqlDatabase = new MysqlDatabase("localhost", 3306, "java", "java", "123456789");
        mysqlDatabase.connect();
        DatabaseQueries queries = new MysqlDatabaseQueries(mysqlDatabase);
        Game game = new Game(this, gameConfiguration, queries);
        registerCommands(gameConfiguration, game);
        registerEvents(game);
        Bukkit.getWorlds().get(0).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        System.out.println(this.getName() + " zostal wlaczony!");
    }

    @Override
    public void onDisable() {
        mysqlDatabase.disconnect();
    }

    private void registerCommands(GameConfiguration gameConfiguration, Game game) {
        getCommand("spawns").setExecutor(new SpawnsCommand(gameConfiguration));
        getCommand("bans").setExecutor(new BansCommand(game));
    }

    private void registerEvents(Game game) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEnterServerListener(game), this);
        pluginManager.registerEvents(new PlayerQuitListener(game), this);
        pluginManager.registerEvents(new PlayerDeathListener(game), this);
        pluginManager.registerEvents(new GameCounterCycleListener(game), this);
        pluginManager.registerEvents(new GameCounterEndListener(game), this);
        pluginManager.registerEvents(new PlayerSafetyMeasuresListener(game), this);
    }


}
