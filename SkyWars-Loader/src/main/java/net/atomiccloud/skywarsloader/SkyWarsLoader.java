package net.atomiccloud.skywarsloader;

import net.atomiccloud.skywars.game.SkyWarsMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SkyWarsLoader extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        File source = new File( "/home/thedenmc_gmail_com/Sw-World1/" );

        File file = new File( "/home/thedenmc_gmail_com/SW-1/Sw-World1" );

        try
        {
            FileUtils.deleteDirectory( file );
            for ( SkyWarsMap map : SkyWarsMap.values() )
            {
                File mapFile = map.getMapFile();
                if ( mapFile.exists() )
                {
                    FileUtils.deleteDirectory( mapFile );
                    break;
                }
            }
            FileUtils.copyDirectory( source, file );
            getServer().getPluginManager().disablePlugin( this );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
