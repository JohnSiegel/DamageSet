package com.sosa.damageset.commands;

import com.sosa.damageset.DamageSet;
import com.sosa.damageset.Settings;
import com.sosa.damageset.Utils.NBT.NBTHelper;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class GiveCommand implements CommandExecutor {
    private static void giveItems(Player player, Material... materials) {
        for (Material material : materials) {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(Settings.getItemName(material));

            itemMeta.setLore(Settings.getLore());

            for (Map.Entry<Enchantment, Integer> enchant : Settings.getDefaultEnchantments(material).entrySet()) {
                itemMeta.addEnchant(enchant.getKey(), enchant.getValue(), true);
            }

            itemStack.setItemMeta(itemMeta);

            itemStack = NBTHelper.applyNBT(itemStack);

            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(itemStack);
            } else {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        Player player = DamageSet.getInstance().getServer().getPlayer(args[1]);

        if (player == null) {
            return false;
        }

        if (args.length == 2) {
            giveItems(player, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
                    Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);
        } else {
            try {
                Material material = Material.valueOf("DIAMOND_" + args[2].toUpperCase());
                giveItems(player, material);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
