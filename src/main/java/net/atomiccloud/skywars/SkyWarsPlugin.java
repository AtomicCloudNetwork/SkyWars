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

import java.io.File;
import java.io.IOException;

public class SkyWarsPlugin extends JavaPlugin
{

    private static SkyWarsPlugin plugin;
    public String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "SkyWars" + ChatColor.GRAY + "] ";

    private GameManager gameManager;


    @Override
    public void onEnable()
    {
        plugin = this;
        gameManager = new GameManager( this );

        File des = new File( "/home/thedenmc_gmail_com/Sw-World1" );

        File file = Bukkit.getWorld( "Sw-World1" ).getWorldFolder();

        Bukkit.getServer().getWorld( "Sw-World1" ).setMonsterSpawnLimit( 0 );
        Bukkit.getServer().getWorld( "Sw-World1" ).setStorm( false );

        try
        {
            FileUtils.copyDirectory( des, file );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        Bukkit.getMessenger().registerOutgoingPluginChannel( this, "BungeeCord" );
        saveDefaultConfig();
        getCommand( "vote" ).setExecutor( new VoteCommand( this ) );
        getCommand( "skywars" ).setExecutor( new SkyWarsCommand( this ) );
        registerListeners();
    }

    private void registerListeners()
    {
        for ( Listener listener : new Listener[]{ new JoinListener( this ), new PvpAndBlocksListener( this ),
                new DeathListener( this ), new DamageListener( this ), new MiscellaneousListener( this ),
                new LeaveListener( this ), new PingListener( this ) } )
        {
            getServer().getPluginManager().registerEvents( listener, this );
        }
    }


    @Override
    public void onDisable()
    {
        try
        {
            FileUtils.deleteDirectory( new File( "/home/thedenmc_gmail_com/SW-1/" +
                    gameManager.getWinningMap().toString() ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public static SkyWarsPlugin getInstance()
    {
        return plugin;
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
