package net.atomiccloud.skywars.game;

import java.io.File;
import java.security.SecureRandom;

public enum SkyWarsMap
{
    SKY_LANDS( "Sky Lands", new String[]{ "Victhetiger" }, new File( "/home/thedenmc_gmail_com/SkyLands" ) ),
    MINES( "Mines", new String[]{ "Victhetiger" }, new File( "/home/thedenmc_gmail_com/Mines" ) ),
    HELL( "Hell", new String[]{ "Victhetiger" }, new File( "/home/thedenmc_gmail_com/Hell" ) ),
    CAKE_LANDS( "Cake Lands", new String[]{ "Victhetiger" }, new File( "/home/thedenmc_gmail_com/CakeLands" ) ),
    LOVE_LANDS( "Love Lands", new String[]{ "PandemicDev" }, new File( "/home/thedenmc_gmail_com/LoveLands" ) ),
    JUNGLE_LANDS( "Jungle Lands", new String[]{ "austin1234575" }, new File( "/home/thedenmc_gmail_com/JungleLands" ) );

    String name;
    String[] authors;
    File mapFile;

    SkyWarsMap(String name, String[] authors, File mapFile)
    {
        this.name = name;
        this.authors = authors;
        this.mapFile = mapFile;
    }

    @Override
    public String toString()
    {
        return name.replace( " ", "" );
    }

    public String getName()
    {
        return name;
    }

    public String[] getAuthors()
    {
        return authors;
    }

    public File getMapFile()
    {
        return mapFile;
    }

    public static SkyWarsMap getRandom(SecureRandom random)
    {
        return values()[ random.nextInt( values().length ) ];
    }
}