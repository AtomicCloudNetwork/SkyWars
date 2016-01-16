package net.atomiccloud.skywars.common;

import com.google.common.base.Joiner;
import org.bukkit.Location;

import java.io.File;
import java.util.List;

public class SkyWarsMap
{
    private String name;
    private String[] authors;
    private SkyWarsLocation[] spawnLocations;
    private List<SkyWarsChest>[] chests;
    private File mapFile;
    private static Joiner joiner = Joiner.on( " & " );


    public SkyWarsMap(String name, SkyWarsLocation[] spawnLocations, List<SkyWarsChest>[] chests, String... authors)
    {
        this.name = name;
        this.spawnLocations = spawnLocations;
        this.chests = chests;
        this.authors = authors;
    }

    public String getName()
    {
        return name;
    }

    public String[] getAuthors()
    {
        return authors;
    }

    public String getAuthorsAsString()
    {
        return joiner.join( authors );
    }

    public Location[] getSpawnLocations()
    {
        return spawnLocations;
    }

    public List<SkyWarsChest>[] getChests()
    {
        return chests;
    }

    public File getMapFile()
    {
        return mapFile;
    }
}
