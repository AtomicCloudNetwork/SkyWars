package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.game.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
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
        switch ( countdown )
        {
            case 50:
                broadcastMessage();
                playNotePling();
                break;
            case 30:
                broadcastMessage();
                playNotePling();
                break;
            case 20:
                broadcastMessage();
                playNotePling();
                break;
            case 10:
                broadcastMessage();
                playNotePling();
                break;
            case 5:
                broadcastMessage();
                playNotePling();
                break;
            case 4:
                broadcastMessage();
                playNotePling();
                break;
            case 3:
                broadcastMessage();
                playNotePling();
                break;
            case 2:
                broadcastMessage();
                playNotePling();
                break;
            case 1:
                broadcastMessage();
                playNotePling();
                Bukkit.dispatchCommand( Bukkit.getConsoleSender(), "gamerule doTileDrops true" );
                break;
            case 0:
                Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes(
                        '&', "&7[&cSkyWars&7] &aGood Luck!" ) );
                playNotePling();
                handleGameStart();
                plugin.getGameManager().setGameState( GameState.IN_GAME );
                break;
        }
        countdown--;
    }

    private void broadcastMessage()
    {
        Bukkit.broadcastMessage( countdown == 1 ? "&7[&cSkyWars&7] &aGame starting in " + countdown + " second."
                : "&7[&cSkyWars&7] &aGame starting in " + countdown + " seconds." );
    }

    private void playNotePling()
    {
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
            plugin.getGameManager().setWinningMap( Maps.valueOf( mapName ) );
        } else
        {
            plugin.getGameManager().setWinningMap( Arrays.stream( Maps.values() ).filter( map ->
                    !map.equals( plugin.getGameManager().getMaps()[ 0 ] )
                            || !map.equals( plugin.getGameManager().getMaps()[ 1 ] ) ).findAny().get() );
        }
        Bukkit.broadcastMessage( plugin.getPrefix() + plugin.getGameManager().getWinningMap().getName() + " by "
                + plugin.getGameManager().getWinningMap().getAuthor() + " won voting!" );

        /*File file = new File( "/home/thedenmc_gmail_com/SW-1/" +
                plugin.getGameManager().getWinningMap().toString() );
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
        Bukkit.createWorld( new WorldCreator( plugin.getGameManager().getWinningMap().toString() ) );*/
        plugin.getConfig().getStringList(
                plugin.getGameManager().getWinningMap().toString() + "spawn-locs" ).forEach( this::locationFromString );
        //SET GAME SETTINGS
        //Teleportation to Arena!
        Player[] players = Bukkit.getOnlinePlayers().toArray(
                new Player[ Bukkit.getOnlinePlayers().size() ] );
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for ( int i = 0; i < Bukkit.getOnlinePlayers().size(); i++ )
        {
            Player player = players[ i ];
            player.setScoreboard( scoreboard );
            player.teleport( plugin.getGameManager().getSpawnLocations().get( i ) );
            player.addPotionEffect( new PotionEffect( PotionEffectType.DAMAGE_RESISTANCE, 20, 100 ) );
        }
        Bukkit.getWorld( "Sw-World1" ).getEntities().stream().filter( entity ->
                !( entity instanceof Player ) ).forEach( Entity::remove );
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
