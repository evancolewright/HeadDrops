package com.github.evancolewright.headdrops.api;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class HeadDropsAPI
{
    /**
     * Checks whether an {@link org.bukkit.inventory.ItemStack} is a player head
     * that is created by the HeadDrops plugin.
     *
     * @param itemStack the item to check
     * @return true if the iteStack is a player head, false otherwise
     */
    public static boolean isPlayerHead(ItemStack itemStack)
    {
        if (itemStack == null) return false;
        if (itemStack.getType() == Material.AIR) return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData() || !nbtItem.hasCustomNbtData())
            return false;
        return nbtItem.getString("HeadDrops_Owner") != null;
    }
}
