package net.atomiccloud.skywars.listeners;

import net.DynamicJk.AtomicCore.Cosmites.Redis;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        handleLeave( e.getPlayer() );
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event)
    {
        handleLeave( event.getPlayer() );
    }

    private void handleLeave(Player player)
    {
        if ( plugin.getGameManager().getPlayersInGame().contains( player.getName() ) )
            plugin.getGameManager().getPlayersInGame().remove( player.getName() );
        if ( plugin.getGameManager().getSpectators().contains( player.getName() ) )
            plugin.getGameManager().getSpectators().remove( player.getName() );
        if ( plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            if ( plugin.getGameManager().getPlayersInGame().size() < 2 )
            {
                plugin.getGameManager().setGameState( GameState.PRE_GAME );
                Bukkit.broadcastMessage( plugin.getPrefix() + "Game timer had ended not enough players to start!" );
            }
        }

        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            if ( plugin.getGameManager().getPlayersInGame().size() == 1 )
            {
                Player winner = Bukkit.getPlayer( plugin.getGameManager().getPlayersInGame().get( 0 ) );
                Redis.addCoins( winner, 500 );
                Bukkit.broadcastMessage( plugin.getPrefix() + winner.getName() + " has won the game!" );
                Bukkit.broadcastMessage( ChatColor.RED + "Server ran out of players." );
                Bukkit.getOnlinePlayers().forEach( BungeeCord::toHub );
                Bukkit.shutdown();
            }
        }
    }
}