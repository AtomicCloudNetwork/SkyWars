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


    public SkyWarsMap()
    {
    }

    public SkyWarsMap(String name, SkyWarsLocation[] spawnLocations, List<SkyWarsChest>[] chests, String... authors)
    {
        this.name = name;
        this.spawnLocations = spawnLocations;
        this.chests = chests;
        this.authors = authors;
    }

    public void setMapFile(File mapFile)
    {
        this.mapFile = mapFile;
    }

    public void setAuthors(String[] authors)
    {
        this.authors = authors;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSpawnLocations(SkyWarsLocation[] spawnLocations)
    {
        this.spawnLocations = spawnLocations;
    }

    public void setChests(List<SkyWarsChest>[] chests)
    {
        this.chests = chests;
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

    public SkyWarsLocation[] getSpawnLocations()
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
