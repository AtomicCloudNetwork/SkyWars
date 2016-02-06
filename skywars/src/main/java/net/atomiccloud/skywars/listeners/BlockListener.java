package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{

    private SkyWarsPlugin plugin;

    public BlockListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME )
                || plugin.getGameManager().getPlayer( e.getPlayer().getName() ).getTeam().equals( Team.SPECTATOR ) )
        {
            e.setCancelled( true );
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME )
                || plugin.getGameManager().getPlayer( e.getPlayer().getName() ).getTeam().equals( Team.SPECTATOR ) )
        {
            e.setCancelled( true );
        }
    }
}