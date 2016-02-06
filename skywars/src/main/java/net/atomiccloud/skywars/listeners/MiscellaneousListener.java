package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class MiscellaneousListener implements Listener
{

    private SkyWarsPlugin plugin;

    public MiscellaneousListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event)
    {
        if ( plugin.getGameManager().getPlayer(
                event.getPlayer() ).getTeam().equals( Team.SPECTATOR ) )
        {
            event.setAmount( 0 );
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event)
    {
        handleSpectator( event, event.getPlayer() );
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        handleSpectator( event, event.getPlayer() );
    }

    private void handleSpectator(Cancellable cancellable, Player player)
    {
        if ( plugin.getGameManager().getPlayer( player ).getTeam().equals( Team.SPECTATOR ) )
        {
            cancellable.setCancelled( true );
        }
    }

    @EventHandler
    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent event)
    {
        event.setCancelled( true );
    }

    @EventHandler
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event)
    {
        event.setCancelled( true );
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event)
    {
        if ( ( event instanceof Creature ) )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled( true );
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e)
    {
        e.setCancelled( true );
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) ||
                plugin.getGameManager().getPlayer( event.getEntity().getName() ).getTeam().equals( Team.SPECTATOR ) )
        {
            event.setCancelled( true );
        }
    }
}