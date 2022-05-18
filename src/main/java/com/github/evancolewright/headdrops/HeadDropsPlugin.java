package com.github.evancolewright.headdrops;

import com.github.evancolewright.headdrops.handlers.CacheHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadDropsPlugin extends JavaPlugin
{
    private CacheHandler cacheHandler;
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        this.cacheHandler = new CacheHandler(this);
        this.cacheHandler.loadCache();
        getServer().getPluginManager().registerEvents(new HeadDropsListeners(this, this.cacheHandler), this);
    }

    @Override
    public void onDisable()
    {
        this.cacheHandler.saveCache();
    }
}
