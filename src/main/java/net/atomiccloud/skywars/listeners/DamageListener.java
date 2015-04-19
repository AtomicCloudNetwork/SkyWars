package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener
{

    private SkyWarsPlugin plugin;

    private Location loc;

    public DamageListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
        World w = Bukkit.getServer().getWorld(
                plugin.getConfig().getString( "game" + ".world" ) );
        double x = plugin.getConfig().getDouble( "game.x" );
        double y = plugin.getConfig().getDouble( "game.y" );
        double z = plugin.getConfig().getDouble( "game.z" );
        loc = new Location( w, x, y, z );
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if ( e.getEntity() instanceof Player )
        {
            if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) ||
                    plugin.getGameManager().getSpectators().contains( e.getEntity().getName() ) )
            {
                e.setCancelled( true );
            }

            if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) ||
                    plugin.getGameManager().getSpectators().contains( e.getEntity().getName() ) )
            {
                if ( e.getCause().equals( EntityDamageEvent.DamageCause.VOID ) )
                {
                    e.getEntity().teleport( loc );
                }
            }
        }
    }

}
