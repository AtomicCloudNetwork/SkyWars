package net.atomiccloud.skywars;

import net.atomiccloud.skywars.game.GameBoard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkyWarsPlayer
{

    private String name;

    private Location spawnLocation;
    private UUID playerUuid;
    private Team team;
    private int kills = 0;
    private GameBoard gameBoard;

    public SkyWarsPlayer(String name, UUID playerUuid, Team team)
    {
        this.name = name;
        this.playerUuid = playerUuid;
        this.team = team;
    }

    public void setSpawnLocation(Location spawnLocation)
    {
        this.spawnLocation = spawnLocation;
    }

    public Location getSpawnLocation()
    {
        return spawnLocation;
    }

    public void setGameBoard(GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
    }

    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    public void incrKills()
    {
        kills++;
    }

    public int getKills()
    {
        return kills;
    }

    public String getName()
    {
        return name;
    }

    public UUID getPlayerUuid()
    {
        return playerUuid;
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam(Team team)
    {
        this.team = team;
        if ( team.equals( Team.SPECTATOR ) )
        {
            Player player = Bukkit.getPlayer( playerUuid );
            player.setAllowFlight( true );
            player.setFlying( true );
            player.spigot().setCollidesWithEntities( false );
            Bukkit.getOnlinePlayers().stream()
                    .filter( tempPlayer -> !tempPlayer.equals( player ) )
                    .forEach( tempPlayer -> tempPlayer.hidePlayer( player ) );
            player.updateInventory();
        }
    }
}
