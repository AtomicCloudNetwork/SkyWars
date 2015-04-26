package net.atomiccloud.skywars;

import net.atomiccloud.skywars.commands.SkyWarsCommand;
import net.atomiccloud.skywars.commands.VoteCommand;
import net.atomiccloud.skywars.game.GameManager;
import net.atomiccloud.skywars.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyWarsPlugin extends JavaPlugin
{

    private static SkyWarsPlugin plugin;
    private String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "SkyWars" + ChatColor.GRAY + "] ";

    private GameManager gameManager;
    private Config config;

    @Override
    public void onEnable()
    {
        plugin = this;
        saveDefaultConfig();
        World world = getServer().getWorld( "Sw-World1" );
        config = new Config( this, world );
        gameManager = new GameManager( this );
        if ( world != null )
        {
            Bukkit.getServer().getWorld( "Sw-World1" ).setMonsterSpawnLimit( 0 );
            Bukkit.getServer().getWorld( "Sw-World1" ).setStorm( false );
        }
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
