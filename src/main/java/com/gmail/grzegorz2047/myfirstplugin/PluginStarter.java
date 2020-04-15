package com.gmail.grzegorz2047.myfirstplugin;

import com.gmail.grzegorz2047.myfirstplugin.listeners.GameCounterCycleListener;
import com.gmail.grzegorz2047.myfirstplugin.listeners.GameCounterEndListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginStarter extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        GameConfiguration gameConfiguration = new GameConfiguration(this.getConfig());
        Game game = new Game(this, gameConfiguration);
        registerEvents(game);
        System.out.println(this.getName() + " zostal wlaczony!");
    }

    private void registerEvents(Game game) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(game), this);
        pluginManager.registerEvents(new PlayerQuitListener(game), this);
        pluginManager.registerEvents(new PlayerDeathListener(game), this);
        pluginManager.registerEvents(new GameCounterCycleListener(game), this);
        pluginManager.registerEvents(new GameCounterEndListener(game), this);
    }


}
