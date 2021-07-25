package com.sosa.damageset.managers;

import com.sosa.damageset.DamageSet;
import com.sosa.damageset.Settings;
import com.sosa.damageset.Utils.Config.Configuration;
import com.sosa.damageset.Utils.Config.ConfigurationProvider;
import com.sosa.damageset.Utils.Config.YamlConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final ConfigurationProvider ymlProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);

    public static void load() {
        File file = new File(DamageSet.getInstance().getDataFolder().getPath() + "/config.yml");

        if (!file.exists()) {
            DamageSet.getInstance().saveResource("config.yml", true);
        }

        try {
            Configuration config = ymlProvider.load(file);

            HashMap<Material, String> armorPieceNames = new HashMap<Material, String>() {{
                put(Material.DIAMOND_BOOTS, "boots");
                put(Material.DIAMOND_LEGGINGS, "leggings");
                put(Material.DIAMOND_CHESTPLATE, "chestplate");
                put(Material.DIAMOND_HELMET, "helmet");
            }};

            for (Map.Entry<Material, String> armorPiece : armorPieceNames.entrySet()) {
                Settings.setItemName(armorPiece.getKey(),
                        formatColor(config.getString("item-display-names." + armorPiece.getValue())));
            }

            Settings.setDamageBuff(config.getInt("damage-buff-percent"));

            Settings.setLore(new ArrayList<String>() {{
                add("");
                for (String loreLine : config.getStringList("item-lore")) {
                    add(formatColor(loreLine));
                }
            }});

            for (Map.Entry<Material, String> armorPiece : armorPieceNames.entrySet()) {
                Settings.setDefaultEnchantments(armorPiece.getKey(),
                        parseEnchantments(config.getStringList("default-item-enchantments."
                                + armorPiece.getValue())));
            }

            Settings.setEquipMessage(formatColor(config.getString("equip-message")));
            Settings.setUnequipMessage(formatColor(config.getString("unequip-message")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<Enchantment, Integer> parseEnchantments(List<String> configStrings) {
        return new HashMap<Enchantment, Integer>() {{
            for (String configString : configStrings) {
                int splitIndex = configString.indexOf(" ");
                put(Enchantment.getByName(configString.substring(0, splitIndex)),
                        Integer.parseInt(configString.substring(splitIndex + 1)));
            }
        }};
    }

    private static String formatColor(String input) {
        return input.replaceAll("&", ChatColor.COLOR_CHAR + "");
    }
}
