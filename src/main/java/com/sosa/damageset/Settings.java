package com.sosa.damageset;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings {

    private static HashMap<Material, String> itemNames = new HashMap<>();
    private static ArrayList<String> lore;
    private static int damageBuff;
    private static String equipMessage;
    private static String unequipMessage;
    private static HashMap<Material, HashMap<Enchantment, Integer>> defaultEnchantments = new HashMap<>();

    public static HashMap<Enchantment, Integer> getDefaultEnchantments(Material material) {
        return defaultEnchantments.get(material);
    }

    public static void setDefaultEnchantments(Material material, HashMap<Enchantment, Integer> enchantments) {
        defaultEnchantments.put(material, enchantments);
    }

    public static void setItemName(Material material, String name) {
        itemNames.put(material, name);
    }

    public static String getItemName(Material material) {
        return itemNames.get(material);
    }

    public static ArrayList<String> getLore() {
        return lore;
    }

    public static void setLore(ArrayList<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("\\$damage-buff-percent\\$", damageBuff + ""));
        }
        Settings.lore = lore;
    }

    public static int getDamageBuff() {
        return damageBuff;
    }

    public static void setDamageBuff(int damageBuff) {
        Settings.damageBuff = damageBuff;
    }

    public static String getEquipMessage() {
        return equipMessage;
    }

    public static void setEquipMessage(String equipMessage) {
        Settings.equipMessage = equipMessage;
    }

    public static String getUnequipMessage() {
        return unequipMessage;
    }

    public static void setUnequipMessage(String unequipMessage) {
        Settings.unequipMessage = unequipMessage;
    }
}
