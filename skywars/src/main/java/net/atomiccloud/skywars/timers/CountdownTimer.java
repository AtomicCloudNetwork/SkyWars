package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.common.SkyWarsLocation;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.Title;
import net.atomiccloud.skywars.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTimer extends Timer
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
        plugin.getGameManager().getPlayers().forEach( skyWarsPlayer -> skyWarsPlayer.getGameBoard().setScore( 10, "&a"
                + GameState.values()[ plugin.getGameManager().getGameState().ordinal() + 1 ].getName()
                + " " + Util.formatTime( plugin.getGameManager().getCurrentTimer().getCountdown(), true ) ) );
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
                for ( SkyWarsLocation location : plugin.getGameManager().getWinningMap().getSpawnLocations() )
                {
                    clearGlass( location.toBukkitLocation() );
                }
                plugin.getGameManager().populateChests();
                Bukkit.getOnlinePlayers().forEach( player -> {
                    player.setGameMode( GameMode.SURVIVAL );
                    player.addPotionEffect( new PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 10, false, false ) );
                } );
                plugin.getGameManager().setGameState( GameState.IN_GAME );
                break;
        }
        countdown--;
    }

    private void clearGlass(Location location)
    {
        int radius = 2;
        for ( double x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++ )
        {
            for ( double y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++ )
            {
                for ( double z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++ )
                {
                    Block block = location.getWorld().getBlockAt( ( int ) x, ( int ) y, ( int ) z );
                    if ( block.getType().equals( Material.GLASS ) )
                    {
                        block.setType( Material.AIR );
                    }
                }
            }
        }
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }
}
