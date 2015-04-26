package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public void onEntityDamage(EntityDamageEvent event)
    {
        if ( event.getEntity() instanceof Player )
        {
            Player player = (Player) event.getEntity();
            if ( ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) &&
                    !plugin.getGameManager().getGameState().equals( GameState.DEATH_MATCH ) ) ||
                    plugin.getGameManager().getSpectators().contains( player.getName() ) )
            {
                event.setCancelled( true );
            }

            if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) ||
                    plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) ||
                    plugin.getGameManager().getSpectators().contains( player.getName() ) )
            {
                if ( event.getCause().equals( EntityDamageEvent.DamageCause.VOID ) )
                {
                    event.getEntity().teleport( plugin.getConfiguration().getSpawnLocation() );
                }
            }
        }
    }
}