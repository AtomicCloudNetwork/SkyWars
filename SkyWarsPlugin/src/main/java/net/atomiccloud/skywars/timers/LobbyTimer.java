package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.ListBuilder;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

public class LobbyTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;

    private int countdown = 60;

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
            case 50:
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
                        '&', "&7[&cSkyWars&7] &aGood Luck!" ) );
                handleGameStart();
                plugin.getGameManager().setGameState( GameState.IN_GAME );
                break;
        }
        countdown--;
    }

    private void broadcastMessage()
    {
        Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes( '&',
                countdown == 1 ? "&7[&cSkyWars&7] &aGame starting in " + countdown + " second."
                        : "&7[&cSkyWars&7] &aGame starting in " + countdown + " seconds." ) );
        for ( Player players : Bukkit.getOnlinePlayers() )
        {
            players.playSound( players.getLocation(), Sound.NOTE_PLING, 20, 20 );
        }
    }

    private void handleGameStart()
    {
        String mapName = plugin.getGameManager().getVotes().entrySet().stream().max(
                Comparator.comparingInt( Map.Entry::getValue ) ).get().getKey();
        if ( !mapName.equals( "Random" ) )
        {
            plugin.getGameManager().setWinningMap( plugin.getGameManager().mapFromString( mapName ) );
        } else
        {
            plugin.getGameManager().setWinningMap(
                    plugin.getGameManager().getMapList().get(
                            plugin.getGameManager().getRandom().nextInt( plugin.getGameManager().getMapList().size()
                            ) ) );
        }
        Bukkit.broadcastMessage(
                plugin.getPrefix() + plugin.getGameManager().getWinningMap().getName() + " by "
                        + new ListBuilder<String>( plugin.getGameManager().getWinningMap().getAuthors(),
                        " & " ).toString() + " won voting!" );

        File file = new File( "/home/thedenmc_gmail_com/SW-1/" + plugin.getGameManager().getWinningMap() );

        try
        {
            FileUtils.copyDirectory( plugin.getGameManager().getWinningMap().getMapFile(), file );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
        Bukkit.createWorld( new WorldCreator( plugin.getGameManager().getWinningMap().toString() ) );
        plugin.getConfiguration().setSpawnLocations( plugin.getGameManager().getWinningMap().toString() );
        //SET GAME SETTINGS
        //Teleportation to Arena!
        Player[] players = Bukkit.getOnlinePlayers().toArray(
                new Player[ Bukkit.getOnlinePlayers().size() ] );
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for ( int i = 0; i < Bukkit.getOnlinePlayers().size(); i++ )
        {
            Player player = players[ i ];
            player.setScoreboard( scoreboard );
            player.teleport( plugin.getConfiguration().getSpawnLocations().get( i ) );
            player.getNearbyEntities( 6, 6, 6 ).stream().filter( entity ->
                    !( entity instanceof Player ) ).forEach( Entity::remove );
            player.addPotionEffect( new PotionEffect( PotionEffectType.DAMAGE_RESISTANCE, 40, 100 ) );
        }
    }
}