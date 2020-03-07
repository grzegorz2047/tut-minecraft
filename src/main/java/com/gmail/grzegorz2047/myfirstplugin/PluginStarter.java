package com.gmail.grzegorz2047.myfirstplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginStarter extends JavaPlugin {

    @Override
    public void onEnable() {
        Game game = new Game();
        registerEvents(game);
        System.out.println(this.getName() + " zostal wlaczony!");
    }

    private void registerEvents(Game game) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(game), this);
        pluginManager.registerEvents(new PlayerQuitListener(game), this);
        pluginManager.registerEvents(new PlayerDeathListener(game), this);
    }


}
