package com.sosa.damageset.listeners;

import com.sosa.damageset.Settings;
import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackListener implements Listener {

    @EventHandler
    public void entityAttackEvent(EntityDamageByEntityEvent e)
    {
        if (PlayerManager.isBuffed(e.getDamager().getUniqueId()))
        {
            e.setDamage(e.getDamage() + (e.getDamage() * (Settings.getDamageBuff() / 100.0)));
        }
    }

}
