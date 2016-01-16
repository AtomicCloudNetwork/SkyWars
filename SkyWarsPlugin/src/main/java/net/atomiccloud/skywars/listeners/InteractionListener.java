package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener
{

    private SkyWarsPlugin plugin;

    public InteractionListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if ( plugin.getGameManager().getPlayer(
                event.getPlayer() ).getTeam().equals( Team.SPECTATOR ) )
        {
            if ( event.getAction().equals( Action.PHYSICAL ) )
            {
                event.setCancelled( true );
            } else if ( event.hasBlock() )
            {
                switch ( event.getClickedBlock().getType() )
                {
                    case BEACON:
                    case WORKBENCH:
                    case ENCHANTMENT_TABLE:
                    case CHEST:
                    case FIREBALL:
                    case BIRCH_DOOR:
                    case TRAP_DOOR:
                    case JUNGLE_DOOR:
                    case SPRUCE_DOOR:
                    case DARK_OAK_DOOR:
                    case WOODEN_DOOR:
                    case ACACIA_DOOR:
                    case STONE_BUTTON:
                    case WOOD_BUTTON:
                    case LEVER:
                        event.setCancelled( true );
                        break;
                }
            }
            if ( event.hasItem()
                    && ( event.getAction().equals( Action.RIGHT_CLICK_AIR ) || event.getAction().equals( Action.RIGHT_CLICK_BLOCK ) )
                    && event.getItem().getType().equals( Material.GHAST_TEAR ) )
            {
                event.getPlayer().openInventory( plugin.getGameManager().getSpectatorInventory() );
            }
        }

    }
}
