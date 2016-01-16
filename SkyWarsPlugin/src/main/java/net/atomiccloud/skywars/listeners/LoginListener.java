package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener
{

    private SkyWarsPlugin plugin;

    public LoginListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        player.setLevel( 0 );
        player.setFoodLevel( 20 );
        player.getInventory().clear();
        player.teleport( plugin.getConfiguration().getSpawnLocation() );
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            e.setJoinMessage( null );
            player.kickPlayer( "Game already started!" );
            return;
        }

        if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) )
        {
            int players = Bukkit.getServer().getOnlinePlayers().size();
            if ( players >= 2 )
            {
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GREEN + "Game starting in 60 seconds." );
                plugin.getGameManager().setGameState( GameState.LOBBY_COUNTDOWN );
            }
        }
        plugin.getGameManager().getPlayersInGame().add( player.getName() );
        e.setJoinMessage( null );
        player.setGameMode( GameMode.SURVIVAL );
        player.setScoreboard( plugin.getGameManager().getVotesBoard() );
        player.sendMessage( "\n\n\n\n\n\n" );
        plugin.getGameManager().sendVoteMessage( player );
        for ( Player players : Bukkit.getOnlinePlayers() )
        {
            players.setFlying( false );
            players.setAllowFlight( false );
        }
    }
}