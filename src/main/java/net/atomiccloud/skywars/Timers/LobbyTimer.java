package net.atomiccloud.skywars.Timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.game.Maps;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class LobbyTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;

    private int countdown;

    public LobbyTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        switch ( countdown )
        {

            case 50:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 50 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 30:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 30 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 20:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 20 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 10:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 10 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 5:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 5 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 4:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 4 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 3:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 3 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                break;
            case 2:
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 2 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }

                break;
            case 1:
                Bukkit.broadcastMessage( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" );
                Bukkit.broadcastMessage( ChatColor.GRAY + "["
                        + ChatColor.RED + "SkyWars"
                        + ChatColor.GRAY + "]"
                        + ChatColor.GREEN
                        + " Game starting in 1 seconds." );
                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                }
                Bukkit.dispatchCommand( Bukkit.getConsoleSender(), "gamerule doTileDrops true" );
                break;
            case 0:
                Bukkit.broadcastMessage( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" );
                Bukkit.broadcastMessage( ChatColor.GRAY
                        + "["
                        + ChatColor.RED
                        + "SkyWars"
                        + ChatColor.GRAY
                        + "]"
                        + ChatColor.GREEN
                        + " Good Luck" );

                for ( Player players : Bukkit.getServer()
                        .getOnlinePlayers() )
                {
                    players.playSound( players.getLocation(),
                            Sound.NOTE_PLING, 20, 20 );
                    players.addPotionEffect( new PotionEffect( PotionEffectType.DAMAGE_RESISTANCE, 20, 100 ) );
                }

                handleGameStart();
                plugin.getGameManager().setGameState( GameState.IN_GAME );
                break;
        }
    }

    private void handleGameStart()
    {
        String mapName = plugin.getGameManager().getVotes().entrySet().stream().max(
                Comparator.comparingInt( Map.Entry::getValue ) ).get().getKey();
        if ( !mapName.equals( "Random" ) )
        {
            plugin.getGameManager().setWinningMap( Maps.valueOf( mapName ) );
        } else
        {
            plugin.getGameManager().setWinningMap( Arrays.stream( Maps.values() ).filter( map ->
                    !map.equals( plugin.getGameManager().getMaps()[ 0 ] )
                            || !map.equals( plugin.getGameManager().getMaps()[ 1 ] ) ).findAny().get() );
        }
        Bukkit.broadcastMessage( plugin.getGameManager().getWinningMap().getName() + " won voting!" );

        File file = new File( "/home/thedenmc_gmail_com/SW-1/" + plugin.getGameManager().getWinningMap()
                .toString() );
        try
        {
            if ( file.mkdir() )
            {
                FileUtils.copyDirectory( new File( "/home/thedenmc_gmail_com/" + plugin.getGameManager()
                        .getWinningMap().toString() ), file );

            }
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
        Bukkit.createWorld( new WorldCreator( plugin.getGameManager().getWinningMap().toString() ) );
        plugin.getConfig().getStringList( plugin.getGameManager().getWinningMap().toString() +
                "spawn-locs" )
                .forEach( this::locationFromString );
        //SET GAME SETTINGS
        //Teleportation to Arena!
        Player[] players = Bukkit.getOnlinePlayers().toArray( new Player[ Bukkit.getOnlinePlayers()
                .size() ] );
        for ( int i = 0; i < Bukkit.getOnlinePlayers().size(); i++ )
        {
            Player player = players[ i ];
            player.setScoreboard( Bukkit.getScoreboardManager().getNewScoreboard() );
            player.teleport( plugin.getGameManager().getSpawnLocations().get( i ) );

            Bukkit.getServer().getWorld( plugin.getGameManager().getWinningMap().toString() ).setDifficulty(
                    Difficulty.PEACEFUL );
            Bukkit.getServer().getWorld( plugin.getGameManager().getWinningMap().toString() ).setDifficulty(
                    Difficulty.HARD );
        }
    }

    private void locationFromString(String string)
    {
        String[] data = string.split( "," );
        plugin.getGameManager().getSpawnLocations().add( new Location( Bukkit.getWorld( data[ 0 ] ), Double
                .parseDouble( data[ 1 ] ),
                Double.parseDouble( data[ 2 ] ), Double.parseDouble( data[ 3 ] ),
                Float.parseFloat( data[ 4 ] ), Float.parseFloat( data[ 5 ] ) ) );
    }
}
