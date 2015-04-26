package net.atomiccloud.skywars.listeners;


import net.DynamicJk.AtomicCore.Cosmites.Redis;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        event.setDeathMessage( handleDeathMessage( event.getEntity() ) );
        if ( event.getEntity().getLocation().getY() < 0 )
        {
            event.getEntity().teleport( plugin.getConfiguration().getSpawnLocation() );
        }
        if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) ||
                plugin.getGameManager().getGameState().equals( GameState.DEATH_MATCH ) )
        {
            Player p = event.getEntity();
            p.setHealth( 20 );
            p.setGameMode( GameMode.SPECTATOR );
            plugin.getGameManager().getPlayersInGame().remove( event.getEntity().getName() );
            plugin.getGameManager().getSpectators().add( event.getEntity().getName() );
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
                // Bukkit.broadcastMessage( plugin.getPrefix() + p.getName() + " has fallen." );

                for ( String players : plugin.getGameManager().getPlayersInGame() )
                {
                    Player player = Bukkit.getPlayer( players );
                    Redis.addCoins( player, 20 );
                    player.getInventory().addItem( new ItemStack( Material.SPONGE ) );
                }
            }
        }
    }

    private String handleDeathMessage(Player player)
    {
        String message = null;
        switch ( player.getLastDamageCause().getCause() )
        {
            case CONTACT:
                message = "%s was destroyed by a cactus.";
                break;
            case ENTITY_ATTACK:
                if ( player.getKiller() != null )
                    message = "%s was killed by %s.";
                else message = "%s was killed by an entity!";
                break;
            case PROJECTILE:
                message = "%s was shot to death!";
                break;
            case FALL:
                message = "%s fell to their death!";
                break;
            case FIRE:
                message = "%s burned to their death!";
                break;
            case FIRE_TICK:
                message = "%s burned to their death!";
                break;
            case LAVA:
                message = "%s tried to swim in lava.";
                break;
            case ENTITY_EXPLOSION:
                message = "%s exploded!";
                break;
            case VOID:
                message = "%s fell into the void.";
                break;
            case FALLING_BLOCK:
                message = "%s was killed by falling anvils!";
                break;
            case STARVATION:
                message = "%s was starved to death.";
                break;
        }
        if ( message != null )
        {
            return ChatColor.translateAlternateColorCodes( '&',
                    plugin.getPrefix() + String.format( message, player.getName(),
                            player.getKiller() != null ? player.getKiller().getName() : "" ) );
        }

        return null;
    }
}