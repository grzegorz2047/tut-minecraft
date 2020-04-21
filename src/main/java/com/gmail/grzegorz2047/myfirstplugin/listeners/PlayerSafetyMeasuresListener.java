package com.gmail.grzegorz2047.myfirstplugin.listeners;

import com.gmail.grzegorz2047.myfirstplugin.Game;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlayerSafetyMeasuresListener implements Listener {
    private final Game game;
    private final List<Material> forbiddenItems = Arrays.asList(Material.ENDER_PEARL, Material.LAVA_BUCKET);

    public PlayerSafetyMeasuresListener(Game game) {
        this.game = game;
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (!game.isStarted()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        if (!game.isStarted()) {
            e.setCancelled(true);
        }
        if (game.isinWarmup()) {
            ItemStack item = e.getItem();
            if(item == null) {
                return;
            }
            Material type = item.getType();

            if (forbiddenItems.contains(type)) {
                e.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    private void onHunger(FoodLevelChangeEvent e) {
        if (!game.isStarted()) {
            e.setCancelled(true);
        }
    }


}
