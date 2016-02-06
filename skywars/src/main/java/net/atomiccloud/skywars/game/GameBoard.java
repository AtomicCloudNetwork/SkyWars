package net.atomiccloud.skywars.game;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class GameBoard
{
    private Scoreboard scoreboard;
    private Map<Integer, String> scores = new HashMap<>();

    public GameBoard(SkyWarsPlugin plugin, Player player)
    {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective sideBar = scoreboard.registerNewObjective( "sideBar", "dummy" );
        sideBar.setDisplaySlot( DisplaySlot.SIDEBAR );
        setScore( 1, "mc.atomic-cloud.net" );
        setScore( 2, "&0" );
        setScore( 3, "Mode: &aSolo" );
        setScore( 4, "Map: &a" + plugin.getGameManager().getWinningMap().getName() );
        setScore( 5, "&1" );
        setScore( 6, "Kills: &a0" );
        setScore( 7, "&2" );
        setScore( 8, "Players left: &a" + plugin.getGameManager().getPlayers().size() );
        setScore( 9, "&3" );
        setScore( 10, "&a" + GameState.values()[ plugin.getGameManager().getGameState().ordinal() ].getName()
                + plugin.getGameManager().getCurrentTimer().getCountdown() );
        setScore( 11, "Next Event:" );
        setScore( 12, "&4" );
        sideBar.setDisplayName( ChatColor.GOLD.toString() + ChatColor.BOLD + "SKYWARS" );
        Objective bellowName = scoreboard.registerNewObjective( "bellowName", "health" );
        bellowName.setDisplaySlot( DisplaySlot.BELOW_NAME );
        bellowName.setDisplayName( ChatColor.RED + "❤" );
        player.setScoreboard( scoreboard );
        Bukkit.getOnlinePlayers().forEach( online -> online.setHealth( online.getHealth() - 1 ) );
    }

    public void setScore(int position, String scoreName)
    {
        if ( scores.containsKey( position ) )
        {
            scoreboard.resetScores( scores.get( position ) );
        }

        String value = ChatColor.translateAlternateColorCodes( '&', scoreName );
        scores.put( position, value );
        scoreboard.getObjective( DisplaySlot.SIDEBAR ).getScore( value ).setScore( position );
    }
}
