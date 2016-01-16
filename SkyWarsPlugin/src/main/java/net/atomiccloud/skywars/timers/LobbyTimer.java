package net.atomiccloud.skywars.timers;

import me.Bogdacutu.VoidGenerator.VoidGeneratorGenerator;
import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.common.SkyWarsLocation;
import net.atomiccloud.skywars.common.SkyWarsMap;
import net.atomiccloud.skywars.game.GameBoard;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.FileUtil;
import net.atomiccloud.skywars.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class LobbyTimer extends Timer
{

    private SkyWarsPlugin plugin;

    private int countdown = 30;

    public LobbyTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        for ( Player player : Bukkit.getOnlinePlayers() ) player.setLevel( countdown );
        switch ( countdown )
        {
            case 30:
            case 20:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                broadcastMessage();
                break;
            case 0:
                Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes(
                        '&', "&eWhat goes up must come down!" ) );
                handleGameStart();
                plugin.getGameManager().setGameState( GameState.START_COUNTDOWN );
                break;
        }
        countdown--;
    }

    private void broadcastMessage()
    {
        Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes( '&',
                "&6Game starting in &c" + Util.formatTime( countdown, false ) + "&6." ) );
        for ( Player players : Bukkit.getOnlinePlayers() )
        {
            players.playSound( players.getLocation(), Sound.NOTE_PLING, 20, 20 );
        }
    }

    private void handleGameStart()
    {
        SkyWarsMap map = plugin.getGameManager().getVotes().entrySet().stream().max(
                Comparator.comparingInt( Map.Entry::getValue ) ).get().getKey();
        if ( !map.equals( plugin.getGameManager().getRandomMap() ) )
        {
            plugin.getGameManager().setWinningMap( map );
        } else
        {
            plugin.getGameManager().setWinningMap( plugin.getGameManager().getMaps().get( plugin.getGameManager().getRandom()
                    .nextInt( plugin.getGameManager().getMaps().size() ) ) );
        }
        Bukkit.broadcastMessage( ChatColor.YELLOW + map.getName() + " by " + map.getAuthorsAsString() + " won voting! Loading map..." );

        new FileUtil( plugin.getGameManager().getWinningMap().getMapFile() ).copyTo( new File( plugin.getServer().getWorldContainer(),
                plugin.getGameManager().getWinningMap().getName() ) );
        Bukkit.createWorld( new WorldCreator( plugin.getGameManager().getWinningMap().getName() ).generator( new VoidGeneratorGenerator() ) );
        Player[] players = Bukkit.getOnlinePlayers().toArray( new Player[ Bukkit.getOnlinePlayers().size() ] );
        List<SkyWarsLocation> spawnLocations = new ArrayList<>( Arrays.asList( plugin.getGameManager().getWinningMap().getSpawnLocations() ) );
        Collections.shuffle( spawnLocations, plugin.getGameManager().getRandom() );
        for ( int i = 0; i < Bukkit.getOnlinePlayers().size(); i++ )
        {
            Player player = players[ i ];
            SkyWarsPlayer skyWarsPlayer = plugin.getGameManager().getPlayer( player );
            skyWarsPlayer.setGameBoard( new GameBoard( plugin, player ) );
            SkyWarsLocation skyWarsLocation = spawnLocations.get( plugin.getGameManager().getRandom().nextInt( spawnLocations.size() ) );
            Location location = skyWarsLocation.toBukkitLocation();
            spawnLocations.remove( skyWarsLocation );
            skyWarsPlayer.setSpawnLocation( location );
            player.teleport( location );
        }
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }
}