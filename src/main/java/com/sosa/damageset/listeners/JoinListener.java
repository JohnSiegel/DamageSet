package com.sosa.damageset.listeners;

import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        PlayerManager.updatePlayerBuff(player);
    }

}
