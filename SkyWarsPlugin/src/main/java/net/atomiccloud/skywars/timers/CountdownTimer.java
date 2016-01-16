package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;
    private int countdown = 10;
    private Title title = new Title( null, "&ePrepare to fight!" );

    public CountdownTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {

        countdown--;

        switch ( countdown )
        {
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                title.setTitle( "&c" + countdown );
                Bukkit.getOnlinePlayers().forEach( title::send );
                break;
            case 0:
                plugin.getGameManager().populateChests();
                for ( Player player : Bukkit.getOnlinePlayers() )
                {
                    clearGlass( player.getLocation() );
                }
                plugin.getGameManager().setGameState( GameState.IN_GAME );
                break;
        }
    }

    private void clearGlass(Location location)
    {
        location.add( 0, -1, 0 ).getBlock().setType( Material.AIR );
        for ( int i = 0; i < 3; i++ )
        {
            location.add( -1, i, 0 ).getBlock().setType( Material.AIR );
            location.add( 0, i, -1 ).getBlock().setType( Material.AIR );
            location.add( 1, i, 0 ).getBlock().setType( Material.AIR );
            location.add( 0, i, -1 ).getBlock().setType( Material.AIR );
        }
    }
}
