package com.github.evancolewright.headdrops.model;

import com.github.evancolewright.headdrops.utilities.HeadUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

@Getter
public final class PlayerHeadData
{
    private final UUID owningPlayerUUID;
    private final String displayName;
    private final List<String> lore;
    private final Location location;

    public PlayerHeadData(UUID owningPlayerUUID, String displayName, List<String> lore, Location location)
    {
        this.owningPlayerUUID = owningPlayerUUID;
        this.displayName = displayName;
        this.lore = lore;
        this.location = location;
    }

    public static PlayerHeadData fromPlacedHead(ItemStack itemStack, Location location)
    {
        NBTItem item = new NBTItem(itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        return new PlayerHeadData(item.getUUID(
                "HeadDrops_Owner"),
                itemMeta.getDisplayName(),
                itemMeta.getLore(),
                location
        );
    }

    public ItemStack toItemStack()
    {
        return HeadUtils.createPlayerHead(this.owningPlayerUUID, null, this.displayName, this.lore);
    }
}
