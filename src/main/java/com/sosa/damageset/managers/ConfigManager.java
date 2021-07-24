package com.sosa.damageset.managers;

import com.sosa.damageset.DamageSet;
import com.sosa.damageset.Settings;
import com.sosa.damageset.Utils.Config.Configuration;
import com.sosa.damageset.Utils.Config.ConfigurationProvider;
import com.sosa.damageset.Utils.Config.YamlConfiguration;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private static final ConfigurationProvider ymlProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);

    public static void load()
    {
        File file = new File(DamageSet.getInstance().getDataFolder().getPath() + "/config.yml");

        if (!file.exists())
        {
            DamageSet.getInstance().saveResource("config.yml", true);
        }

        try {
            Configuration config = ymlProvider.load(file);

            String path = "item-display-names.";

            Settings.setItemName(Material.DIAMOND_HELMET, formatColor(config.getString(path  + "helmet")));
            Settings.setItemName(Material.DIAMOND_CHESTPLATE, formatColor(config.getString(path  + "chestplate")));
            Settings.setItemName(Material.DIAMOND_LEGGINGS, formatColor(config.getString(path  + "leggings")));
            Settings.setItemName(Material.DIAMOND_BOOTS, formatColor(config.getString(path  + "boots")));

            Settings.setDamageBuff(config.getInt("damage-buff-percent"));

            ArrayList<String> lore = new ArrayList<>(Collections.singleton(""));

            for (String loreLine : config.getStringList("item-lore"))
            {
                lore.add(formatColor(loreLine));
            }

            Settings.setLore(lore);

            path = "default-item-enchantments.";

            Settings.setDefaultEnchantments(Material.DIAMOND_HELMET,
                    parseEnchantments(config.getStringList(path + "helmet")));
            Settings.setDefaultEnchantments(Material.DIAMOND_CHESTPLATE,
                    parseEnchantments(config.getStringList(path + "chestplate")));
            Settings.setDefaultEnchantments(Material.DIAMOND_LEGGINGS,
                    parseEnchantments(config.getStringList(path + "leggings")));
            Settings.setDefaultEnchantments(Material.DIAMOND_BOOTS,
                    parseEnchantments(config.getStringList(path + "boots")));

            Settings.setEquipMessage(formatColor(config.getString("equip-message")));
            Settings.setUnequipMessage(formatColor(config.getString("unequip-message")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<Enchantment, Integer> parseEnchantments(List<String> strings)
    {
        HashMap<Enchantment, Integer> output = new HashMap<>();

        for (String string : strings)
        {
            int splitIndex = string.indexOf(" ");
            Enchantment enchantment = Enchantment.getByName(string.substring(0, splitIndex));
            int level = Integer.parseInt(string.substring(splitIndex + 1));

            output.put(enchantment, level);
        }

        return output;
    }

    private static String formatColor(String input)
    {
        return input.replaceAll("&", ChatColor.COLOR_CHAR + "");
    }

}
