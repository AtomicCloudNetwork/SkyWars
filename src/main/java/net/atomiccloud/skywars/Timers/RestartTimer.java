package net.atomiccloud.skywars.Timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class RestartTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;
    private int countdown = 20;

    public RestartTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        switch ( countdown )
        {
            case 10:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is restarting in 10 seconds." );
                break;
            case 3:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is restarting in 3 seconds!" );
                break;
            case 2:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is restarting in 2 seconds!" );
                break;
            case 1:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is restarting in 1 seconds!" );
                Bukkit.getServer().getOnlinePlayers().forEach( BungeeCord::toHub );
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
    }
}
