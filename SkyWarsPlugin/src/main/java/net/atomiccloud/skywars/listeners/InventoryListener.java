package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryListener implements Listener
{

    private SkyWarsPlugin plugin;
    private static final String INVENTORY_TITLE = "Teleporter";

    public InventoryListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if ( event.getInventory().getType().equals( InventoryType.ENCHANTING )
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType().equals( Material.LAPIS_ORE ) )
        {
            event.setCancelled( true );
            return;
        }

        if ( event.getInventory().getName().equals( INVENTORY_TITLE )
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType().equals( Material.SKULL_ITEM )
                && event.getCurrentItem().hasItemMeta() )
        {
            SkullMeta itemMeta = ( SkullMeta ) event.getCurrentItem().getItemMeta();
            if ( itemMeta.hasOwner() )
            {
                event.getWhoClicked().teleport( Bukkit.getPlayer( itemMeta.getOwner() ).getLocation() );
            }
        }
        if ( plugin.getGameManager().getPlayer( ( ( Player ) event.getWhoClicked() ) ).getTeam().equals( Team.SPECTATOR ) )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event)
    {
        if ( event.getInventory() instanceof EnchantingInventory )
        {
            event.setCancelled( true );
            EnchantingInventory inventory = ( EnchantingInventory ) event.getInventory();
            inventory.setSecondary( new ItemStack( Material.INK_SACK, 3, ( short ) 4 ) );
        }
        if ( event.getInventory().getName().equals( INVENTORY_TITLE ) )
        {
            plugin.getGameManager().getCurrentViewers().add( event.getPlayer().getUniqueId() );
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        if ( event.getInventory() instanceof EnchantingInventory )
            ( ( EnchantingInventory ) event.getInventory() ).setSecondary( new ItemStack( Material.AIR ) );
        if ( event.getInventory().getName().equals( INVENTORY_TITLE ) )
        {
            plugin.getGameManager().getCurrentViewers().add( event.getPlayer().getUniqueId() );
        }
    }
}
