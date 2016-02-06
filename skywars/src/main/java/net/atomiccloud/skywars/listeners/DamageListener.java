package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener
{

    private SkyWarsPlugin plugin;


    public DamageListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if ( event.getEntity() instanceof Wither )
        {
            event.setCancelled( true );
            return;
        }
        if ( event.getEntity() instanceof Player )
        {
            Player player = ( Player ) event.getEntity();
            boolean isSpectator = plugin.getGameManager().getPlayer( player.getName() ).getTeam().equals( Team.SPECTATOR );
            if ( !plugin.getGameManager().getGameState().isPvp() || isSpectator )
            {
                event.setCancelled( true );
            }

            if ( event.getCause().equals( EntityDamageEvent.DamageCause.VOID ) )
            {
                if ( isSpectator )
                {
                    player.teleport( plugin.getConfiguration().getSpawnLocation() );
                } else if ( plugin.getGameManager().getGameState().isPvp() )
                {
                    player.damage( 20.0D );
                }
            }
        }
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if ( event.getEntity() instanceof Player )
        {
            if ( event.getDamager() instanceof Player )
            {
                plugin.getGameManager().getDamageCache().put( event.getEntity().getUniqueId(), event.getDamager().getUniqueId() );
                SkyWarsPlayer player = plugin.getGameManager().getPlayer( event.getEntity().getName() );
                SkyWarsPlayer damager = plugin.getGameManager().getPlayer( event.getDamager().getName() );
                //player.getGameBoard().updateHealth();
                if ( player.getTeam().equals( Team.SPECTATOR )
                        || damager.getTeam().equals( Team.SPECTATOR ) )
                {
                    event.setCancelled( true );
                }
            } else if ( event.getDamager() instanceof Projectile )
            {
                Projectile projectile = ( Projectile ) event.getDamager();
                if ( projectile.getShooter() != null
                        && projectile.getShooter() instanceof Player )
                {
                    plugin.getGameManager().getDamageCache().put( event.getEntity().getUniqueId(),
                            ( ( Player ) projectile.getShooter() ).getUniqueId() );
                }
            }
        }
    }
}