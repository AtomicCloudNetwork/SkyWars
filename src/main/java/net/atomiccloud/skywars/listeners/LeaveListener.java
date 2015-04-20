package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener
{
    private SkyWarsPlugin plugin;

    public LeaveListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        handleLeave( e.getPlayer() );
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        handleLeave( event.getPlayer() );
    }

    private void handleLeave(Player player)
    {
        if ( plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            if ( Bukkit.getServer().getOnlinePlayers().size() <= 1 )
            {
                plugin.getGameManager().setGameState( GameState.PRE_GAME );
                Bukkit.broadcastMessage( plugin.getPrefix() + "Game timer had ended not enough players to start!" );

            }
        }
        if ( plugin.getGameManager().getPlayersInGame().contains( player.getName() ) )
            plugin.getGameManager().getPlayersInGame().remove( player.getName() );
        if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
        {
            if ( Bukkit.getServer().getOnlinePlayers().size() < 2 )
            {
                for ( Player players : Bukkit.getServer().getOnlinePlayers() )
                {
                    players.kickPlayer( ChatColor.RED + "Server ran out of players." );
                    Bukkit.shutdown();
                }
            }
        }
    }
}
