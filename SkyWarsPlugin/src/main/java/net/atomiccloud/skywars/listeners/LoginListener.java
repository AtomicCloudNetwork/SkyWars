package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginListener implements Listener
{

    private SkyWarsPlugin plugin;

    public LoginListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            event.disallow( PlayerLoginEvent.Result.KICK_OTHER, "Game already started!" );
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.setFlying( false );
        player.setAllowFlight( false );
        player.setLevel( 0 );
        player.setHealth( 20 );
        player.setFoodLevel( 20 );
        player.setFireTicks( 0 );
        ItemStack air = new ItemStack( Material.AIR );
        player.getInventory().setArmorContents( new ItemStack[]{ air, air, air, air } );
        player.getInventory().clear();
        player.setExp( 0f );
        player.getActivePotionEffects().forEach( potionEffect -> player.removePotionEffect( potionEffect.getType() ) );
        if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) )
        {
            int players = Bukkit.getServer().getOnlinePlayers().size();
            if ( players >= 2 )
            {
                plugin.getGameManager().setGameState( GameState.LOBBY_COUNTDOWN );
            }
        }
        plugin.getGameManager().addPlayer( player );
        event.setJoinMessage( null );
        player.setGameMode( GameMode.ADVENTURE );
        player.setScoreboard( plugin.getGameManager().getVotesBoard() );
        player.sendMessage( "\n\n\n\n\n\n" );
        plugin.getGameManager().sendVoteMessage( player );
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for ( Player p : Bukkit.getOnlinePlayers() )
                    event.getPlayer().showPlayer( p );
                player.teleport( plugin.getConfiguration().getSpawnLocation() );
            }
        }.runTaskLater( plugin, 20 );
    }
}