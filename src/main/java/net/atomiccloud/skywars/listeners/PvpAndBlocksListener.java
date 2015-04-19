package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PvpAndBlocksListener implements Listener
{

    private SkyWarsPlugin plugin;

    public PvpAndBlocksListener(SkyWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME) )
        {
            e.setCancelled( true );
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME) )
        {
            e.setCancelled( true );
        }
    }

}
