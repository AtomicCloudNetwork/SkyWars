package net.atomiccloud.skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Config
{
    private Location spawnLocation;

    public List<Location> spawnLocations = new ArrayList<>();
    public List<Location> deathMatchLocations = new ArrayList<>();


    private SkyWarsPlugin plugin;

    public Config(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;

        spawnLocation = new Location( Bukkit.getWorld( "Sw-world1" ), -398.503D, 114.0D, -279.489D );
        plugin.getConfig().getStringList( "death-match-locs" ).forEach( s ->
                getDeathMatchLocations().add( locationFromString( s ) ) );
    }

    public Location getSpawnLocation()
    {
        return spawnLocation;
    }

    public List<Location> getSpawnLocations()
    {
        return spawnLocations;
    }

    public List<Location> getDeathMatchLocations()
    {
        return deathMatchLocations;
    }

    public void setSpawnLocations(String mapName)
    {
        plugin.getConfig().getStringList(
                mapName + "spawn-locs" ).forEach( s ->
                getSpawnLocations().add( locationFromString( s ) ) );
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
