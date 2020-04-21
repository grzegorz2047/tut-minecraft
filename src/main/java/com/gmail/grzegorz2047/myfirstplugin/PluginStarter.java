package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.myfirstplugin.command.SpawnsCommand;
import com.gmail.grzegorz2047.myfirstplugin.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginStarter extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        GameConfiguration gameConfiguration = new GameConfiguration(this, this.getConfig());
        registerCommands(gameConfiguration);
        Game game = new Game(this, gameConfiguration);
        registerEvents(game);
        Bukkit.getWorlds().get(0).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        System.out.println(this.getName() + " zostal wlaczony!");
    }

    private void registerCommands(GameConfiguration gameConfiguration) {
        getCommand("spawns").setExecutor(new SpawnsCommand(gameConfiguration));
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
