package net.atomiccloud.skywars.timers;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Timer extends BukkitRunnable
{
    public abstract int getCountdown();
}
