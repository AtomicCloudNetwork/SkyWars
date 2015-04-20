package net.atomiccloud.skywars.util;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface BukkitRunnable extends Runnable
{

    default BukkitTask runAsync()
    {
        return Bukkit.getScheduler().runTaskAsynchronously( SkyWarsPlugin.getInstance(), this );
    }

    default int runAfter(long time, TimeUnit unit)
    {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(
                SkyWarsPlugin.getInstance(), this, unit.toSeconds( time ) * 20L );
    }

    default int runAfterEvery(long time, long repeat, TimeUnit unit)
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask( SkyWarsPlugin.getInstance(),
                this, unit.toSeconds( time ) * 20L, unit.toSeconds( repeat ) * 20L );
    }
}