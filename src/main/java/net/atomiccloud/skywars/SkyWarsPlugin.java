package net.atomiccloud.skywars;

import net.atomiccloud.skywars.commands.SkyWarsCommand;
import net.atomiccloud.skywars.commands.VoteCommand;
import net.atomiccloud.skywars.game.GameManager;
import net.atomiccloud.skywars.listeners.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.atomiccloud.skywars.util.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SkyWarsPlugin extends JavaPlugin
{

    private static SkyWarsPlugin plugin;
    public String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "SkyWars" + ChatColor.GRAY + "] ";

    private GameManager gameManager;
    private Config config;

    @Override
    public void onEnable()
    {
        // Move map copying to its own plugin.
        /*File source = new File( "/home/thedenmc_gmail_com/Sw-World1/" );

        File file = new File( "/home/thedenmc_gmail_com/SW-1/Sw-World1" );

        try
        {
            FileUtils.deleteDirectory( file );
            if ( file.mkdir() ) getLogger().info( "Created World folder!" );
            FileUtils.copyDirectory( source, file );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }*/
        plugin = this;
        saveDefaultConfig();
        config = new Config( this );
        gameManager = new GameManager( this );
        Bukkit.getServer().getWorld( "Sw-World1" ).setMonsterSpawnLimit( 0 );
        Bukkit.getServer().getWorld( "Sw-World1" ).setStorm( false );
        Bukkit.getMessenger().registerOutgoingPluginChannel( this, "BungeeCord" );
        getCommand( "vote" ).setExecutor( new VoteCommand( this ) );
        getCommand( "skywars" ).setExecutor( new SkyWarsCommand( this ) );
        registerListeners();
    }

    @Override
    public void onDisable()
    {
        plugin = null;
    }

    private void registerListeners()
    {
        for ( Listener listener : new Listener[]{ new JoinListener( this ), new BlockListener( this ),
                new DeathListener( this ), new DamageListener( this ), new MiscellaneousListener( this ),
                new LeaveListener( this ), new PingListener( this ) } )
        {
            getServer().getPluginManager().registerEvents( listener, this );
        }
    }

    public static SkyWarsPlugin getInstance()
    {
        return plugin;
    }

    public Config getConfiguration()
    {
        return config;
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }

    public String getPrefix()
    {
        return prefix;
    }
}
