package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.BungeeCord;
import net.atomiccloud.skywars.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RestartTimer extends Timer
{

    private SkyWarsPlugin plugin;
    private int countdown = 10;

    public RestartTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
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
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Bukkit.broadcastMessage( ChatColor.YELLOW
                        + "Server is restarting in " + ChatColor.RED + Util.formatTime( countdown, false ) );
                if ( countdown == 1 )
                    Bukkit.getServer().getOnlinePlayers().forEach( new BungeeCord( plugin )::toLobby );
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
        countdown--;
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }
}