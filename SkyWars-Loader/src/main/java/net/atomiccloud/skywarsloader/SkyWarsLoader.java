package net.atomiccloud.skywarsloader;

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
            if ( file.mkdir() ) getLogger().info( "Created World folder!" );
            FileUtils.copyDirectory( source, file );
        } catch ( IOException e )
        {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin( this );
        }
        getLogger().info( "Loaded SkyWars Map!" );
        getServer().getPluginManager().disablePlugin( this );
    }
}
