package com.sosa.damageset.listeners;

import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e)
    {
        Player player = e.getEntity();

        PlayerManager.deactivateBuff(player);
    }

}
