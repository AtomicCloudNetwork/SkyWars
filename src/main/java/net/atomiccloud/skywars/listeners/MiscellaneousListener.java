package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class MiscellaneousListener implements Listener
{

    private SkyWarsPlugin plugin;

    public MiscellaneousListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e)
    {
        if ( e.getEntity() instanceof ItemFrame )
        {
            if ( e.getRemover() instanceof Player )
            {
                Player p = (Player) e.getRemover();
                if ( p.isOp() )
                {
                    e.setCancelled( false );
                } else
                {
                    e.setCancelled( true );
                }
            }
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e)
    {
        e.setCancelled( true );
    }

    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
        {
            event.setCancelled( true );
        }
    }
}
