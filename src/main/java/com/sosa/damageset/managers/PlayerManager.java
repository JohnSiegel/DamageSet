package com.sosa.damageset.managers;

import com.sosa.damageset.Settings;
import com.sosa.damageset.Utils.NBT.NBTHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private static final ArrayList<UUID> equippedPlayers = new ArrayList<>();

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
        if (!equippedPlayers.contains(player.getUniqueId())) {
            equippedPlayers.add(player.getUniqueId());
            player.sendMessage(Settings.getEquipMessage());
        }
    }

    public static void deactivateBuff(Player player) {
        if (equippedPlayers.contains(player.getUniqueId())) {
            equippedPlayers.remove(player.getUniqueId());
            player.sendMessage(Settings.getUnequipMessage());
        }
    }

    public static boolean isBuffed(UUID uuid)
    {
        return equippedPlayers.contains(uuid);
    }

}
