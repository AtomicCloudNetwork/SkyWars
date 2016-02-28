package net.atomiccloud.skywars.timers;

import me.Bogdacutu.VoidGenerator.VoidGeneratorGenerator;
import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.ActionBar;
import net.atomiccloud.skywars.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.stream.Collectors;

public class GameTimer extends Timer
{

    private SkyWarsPlugin plugin;
    private int countdown = 600;

    public GameTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        new ActionBar( "&6Time left: &c" + Util.formatTime( countdown, true ) ).broadcast();
        plugin.getGameManager().getPlayers().forEach( skyWarsPlayer -> skyWarsPlayer.getGameBoard().setScore( 10, "&a"
                + GameState.values()[ plugin.getGameManager().getGameState().ordinal() + 1 ].getName()
                + " " + Util.formatTime( plugin.getGameManager().getCurrentTimer().getCountdown(), true ) ) );
        switch ( countdown )
        {
            case 500:
            case 400:
            case 300:
            case 200:
            case 100:
            case 50:
            case 20:
            case 10:
            case 3:
            case 2:
            case 1:
                Bukkit.broadcastMessage( ChatColor.YELLOW + "Game is ending in " + ChatColor.RED + Util.formatTime( countdown, false ) );
                break;
            case 0:
                World world = Bukkit.createWorld( new WorldCreator( plugin.getConfiguration()
                        .getDeathMatchLocations()[ 0 ].getWorldName() ).generator( new VoidGeneratorGenerator() ) );
                plugin.getGameManager().setGameState( GameState.DEATH_MATCH );
                List<SkyWarsPlayer> players = plugin.getGameManager().getPlayers().stream().filter( skyWarsPlayer ->
                        skyWarsPlayer.getTeam().equals( Team.PLAYER ) ).collect( Collectors.toList() );
                for ( int i = 0; i < players.size(); i++ )
                {
                    Player player = Bukkit.getPlayer( players.get( i ).getPlayerUuid() );
                    player.addPotionEffect( new PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 10, false, false ) );
                    player.teleport( plugin.getConfiguration().getDeathMatchLocations()[ i ].toBukkitLocation() );
                }
                plugin.getGameManager().getPlayers().stream().filter( skyWarsPlayer ->
                        skyWarsPlayer.getTeam().equals( Team.SPECTATOR ) )
                        .forEach( skyWarsPlayer -> Bukkit.getPlayer( skyWarsPlayer.getPlayerUuid() ).teleport( new Location( world, 0, 80, 0 ) ) );
                break;
        }
        countdown--;
    }

    @Override
    public int getCountdown()
    {
        return countdown;
    }
}