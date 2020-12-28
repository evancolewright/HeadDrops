package com.evancolewright.headdrops.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerDropHeadEvent extends Event implements Cancellable
{
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    /**
     * The player that died / was killed
     */
    private final Player player;

    /**
     * The player that killed the player (WILL BE NULL IF THE PLAYER DID NOT GET KILLED BY ANOTHER PLAYER)
     */
    private final Player killer;

    /**
     * The status of the event
     */
    private boolean cancelled;

    public PlayerDropHeadEvent(Player player, Player killer)
    {
        this.player = player;
        this.killer = killer;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public Player getKiller()
    {
        return this.killer;
    }

    @Override
    public boolean isCancelled()
    {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS_LIST;
    }

}
