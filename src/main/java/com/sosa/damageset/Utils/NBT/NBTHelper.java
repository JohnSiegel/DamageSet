package com.sosa.damageset.Utils.NBT;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTHelper {

    private static final String NBT_TAG_KEY = "custom_set";
    private static final String NBT_TAG_DAMAGE_SET_VALUE = "damage";

    private static Class nbtTagCompoundClass;
    private static Class nbtTagStringClass;

    private static Method asNMSCopy;
    private static Method asBukkitCopy;
    private static Method hasTag;
    private static Method getTag;
    private static Method setTag;
    private static Method compoundSetString;
    private static Method compoundGetString;

    @SuppressWarnings("all")
    public static void setup() {
        try {
            Class craftItemStackClass = Class.forName("org.bukkit.craftbukkit." +
                    Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] +
                    ".inventory.CraftItemStack");

            Class nmsItemStackClass = Class.forName("net.minecraft.server." +
                    Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] +
                    ".ItemStack");

            nbtTagCompoundClass = Class.forName("net.minecraft.server." +
                    Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] +
                    ".NBTTagCompound");

            nbtTagStringClass = Class.forName("net.minecraft.server." +
                    Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] +
                    ".NBTTagString");

            asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);

            asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", nmsItemStackClass);

            hasTag = nmsItemStackClass.getDeclaredMethod("hasTag");

            getTag = nmsItemStackClass.getDeclaredMethod("getTag");

            compoundSetString = nbtTagCompoundClass.getDeclaredMethod("set", String.class,
                    nbtTagStringClass.getSuperclass());

            compoundGetString = nbtTagCompoundClass.getDeclaredMethod("getString", String.class);

            setTag = nmsItemStackClass.getDeclaredMethod("setTag", nbtTagCompoundClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public static ItemStack applyNBT(ItemStack itemStack) {
        try {
            Object copyStack = asNMSCopy.invoke(null, itemStack);

            Object nbtTagCompound = (boolean) hasTag.invoke(copyStack) ? getTag.invoke(copyStack) :
                    nbtTagCompoundClass.getConstructor().newInstance();

            compoundSetString.invoke(nbtTagCompound,
                    NBT_TAG_KEY, nbtTagStringClass.getConstructor(String.class)
                            .newInstance(NBT_TAG_DAMAGE_SET_VALUE));

            setTag.invoke(copyStack, nbtTagCompound);

            return (ItemStack) asBukkitCopy.invoke(null, copyStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isDamageSetPiece(ItemStack itemStack)
    {
        try {
            Object copyStack = asNMSCopy.invoke(null, itemStack);

            if (copyStack == null) {
                return false;
            }

            if (!(boolean) hasTag.invoke(copyStack))
            {
                return false;
            }

            Object nbtTagCompound = getTag.invoke(copyStack);

            return compoundGetString.invoke(nbtTagCompound, NBT_TAG_KEY).equals(NBT_TAG_DAMAGE_SET_VALUE);
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
