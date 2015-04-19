package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
        for ( Player player : Bukkit.getOnlinePlayers() )
        {
            player.setLevel( countdown );
        }
        switch ( countdown )
        {
            case 10:
                broadcastMessage();
                break;
            case 3:
                broadcastMessage();
                break;
            case 2:
                broadcastMessage();
                break;
            case 1:
                broadcastMessage();
                Bukkit.getServer().getOnlinePlayers().forEach( BungeeCord::toHub );
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
        countdown--;
    }

    private void broadcastMessage() {
        Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is restarting in " + countdown + " seconds." );
    }
}
