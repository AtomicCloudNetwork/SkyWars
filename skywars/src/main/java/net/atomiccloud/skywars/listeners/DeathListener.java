package net.atomiccloud.skywars.listeners;


import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.stream.Collectors;

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
        event.setDeathMessage( null );
        event.getDrops().forEach( drop -> event.getEntity().getLocation().getWorld().dropItem( event.getEntity().getLocation(), drop ) );
        event.getDrops().clear();
        Bukkit.broadcastMessage( handleDeathMessage( event.getEntity() ) );
        if ( plugin.getGameManager().getGameState().isPvp() )
        {
            if ( plugin.getGameManager().getDamageCache().containsKey( event.getEntity().getUniqueId() ) )
            {
                Player killer = Bukkit.getPlayer( plugin.getGameManager().getDamageCache().get( event.getEntity().getUniqueId() ) );
                if ( killer != null )
                {
                    SkyWarsPlayer player = plugin.getGameManager().getPlayer( killer );
                    player.incrKills();
                    player.getGameBoard().setScore( 6, "Kills: &a" + player.getKills() );
                }
            }
            Bukkit.getOnlinePlayers().forEach( player -> player.playSound( player.getLocation(), Sound.GHAST_DEATH, 1.0f, 1.0f ) );
            plugin.getGameManager().getPlayer( event.getEntity().getName() ).setTeam( Team.SPECTATOR );
            plugin.getGameManager().populateSpectatorInventory();
            List<SkyWarsPlayer> remainingPlayers = plugin.getGameManager().getPlayers().stream().filter(
                    skyWarsPlayer -> skyWarsPlayer.getTeam().equals( Team.PLAYER ) ).collect( Collectors.toList() );
            plugin.getGameManager().getPlayers().forEach( skyWarsPlayer ->
                    skyWarsPlayer.getGameBoard().setScore( 8, "Players left: &a" + remainingPlayers.size() ) );
            if ( remainingPlayers.size() == 1 )
            {
                Player winner = Bukkit.getPlayer( remainingPlayers.get( 0 ).getPlayerUuid() );
                Bukkit.broadcastMessage( plugin.getPrefix() + winner.getName() + " has won the game!" );
                Title title = new Title( ChatColor.AQUA + winner.getName(), "&3has won the game!" );
                Bukkit.getOnlinePlayers().forEach( title::send );
                plugin.getGameManager().setGameState( GameState.POST_GAME );
                Bukkit.getOnlinePlayers().forEach( player -> player.playSound( player.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0f, 1.0f ) );
                //Redis.addCoins( winner, 500 );
            }
        }
        event.getEntity().spigot().respawn();
    }

    private String handleDeathMessage(Player player)
    {
        String message = null;
        Player killer = null;
        if ( plugin.getGameManager().getDamageCache().containsKey( player.getUniqueId() ) )
        {
            Player temp = Bukkit.getPlayer( plugin.getGameManager().getDamageCache().get( player.getUniqueId() ) );
            if ( temp != null )
            {
                killer = temp;
            }
        }
        switch ( player.getLastDamageCause().getCause() )
        {
            case CONTACT:
                message = "%s was destroyed by a cactus.";
                break;
            case ENTITY_ATTACK:
                if ( killer != null )
                    message = "%s was killed by %s.";
                else message = "%s was killed by an entity!";
                break;
            case PROJECTILE:
                if ( killer != null )
                    message = "%s was shot to death by %s.";
                else message = "%s was shot to death!";
                break;
            case FALL:
                if ( killer != null)
                    message = "%s was thrown off a cliff by %s.!";
                else message = "%s fell to their death!";
                break;
            case FIRE:
                if ( killer != null)
                    message = "%s burned to their death while fighting %s!";
                else message = "%s burned to their death!";
                break;
            case FIRE_TICK:
                if ( killer != null)
                    message = "%s burned to their death while fighting %s!";
                else message = "%s burned to their death!";
                break;
            case LAVA:
                if ( killer != null )
                    message = "%s tried to swim in lava while being attacked by %s.";
                else message = "%s tried to swim in lava.";
                break;
            case ENTITY_EXPLOSION:
                message = "%s exploded!";
                break;
            case VOID:
                if ( killer != null )
                    message = "%s was thrown into the void by %s.";
                else message = "%s fell into the void.";
                break;
            case FALLING_BLOCK:
                message = "%s was killed by a falling anvil!";
                break;
            case STARVATION:
                message = "%s was starved to death.";
                break;
        }
        if ( message != null )
        {
            return ChatColor.translateAlternateColorCodes( '&',
                    ChatColor.AQUA + String.format( message, player.getName() + ChatColor.GRAY,
                            killer != null ? ChatColor.AQUA + killer.getName() + ChatColor.GRAY : "" ) );
        }

        return null;
    }
}