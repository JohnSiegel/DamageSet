package com.sosa.damageset;

import com.sosa.damageset.commands.GiveCommand;
import com.sosa.damageset.listeners.*;
import com.sosa.damageset.managers.ConfigManager;
import com.sosa.damageset.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class DamageSet extends JavaPlugin {

    private static DamageSet instance;

    private static void registerListeners(Class<?>... listeners)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(
                    (Listener) listener.getConstructor().newInstance(), getInstance());
        }
    }

    public static DamageSet getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.load();
        getCommand("ds").setExecutor(new GiveCommand());
        try {
            registerListeners(ArmorSwapListener.class, JoinListener.class, DeathListener.class, InteractListener.class,
                    AttackListener.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManager.updatePlayerBuff(player);
        }
    }
}
