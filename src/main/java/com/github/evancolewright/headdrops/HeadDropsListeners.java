package com.github.evancolewright.headdrops;

import com.github.evancolewright.headdrops.api.event.HeadDropEvent;
import com.github.evancolewright.headdrops.api.HeadDropsAPI;
import com.github.evancolewright.headdrops.handlers.CacheHandler;
import com.github.evancolewright.headdrops.model.PlayerHeadData;
import com.github.evancolewright.headdrops.utilities.HeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public final class HeadDropsListeners implements Listener
{
    private final HeadDropsPlugin plugin;
    private final CacheHandler cacheHandler;
    public HeadDropsListeners(HeadDropsPlugin plugin, CacheHandler cacheHandler)
    {
        this.plugin = plugin;
        this.cacheHandler = cacheHandler;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        FileConfiguration configuration = this.plugin.getConfig();
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        HeadDropType headType = killer == null ? HeadDropType.NORMAL : HeadDropType.SLAIN;

        if (victim.hasPermission("headdrops.nodrop")) return;
        if (!configuration.getBoolean(headType.getRootPath() + ".enabled")) return;
        if (configuration.getStringList(headType.getRootPath() + ".disabled_worlds").contains(victim.getWorld().getName())) return;
        if (Math.random() > configuration.getDouble(headType.getChancePath())) return;

        HeadDropEvent headDropEvent = new HeadDropEvent(victim, killer, headType, HeadUtils.createPlayerHead(
                victim.getUniqueId(),
                killer,
                configuration.getString(headType.getItemPath() + ".name"),
                configuration.getStringList(headType.getItemPath() + ".lore")));
        Bukkit.getPluginManager().callEvent(headDropEvent);
        if (headDropEvent.isCancelled()) return;
        if (configuration.getBoolean("transport_to_killer_inventory") && headType == HeadDropType.SLAIN)
            killer.getInventory().addItem(headDropEvent.getVictimHead());
        else
            event.getDrops().add(headDropEvent.getVictimHead());
    }

    @EventHandler
    public void onHeadPlace(BlockPlaceEvent event)
    {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        if (HeadDropsAPI.isPlayerHead(itemInHand))
            this.cacheHandler.addPlayerHeadData(PlayerHeadData.fromPlacedHead(itemInHand, event.getBlockPlaced().getLocation()));
    }

    @EventHandler
    public void onHeadBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Location location = block.getLocation();
        Optional<PlayerHeadData> playerHeadData = this.cacheHandler.getPlayerHeadData(location);
        if (!playerHeadData.isPresent())
            return;
        block.setType(Material.AIR);
        block.getWorld().dropItemNaturally(location, playerHeadData.get().toItemStack());
        this.cacheHandler.removePlayerHeadData(location);
    }
}
