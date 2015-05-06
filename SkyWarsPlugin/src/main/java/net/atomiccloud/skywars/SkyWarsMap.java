package net.atomiccloud.skywars;

import org.bukkit.Location;

import java.io.File;
import java.util.List;

public class SkyWarsMap
{
    private String name;
    private List<String> authors;
    private File mapFile;
    private List<Location> spawnLocations;

    public SkyWarsMap(String name, List<String> authors, File mapFile, List<Location> spawnLocations)
    {
        this.name = name;
        this.authors = authors;
        this.mapFile = mapFile;
        this.spawnLocations = spawnLocations;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getAuthors()
    {
        return authors;
    }

    public File getMapFile()
    {
        return mapFile;
    }

    public List<Location> getSpawnLocations()
    {
        return spawnLocations;
    }

    public void addSpawnLocations(Location locations)
    {
        spawnLocations.add( locations );
    }
}
