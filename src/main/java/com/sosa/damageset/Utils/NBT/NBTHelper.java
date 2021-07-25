package com.sosa.damageset.Utils.NBT;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTHelper {

    public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static final String NBT_TAG_KEY = "custom_set";
    private static final String NBT_TAG_DAMAGE_SET_VALUE = "damage";

    @SuppressWarnings("all")
    public static ItemStack applyNBT(ItemStack itemStack) {
        try {
            Object copyStack = Methods.CRAFT_ITEM_STACK_AS_NMS_COPY.method.invoke(null, itemStack);

            Object nbtTagCompound = (boolean) Methods.NMS_ITEM_STACK_HAS_TAG.method.invoke(copyStack) ?
                    Methods.NMS_ITEM_STACK_GET_TAG.method.invoke(copyStack) :
                    Classes.NBT_TAG_COMPOUND.clazz.getConstructor().newInstance();

            Methods.NBT_TAG_COMPOUND_SET.method.invoke(nbtTagCompound,
                    NBT_TAG_KEY, Classes.NBT_TAG_STRING.clazz.getConstructor(String.class)
                            .newInstance(NBT_TAG_DAMAGE_SET_VALUE));

            Methods.NMS_ITEM_STACK_SET_TAG.method.invoke(copyStack, nbtTagCompound);

            return (ItemStack) Methods.CRAFT_ITEM_STACK_AS_BUKKIT_COPY.method.invoke(null, copyStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isDamageSetPiece(ItemStack itemStack) {
        try {
            Object copyStack = Methods.CRAFT_ITEM_STACK_AS_NMS_COPY.method.invoke(null, itemStack);

            if (copyStack == null) {
                return false;
            }

            if (!(boolean) Methods.NMS_ITEM_STACK_HAS_TAG.method.invoke(copyStack)) {
                return false;
            }

            Object nbtTagCompound = Methods.NMS_ITEM_STACK_GET_TAG.method.invoke(copyStack);

            return Methods.NBT_TAG_COMPOUND_GET_STRING.method.invoke(nbtTagCompound, NBT_TAG_KEY)
                    .equals(NBT_TAG_DAMAGE_SET_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private enum Classes {
        NBT_TAG_COMPOUND("net.minecraft.server." + NMS_VERSION + ".NBTTagCompound"),
        NBT_TAG_STRING("net.minecraft.server." + NMS_VERSION + ".NBTTagString"),
        CRAFT_ITEM_STACK("org.bukkit.craftbukkit." + NMS_VERSION + ".inventory.CraftItemStack"),
        NMS_ITEM_STACK("net.minecraft.server." + NMS_VERSION + ".ItemStack");

        private Class<?> clazz;

        Classes(String className) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private enum Methods {
        CRAFT_ITEM_STACK_AS_NMS_COPY(Classes.CRAFT_ITEM_STACK.clazz, "asNMSCopy", ItemStack.class),
        CRAFT_ITEM_STACK_AS_BUKKIT_COPY(Classes.CRAFT_ITEM_STACK.clazz, "asBukkitCopy",
                Classes.NMS_ITEM_STACK.clazz),
        NMS_ITEM_STACK_HAS_TAG(Classes.NMS_ITEM_STACK.clazz, "hasTag"),
        NMS_ITEM_STACK_GET_TAG(Classes.NMS_ITEM_STACK.clazz, "getTag"),
        NMS_ITEM_STACK_SET_TAG(Classes.NMS_ITEM_STACK.clazz, "setTag", Classes.NBT_TAG_COMPOUND.clazz),
        NBT_TAG_COMPOUND_SET(Classes.NBT_TAG_COMPOUND.clazz, "set",
                String.class, Classes.NBT_TAG_STRING.clazz.getSuperclass()),
        NBT_TAG_COMPOUND_GET_STRING(Classes.NBT_TAG_COMPOUND.clazz, "getString", String.class);

        private Method method;

        Methods(Class<?> clazz, String methodName, Class<?>... typeArgs) {
            try {
                method = clazz.getDeclaredMethod(methodName, typeArgs);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}
