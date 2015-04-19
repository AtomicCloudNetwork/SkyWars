package net.atomiccloud.skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Config
{
    private Location spawnLocation;
    private Location gameLocation;

    public List<Location> spawnLocations = new ArrayList<>();
    public List<Location> deathMatchLocations = new ArrayList<>();

    private SkyWarsPlugin plugin;

    public Config(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
        World world = Bukkit.getServer().getWorld( "Sw-World1" );
        double x = plugin.getConfig().getDouble( "lobby.x" );
        double y = plugin.getConfig().getDouble( "lobby.y" );
        double z = plugin.getConfig().getDouble( "lobby.z" );
        spawnLocation = new Location( world, x, y, z );
        World w = Bukkit.getServer().getWorld(
                plugin.getConfig().getString( "game.world" ) );
        double x2 = plugin.getConfig().getDouble( "game.x" );
        double y2 = plugin.getConfig().getDouble( "game.y" );
        double z2 = plugin.getConfig().getDouble( "game.z" );
        gameLocation = new Location( w, x2, y2, z2 );
        plugin.getConfig().getStringList( "death-match-locs" ).forEach( s ->
                getDeathMatchLocations().add( locationFromString( s ) ));
    }

    public Location getSpawnLocation()
    {
        return spawnLocation;
    }

    public List<Location> getSpawnLocations()
    {
        return spawnLocations;
    }

    public Location getGameLocation()
    {
        return gameLocation;
    }

    public List<Location> getDeathMatchLocations()
    {
        return deathMatchLocations;
    }

    public void setSpawnLocations(String mapName)
    {
        plugin.getConfig().getStringList(
                mapName + "spawn-locs" ).forEach( s -> getSpawnLocations().add( locationFromString( s ) ) );
    }

    private Location locationFromString(String string)
    {
        String[] data = string.split( "," );
        return new Location(
                Bukkit.getWorld( data[ 0 ] ), Double.parseDouble( data[ 1 ] ),
                Double.parseDouble( data[ 2 ] ), Double.parseDouble( data[ 3 ] ),
                Float.parseFloat( data[ 4 ] ), Float.parseFloat( data[ 5 ] ) );
    }
}
