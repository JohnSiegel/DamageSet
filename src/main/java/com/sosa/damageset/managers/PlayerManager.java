package com.sosa.damageset.managers;

import com.sosa.damageset.Settings;
import com.sosa.damageset.Utils.NBT.NBTHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private static final ArrayList<UUID> buffedPlayers = new ArrayList<>();

    public static void updatePlayerBuff(Player player) {
        ItemStack[] equipment = player.getEquipment().getArmorContents();

        for (ItemStack item : equipment) {
            if (!NBTHelper.isDamageSetPiece(item)) {
                deactivateBuff(player);
                return;
            }
        }

        PlayerManager.activateBuff(player);
    }

    public static void activateBuff(Player player) {
        if (!buffedPlayers.contains(player.getUniqueId())) {
            buffedPlayers.add(player.getUniqueId());
            player.sendMessage(Settings.getEquipMessage());
        }
    }

    public static void deactivateBuff(Player player) {
        if (buffedPlayers.contains(player.getUniqueId())) {
            buffedPlayers.remove(player.getUniqueId());
            player.sendMessage(Settings.getUnequipMessage());
        }
    }

    public static boolean isBuffed(UUID uuid) {
        return buffedPlayers.contains(uuid);
    }

}
