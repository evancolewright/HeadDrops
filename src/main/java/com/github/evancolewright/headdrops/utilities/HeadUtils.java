package com.github.evancolewright.headdrops.utilities;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class HeadUtils
{
    public static ItemStack createPlayerHead(UUID playerUUID, Player killer, String displayName, List<String> lore)
    {
        ItemStack playerHead;
        try
        {
            playerHead = new ItemStack(Material.getMaterial("PLAYER_HEAD"), 1);
        } catch(IllegalArgumentException | NullPointerException exception)
        {
            playerHead = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) 3);
        }
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(Bukkit.getOfflinePlayer(playerUUID).getName());
        skullMeta.setDisplayName(replacePlaceHolders(displayName, playerUUID, killer));
        skullMeta.setLore(lore.stream().map(s -> replacePlaceHolders(s, playerUUID, killer)).collect(Collectors.toList()));
        playerHead.setItemMeta(skullMeta);

        NBTItem headNBT = new NBTItem(playerHead);
        headNBT.setUUID("HeadDrops_Owner", playerUUID);

        return headNBT.getItem();
    }

    private static String replacePlaceHolders(String string, UUID playerUUID, Player killer)
    {
        string = ChatColor.translateAlternateColorCodes('&', string.replace("{PLAYER}", Bukkit.getOfflinePlayer(playerUUID).getName())
                .replace("{TIMESTAMP}", new SimpleDateFormat("MMM dd, yyy")
                        .format(new Date(System.currentTimeMillis()))));
        if (killer == null)
            return string;

        ItemStack murderWeapon = killer.getInventory().getItemInHand();
        String murderWeaponName;

        if (murderWeapon.getType() == Material.AIR)
            murderWeaponName = "Fist";
        else if (!murderWeapon.hasItemMeta() || murderWeapon.getItemMeta().getDisplayName().equals(""))
            murderWeaponName = StringUtils.capitalizeMultiWordMaterialString(murderWeapon.getType().toString().toLowerCase(), "_");
        else
            murderWeaponName = murderWeapon.getItemMeta().getDisplayName();
        string = string.replace("{KILLER}", killer.getName())
                .replace("{MURDER_WEAPON}", murderWeaponName);

        return string;
    }
}
