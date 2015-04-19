package net.atomiccloud.skywars.game;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.timers.GameTimer;
import net.atomiccloud.skywars.timers.LobbyTimer;
import net.atomiccloud.skywars.timers.RestartTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class GameManager
{
    private GameState gameState;

    private Location spawnLocation;

    public List<Location> spawnLocations = new ArrayList<>();

    private Scoreboard votesBoard;

    private Maps winningMap;

    private Maps[] maps = new Maps[ 2 ];

    private Map<String, Integer> votes = new HashMap<>();

    private Set<String> playersInGame = new HashSet<>();
    private Set<String> spectators = new HashSet<>();

    BukkitTask currentTask;

    private SkyWarsPlugin plugin;

    public GameManager(SkyWarsPlugin plugin)
    {
        gameState = GameState.PRE_GAME;
        this.plugin = plugin;
        maps[ 0 ] = Maps.getRandom();

        if ( maps[ 0 ].ordinal() == Maps.values().length - 1 )
        {
            maps[ 1 ] = Maps.values()[ 0 ];
        } else
        {
            maps[ 1 ] = Maps.values()[ maps[ 0 ].ordinal() + 1 ];
        }

        for ( Maps map : maps )
        {
            votes.put( map.name(), 0 );
        }
        votes.put( "Random", 0 );
        World world = Bukkit.getServer().getWorld( "Sw-World1" );
        double x = plugin.getConfig().getDouble( "lobby.x" );
        double y = plugin.getConfig().getDouble( "lobby.y" );
        double z = plugin.getConfig().getDouble( "lobby.z" );
        spawnLocation = new Location( world, x, y, z );
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        if ( this.gameState != gameState )
        {
            switch ( gameState )
            {
                case PRE_GAME:
                    if ( currentTask != null )
                    {
                        currentTask.cancel();
                    }
                    currentTask = new LobbyTimer( plugin ).runTaskTimer( plugin, 0, 20 );
                    break;
                case IN_GAME:
                    currentTask.cancel();
                    currentTask = new GameTimer( plugin ).runTaskTimer( plugin, 0, 20 );
                    break;
                case POST_GAME:
                    currentTask.cancel();
                    currentTask = new RestartTimer( plugin ).runTaskTimer( plugin, 0, 20 );
                    break;
            }
        }
        this.gameState = gameState;
    }

    public void sendVoteMessage(Player player)
    {
        player.sendMessage( ChatColor.GOLD + "Vote for a map with /vote #" );
        player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&6&l1. &6"
                + getMaps()[ 0 ].getName() +
                " (&b" + getVotes().get( getMaps()[ 0 ].name() ) + " votes&6)" ) );
        player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&6&l2. &6" +
                getMaps()[ 1 ].getName() + " (&b" +
                getVotes().get( getMaps()[ 1 ].name() ) + "votes&6)" ) );
        player.sendMessage( ChatColor.translateAlternateColorCodes( '&', "&6&l3. &b" +
                "Random" + " (&b" + getVotes().get( "Random" ) + " votes&6)" ) );
    }

    private void makeScoreboard()
    {
        votesBoard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = votesBoard.registerNewObjective( "votes", "votes1" );
        objective.setDisplayName( ChatColor.GREEN + "Votes" );
        objective.setDisplaySlot( DisplaySlot.SIDEBAR );

        objective.getScore( ChatColor.GRAY + getMaps()[ 0 ].getName() ).setScore( votes.get( getMaps()[ 0 ].name() ) );
        objective.getScore( ChatColor.GRAY + getMaps()[ 1 ].getName() ).setScore( votes.get( getMaps()[ 1 ].name() ) );
        objective.getScore( ChatColor.AQUA + "Random" ).setScore( votes.get( "Random" ) );
    }


    public void updateScoreboard(int i)
    {
        switch ( i )
        {
            case 1:
                votesBoard.getObjective( "votes" ).getScore( ChatColor.GRAY + getMaps()[ 0 ].getName() ).setScore(
                        votes.get( getMaps()[ 0 ].name() ) );
                break;
            case 2:
                votesBoard.getObjective( "votes" ).getScore( ChatColor.GRAY + getMaps()[ 1 ].getName() ).setScore(
                        votes.get( getMaps()[ 1 ].name() ) );
                break;
            case 3:
                votesBoard.getObjective( "votes" ).getScore( ChatColor.AQUA + "Random" ).setScore(
                        votes.get( "Random" ) );
                break;
        }
    }

    public Maps[] getMaps()
    {
        return maps;
    }

    public Scoreboard getVotesBoard()
    {
        return votesBoard;
    }

    public Map<String, Integer> getVotes()
    {
        return votes;
    }

    public List<Location> getSpawnLocations()
    {
        return spawnLocations;
    }

    public Set<String> getPlayersInGame()
    {
        return playersInGame;
    }

    public Maps getWinningMap()
    {
        return winningMap;
    }

    public void setWinningMap(Maps winningMap)
    {
        this.winningMap = winningMap;
    }

    public Set<String> getSpectators()
    {
        return spectators;
    }

    public Location getSpawnLocation()
    {
        return spawnLocation;
    }
}
