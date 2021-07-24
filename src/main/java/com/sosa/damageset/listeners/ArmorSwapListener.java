package com.sosa.damageset.listeners;

import com.sosa.damageset.DamageSet;
import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorSwapListener implements Listener {

    @EventHandler
    public void armorSwapEvent(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();
        ItemStack cursorItem = e.getCursor();

        if ((clickedItem != null && clickedItem.getType().name().startsWith("DIAMOND_")) ||
                (cursorItem != null && cursorItem.getType().name().startsWith("DIAMOND_"))) {
            DamageSet.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(DamageSet.getInstance(), () -> {

                Player player = (Player) e.getInventory().getHolder();

                PlayerManager.updatePlayerBuff(player);
            }, 1L);
        }
    }

}
