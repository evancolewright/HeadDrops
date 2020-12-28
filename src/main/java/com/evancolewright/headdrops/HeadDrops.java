package com.evancolewright.headdrops;

import org.bukkit.plugin.java.JavaPlugin;

public final class HeadDrops extends JavaPlugin
{

    private final HeadDropListeners headDropListeners = new HeadDropListeners(this);

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(headDropListeners, this);
    }
}
