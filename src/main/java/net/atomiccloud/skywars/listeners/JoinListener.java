package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) ||
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
        World world = Bukkit.getServer().getWorld( p.getWorld().getName() );
        double x = plugin.getConfig().getDouble( "lobby.x" );
        double y = plugin.getConfig().getDouble( "lobby.y" );
        double z = plugin.getConfig().getDouble( "lobby.z" );
        p.teleport( new Location( world, x, y, z ) );
        handleEtc( p );
    }

    private void handleEtc(Player player) {
        if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) ||
                plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) ) {
            player.setScoreboard( plugin.getGameManager().getVotesBoard() );
            final Player p = player.getPlayer();

            p.sendMessage( "\n\n\n\n\n\n\n\n" );
            p.sendMessage( ChatColor.GREEN + "§c§m§l-----------------------------------------" );
            p.sendMessage( "" );
            p.sendMessage( "§e§o Game: §cSkyWars" );
            p.sendMessage( "" );
            //p.sendMessage("§e§o Current Map: §6" + SW.instance.get);
            p.sendMessage( "§e§o Currently Online: §a" + Bukkit.getServer().getOnlinePlayers().size() );
            p.sendMessage( "" );
            p.sendMessage( ChatColor.GREEN + "§c§m§l-----------------------------------------" );
            plugin.getGameManager().sendVoteMessage( p );
        }

        for ( Player players : Bukkit.getOnlinePlayers() )
        {
            players.setAllowFlight( false );
            players.setFlying( false );
        }
    }
}
