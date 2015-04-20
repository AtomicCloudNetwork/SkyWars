package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener
{

    private SkyWarsPlugin plugin;


    public DamageListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if ( e.getEntity() instanceof Player )
        {
            Player player = (Player) e.getEntity();
            if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) ||
                    plugin.getGameManager().getSpectators().contains( player.getName() ) )
            {
                e.setCancelled( true );
            }

            if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) ||
                    plugin.getGameManager().getSpectators().contains( player.getName() ) )
            {
                if ( e.getCause().equals( EntityDamageEvent.DamageCause.VOID ) )
                {
                    e.getEntity().teleport( plugin.getConfiguration().getSpawnLocation() );
                }
            }
        }
    }

}