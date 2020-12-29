package com.evancolewright.headdrops;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadDrops extends JavaPlugin
{
    private int minorVersion;
    private final Server server = getServer();
    private final HeadDropListeners headDropListeners = new HeadDropListeners(this);

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        setMinor();
        getLogger().info("Found minor version: " + minorVersion + " (" + getMinecraftVersion() + ")");
        server.getPluginManager().registerEvents(headDropListeners, this);
    }

    /**
     * Set the minor version (8 = 1.8, 9 = 1.9 etc..)
     */
    private void setMinor()
    {
        try {
            String minor = getMinecraftVersion().split("_")[1];
            this.minorVersion = Integer.parseInt(minor);
        } catch(NumberFormatException exception)
        {
            getLogger().severe("Error while trying to establish server version! Defaulting to 1.16");
            this.minorVersion = 16;
        }
    }

    /**
     * Obtains the minecraft server version as a substring.
     * @return  The minecraft version (ex: v1_8_R1)
     */
    private String getMinecraftVersion()
    {
        String fullPackage = server.getClass().getPackage().getName();

        return fullPackage.substring(fullPackage.lastIndexOf('.') + 1);
    }

    public int getMinorVersion()
    {
        return this.minorVersion;
    }
}
