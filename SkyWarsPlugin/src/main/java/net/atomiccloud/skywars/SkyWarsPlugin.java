package net.atomiccloud.skywars;

import com.google.gson.Gson;
import net.atomiccloud.skywars.commands.SkyWarsCommand;
import net.atomiccloud.skywars.commands.VoteCommand;
import net.atomiccloud.skywars.game.GameManager;
import net.atomiccloud.skywars.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SkyWarsPlugin extends JavaPlugin
{

    private static SkyWarsPlugin plugin;
    private Gson gson = new Gson();
    private String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "SkyWars" + ChatColor.GRAY + "] ";

    private GameManager gameManager;
    private Config config;

    @Override
    public void onEnable()
    {
        plugin = this;
        saveDefaultConfig();
        config = new Config( this );
        gameManager = new GameManager( this );
        handleMaps();
        config.getSpawnLocation().getWorld().setMonsterSpawnLimit( 0 );
        config.getSpawnLocation().getWorld().setStorm( false );
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
                new LeaveListener( this ), new PingListener( this ), new ExplosionListener( this ),
                new ChatListener( this ) } )
        {
            getServer().getPluginManager().registerEvents( listener, this );
        }
    }

    private void handleMaps()
    {
        File file = new File( getDataFolder().getPath() + File.separator + "maps" );
        if ( !file.exists() )
        {
            try
            {
                file.createNewFile();
            } catch ( IOException e )
            {
                e.printStackTrace();
            }
            return;
        }

        File[] files = file.listFiles();
        if ( files != null )
        {
            for ( File tempFile : files )
            {
                if ( tempFile.getName().endsWith( ".json" ) )
                {
                    try
                    {
                        SkyWarsMap map = gson.fromJson( new FileReader( tempFile ), SkyWarsMap.class );
                        getGameManager().getMapList().add( map );
                    } catch ( FileNotFoundException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
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
