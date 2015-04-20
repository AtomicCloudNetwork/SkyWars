package net.atomiccloud.skywars.game;

import java.io.File;
import java.security.SecureRandom;

public enum Maps
{
    SKY_LANDS( "Sky Lands", "Victhetiger", new File( "/home/thedenmc_gmail_com/SkyLands" ) ),
    MINES( "Mines", "Victhetiger", new File( "/home/thedenmc_gmail_com/Mines" ) ),
    HELL( "Hell", "Victhetiger", new File( "/home/thedenmc_gmail_com/Hell" ) ),
    CAKE_LANDS( "Cake Lands", "Victhetiger", new File( "/home/thedenmc_gmail_com/CakeLands" ) );

    String name;
    String author;
    File mapFile;

    Maps(String name, String author, File mapFile)
    {
        this.name = name;
        this.author = author;
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

    public String getAuthor()
    {
        return author;
    }

    public File getMapFile()
    {
        return mapFile;
    }

    public static Maps getRandom(SecureRandom random)
    {
        return values()[ random.nextInt( values().length ) ];
    }
}