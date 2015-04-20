package net.atomiccloud.skywars.listeners;


import net.DynamicJk.AtomicCore.Cosmites.Redis;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener
{

    private SkyWarsPlugin plugin;

    public DeathListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if ( e.getEntity().getLocation().getY() < 0 )
        {
            e.getEntity().teleport( plugin.getConfiguration().getSpawnLocation() );
        }
        if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
        {
            Player p = e.getEntity();
            p.setHealth( 20 );
            p.setGameMode( GameMode.SPECTATOR );
            plugin.getGameManager().getPlayersInGame().remove( e.getEntity().getName() );
            plugin.getGameManager().getSpectators().add( e.getEntity().getName() );
            if ( plugin.getGameManager().getPlayersInGame().size() == 1 )
            {
                Player winner = Bukkit.getPlayer( plugin.getGameManager().getPlayersInGame().get( 0 ) );
                Bukkit.broadcastMessage( plugin.getPrefix() + winner.getName() + " has won the game!" );
                plugin.getGameManager().setGameState( GameState.POST_GAME );
                Redis.addCoins( winner, 500 );
            } else
            {
                p.setHealth( 20 );
                p.setGameMode( GameMode.SPECTATOR );
                p.sendMessage( plugin.getPrefix() + "You have fallen!" );
                Bukkit.broadcastMessage( plugin.getPrefix() + p.getName() + " has fallen." );

                for ( String players : plugin.getGameManager().getPlayersInGame() )
                {
                    Player player = Bukkit.getPlayer( players );
                    Redis.addCoins( player, 20 );
                    player.getInventory().addItem( new ItemStack( Material.SPONGE ) );
                }
            }
        }
    }
}