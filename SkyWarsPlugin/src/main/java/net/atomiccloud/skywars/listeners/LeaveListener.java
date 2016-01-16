package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
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

import java.util.Collection;
import java.util.stream.Collectors;

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
        e.setQuitMessage( null );
        handleLeave( e.getPlayer() );
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerKick(PlayerKickEvent event)
    {
        handleLeave( event.getPlayer() );
    }

    private void handleLeave(Player player)
    {
        plugin.getGameManager().removePlayer( player );
        Collection<SkyWarsPlayer> players = plugin.getGameManager().getPlayers()
                .stream().filter( skyWarsPlayer -> skyWarsPlayer.getTeam().equals( Team.PLAYER ) )
                .collect( Collectors.toSet() );
        plugin.getGameManager().getPlayers().forEach( skyWarsPlayer ->
                skyWarsPlayer.getGameBoard().setScore( 8, "Players left: &a" + players.size() ) );
        if ( plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            if ( players.size() < 2 )
            {
                plugin.getGameManager().setGameState( GameState.PRE_GAME );
                Bukkit.broadcastMessage( plugin.getPrefix() + "Game timer had ended not enough players to start!" );
            }
        }

        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) &&
                !plugin.getGameManager().getGameState().equals( GameState.POST_GAME ) )
        {
            if ( players.size() == 1 )
            {
                Player winner = Bukkit.getPlayer( players.stream().findFirst().get().getPlayerUuid() );
                //Redis.addCoins( winner, 500 );
                Bukkit.broadcastMessage( plugin.getPrefix() + winner.getName() + " has won the game!" );
                Bukkit.broadcastMessage( ChatColor.RED + "Server ran out of players." );
                Bukkit.getOnlinePlayers().forEach( new BungeeCord( plugin )::toLobby );
                Bukkit.shutdown();
            }
        }
    }
}