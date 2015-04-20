package net.atomiccloud.skywars;

import net.atomiccloud.skywars.util.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Config
{
    private Location spawnLocation;

    public List<Location> spawnLocations = new ArrayList<>();
    public List<Location> deathMatchLocations = new ArrayList<>();

    private SkyWarsPlugin plugin;

    public Config(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
        ( (BukkitRunnable) () -> {
            World world = Bukkit.getServer().getWorld( "Sw-World1" );
            double x = plugin.getConfig().getDouble( "lobby.x" );
            double y = plugin.getConfig().getDouble( "lobby.y" );
            double z = plugin.getConfig().getDouble( "lobby.z" );
            spawnLocation = new Location( world, x, y, z );
            plugin.getConfig().getStringList( "death-match-locs" ).forEach( s ->
                    getDeathMatchLocations().add( locationFromString( s ) ) );
        } ).runAfter( 2, TimeUnit.SECONDS );
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