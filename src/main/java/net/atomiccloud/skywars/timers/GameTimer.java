package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;
    private int countdown = 800;

    public GameTimer(SkyWarsPlugin plugin)
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
            case 500:
                broadcastMessage();
                //BORDER
                break;
            case 400:
                broadcastMessage();
                //BORDER
                break;
            case 300:
                broadcastMessage();
                //BORDER
                break;
            case 200:
                broadcastMessage();
                //BORDER
                break;
            case 100:
                broadcastMessage();
                //BORDER
                break;
            case 50:
                broadcastMessage();
                //BORDER
                break;
            case 20:
                broadcastMessage();
                //BORDER
                break;
            case 10:
                broadcastMessage();
                //BORDER
                break;
            case 3:
                broadcastMessage();
                //BORDER
                break;
            case 2:
                broadcastMessage();
                //BORDER
                break;
            case 1:
                broadcastMessage();
                Bukkit.getServer().getOnlinePlayers().forEach( BungeeCord::toHub );
                //BORDER
                break;
            case 0:
                //STATS
                //COSMITES
                //START TELEPORTER COUNTDOWN
                //BORDER
                Bukkit.shutdown();
                break;
        }
        countdown--;
    }

    private void broadcastMessage()
    {
        Bukkit.broadcastMessage( countdown == 1 ? plugin.getPrefix() +
                ChatColor.GOLD + "Game is ending in " + countdown + " second!" :
                plugin.getPrefix() + ChatColor.GOLD + "Game is ending in " + countdown + " seconds!" );
    }
}
