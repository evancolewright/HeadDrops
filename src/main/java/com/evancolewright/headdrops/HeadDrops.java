package com.evancolewright.headdrops;

import org.bukkit.plugin.java.JavaPlugin;

public final class HeadDrops extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new HeadDropListeners(this), this);
    }
}
