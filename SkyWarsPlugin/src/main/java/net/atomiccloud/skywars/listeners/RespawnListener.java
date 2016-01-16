package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.util.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener implements Listener
{

    private SkyWarsPlugin plugin;

    public RespawnListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        event.setRespawnLocation( plugin.getGameManager().getPlayer( event.getPlayer() ).getSpawnLocation() );
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                event.getPlayer().getInventory().setItem( 0,
                        new ItemCreator( Material.GHAST_TEAR ).name( "&a&lTeleport Tear &7(Right Click)" ).toItemStack() );
            }
        }.runTaskLater( plugin, 20 );
    }
}
