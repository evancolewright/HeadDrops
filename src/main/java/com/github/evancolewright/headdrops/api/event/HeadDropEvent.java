package com.github.evancolewright.headdrops.api.event;

import com.github.evancolewright.headdrops.HeadDropType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

@Getter
public final class HeadDropEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private final Player victim;
    private final Player killer;
    private final HeadDropType headDropType;

    @Setter
    private ItemStack victimHead;
    private boolean canceled = false;

    public HeadDropEvent(Player victim, Player killer, HeadDropType dropType, ItemStack victimHead)
    {
        this.victim = victim;
        this.killer = killer;
        this.headDropType = dropType;
        this.victimHead = victimHead;
    }

    @Override
    public boolean isCancelled()
    {
        return false;
    }

    @Override
    public void setCancelled(boolean b)
    {
        this.canceled = b;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
