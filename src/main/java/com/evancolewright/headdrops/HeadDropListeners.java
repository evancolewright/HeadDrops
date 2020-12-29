package com.evancolewright.headdrops;

import com.evancolewright.headdrops.api.PlayerDropHeadEvent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class HeadDropListeners implements Listener
{
    private final FileConfiguration config;

    public HeadDropListeners(HeadDrops plugin)
    {
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        HeadType headType = killer == null ? HeadType.DEFAULT : HeadType.SLAIN;
        double random = Math.random();

        if (config.getBoolean(headType.getItemPath() + ".enabled"))
        {
            PlayerDropHeadEvent playerDropHeadEvent = new PlayerDropHeadEvent(player, killer);

            if (!player.hasPermission("headdrops.immune"))
            {
                // Drop the immediately regardless
                if (headType == HeadType.SLAIN && killer.hasPermission("headdrops.guarantee"))
                {
                    if (shouldDropHead(playerDropHeadEvent))
                        event.getDrops().add(getPlayerHead(player, headType, killer));
                    return;
                }
                // Drop head based on chance
                if (random < config.getDouble(headType.getChancePath()))
                {
                    if (shouldDropHead(playerDropHeadEvent))
                        event.getDrops().add(getPlayerHead(player, headType, killer));
                }
            }
        }
    }

    private boolean shouldDropHead(PlayerDropHeadEvent event)
    {
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    private ItemStack getPlayerHead(Player player, HeadType headType, Player killer)
    {
        // Construct what we can prior to checking if the player was slain
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerMeta = (SkullMeta) playerHead.getItemMeta();
        playerMeta.setOwningPlayer(player);

        String name = colorize(config.getString(headType.getItemPath() + ".name"));
        List<String> lore = colorize(config.getStringList(headType.getItemPath() + ".lore"));

        // Replace placeholders for displayName
        playerMeta.setDisplayName(replacePlaceholders(name, player, killer));

        // Replace placeholders for lore
        List<String> replacedLore = new ArrayList<>();
        lore.forEach(line -> replacedLore.add(replacePlaceholders(line, player, killer)));

        playerMeta.setLore(replacedLore);

        playerHead.setItemMeta(playerMeta);

        return playerHead;
    }

    private String replacePlaceholders(String string, Player player, Player killer)
    {
        // Replace common placeholders
        string = string.replace("{PLAYER}", player.getName())
                .replace("{TIMESTAMP}", new SimpleDateFormat("MMM dd, yyy")
                        .format(new Date(System.currentTimeMillis())));

        // Replace conditional placeholders
        if (killer != null)
        {
            ItemStack itemInHand = killer.getInventory().getItemInMainHand();

            // Determine the {ITEM} placeholder value
            String item;
            if (itemInHand.getType() == Material.AIR)
            {
                item = config.getString("slain_head.fist_text");
            } else if (!itemInHand.hasItemMeta() || itemInHand.getItemMeta().getDisplayName().equals(""))
            {
                item = capitalize(itemInHand.getType().toString().toLowerCase());
            } else
            {
                item = itemInHand.getItemMeta().getDisplayName();
            }

            string = string.replace("{KILLER}", killer.getName())
                    .replace("{ITEM}", item);
        }

        return colorize(string);
    }

    private String capitalize(String string)
    {
        StringBuilder builder = new StringBuilder();
        String[] words = string.split("_");
        for (int i = 0; i < words.length; i++)
        {
            builder.append(WordUtils.capitalize(words[i]) + (i == (words.length - 1) ? "" : " "));
        }

        return builder.toString();
    }

    private String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private List<String> colorize(List<String> strings)
    {
        List<String> colored = new ArrayList<>();

        for (String string : strings)
        {
            colored.add(colorize(string));
        }

        return colored;
    }
}
