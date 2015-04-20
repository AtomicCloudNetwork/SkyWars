package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener
{

    private SkyWarsPlugin plugin;

    public JoinListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        p.setLevel( 0 );
        p.setFoodLevel( 20 );
        p.getInventory().clear();
        p.teleport( plugin.getConfiguration().getSpawnLocation() );
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            e.setJoinMessage( null );
            p.setGameMode( GameMode.SPECTATOR );
            p.sendMessage( plugin.getPrefix() + "You have joined as a spectator." );
            plugin.getGameManager().getSpectators().add( p.getName() );
            return;
        }

        if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) )
        {
            int players = Bukkit.getServer().getOnlinePlayers().size();
            if ( players >= 2 )
            {
                Bukkit.broadcastMessage( plugin.getPrefix() + "Game Timer started." );
                plugin.getGameManager().setGameState( GameState.LOBBY_COUNTDOWN );
            }
        }
        plugin.getGameManager().getPlayersInGame().add( p.getName() );
        e.setJoinMessage( null );
        p.setGameMode( GameMode.SURVIVAL );
        p.setScoreboard( plugin.getGameManager().getVotesBoard() );
        sendJoinMessage( p );
        plugin.getGameManager().sendVoteMessage( p );
        for ( Player players : Bukkit.getOnlinePlayers() )
        {
            players.setFlying( false );
            players.setAllowFlight( false );
        }
    }

    private void sendJoinMessage(Player p) {
        p.sendMessage( "\n\n\n\n\n\n\n\n" );
        p.sendMessage( "&c&m&l-----------------------------------------" );
        p.sendMessage( "" );
        p.sendMessage( "&e&o Game: &cSkyWars" );
        p.sendMessage( "" );
        p.sendMessage( "&e&o Currently Online: &a" + Bukkit.getServer().getOnlinePlayers().size() );
        p.sendMessage( "" );
        p.sendMessage( "&c&m&l-----------------------------------------" );
        p.sendMessage( "\n" );
    }
}