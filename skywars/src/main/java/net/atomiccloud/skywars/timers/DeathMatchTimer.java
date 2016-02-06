package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.listeners.MoveListener;
import net.atomiccloud.skywars.util.BungeeCord;
import net.atomiccloud.skywars.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.HandlerList;

public class DeathMatchTimer extends Timer
{
    private SkyWarsPlugin plugin;

    private int countdown = 250;

    private MoveListener listener;

    public DeathMatchTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
        listener = new MoveListener( plugin );
        plugin.getServer().getPluginManager().registerEvents(
                listener, plugin );
    }

    @Override
    public void run()
    {
        plugin.getGameManager().getPlayers().forEach( skyWarsPlayer -> skyWarsPlayer.getGameBoard().setScore( 10, "&a"
                + GameState.values()[ plugin.getGameManager().getGameState().ordinal() + 1 ].getName()
                + " " + Util.formatTime( plugin.getGameManager().getCurrentTimer().getCountdown(), true ) ) );
        for ( Player player : Bukkit.getOnlinePlayers() ) player.setLevel( countdown );
        switch ( countdown )
        {
            case 250:
            case 245:
            case 244:
            case 243:
            case 242:
            case 241:
                Bukkit.broadcastMessage( ChatColor.YELLOW + "Death match starting in " + ChatColor.RED + Util.formatTime( countdown - 120, false ) );
                break;
            case 240:
                HandlerList.unregisterAll( listener );
                Bukkit.broadcastMessage( ChatColor.YELLOW + "Death match started!" );
            case 180:
            case 121:
                spawnWither();
                break;
            case 120:
            case 60:
            case 30:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                broadcastEnd();
                break;
            case 1:
                broadcastEnd();
                Bukkit.getOnlinePlayers().forEach( new BungeeCord( plugin )::toLobby );
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
        countdown--;
    }

    private void broadcastEnd()
    {
        Bukkit.broadcastMessage( ChatColor.YELLOW + "Death match ending in " + ChatColor.RED + Util.formatTime( countdown, false ) );
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }


    private void spawnWither()
    {
        World world = Bukkit.getWorld( "DeathArena" );
        Wither wither = world.spawn( new Location( world, 0, 80, 0 ), Wither.class );
        wither.setCustomNameVisible( true );
        wither.setCustomName( ChatColor.RED + "Death Match" );
    }
}