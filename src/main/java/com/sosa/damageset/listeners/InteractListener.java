package com.sosa.damageset.listeners;

import com.sosa.damageset.DamageSet;
import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        ItemStack item = e.getItem();

        if (item != null && item.getType().name().startsWith("DIAMOND_")) {
            DamageSet.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(DamageSet.getInstance(), () -> PlayerManager.updatePlayerBuff(e.getPlayer()), 1L);
        }
    }
}
