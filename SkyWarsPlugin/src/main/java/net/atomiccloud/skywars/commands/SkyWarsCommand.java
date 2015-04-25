package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsCommand implements CommandExecutor
{

    private SkyWarsPlugin plugin;

    public SkyWarsCommand(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if ( !( sender instanceof Player ) )
        {
            return true;
        }
        Player p = (Player) sender;

        if ( args.length == 0 )
        {
            p.sendMessage( "Please Specify an action!" );
            plugin.getConfig().set( "World", p.getWorld().getName() );
            return true;
        }

        if ( args[ 0 ].equalsIgnoreCase( "forcedeathmatch" ) )
        {
            if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
            {
                plugin.getGameManager().setGameState( GameState.DEATH_MATCH );
            } else
            {
                p.sendMessage( ChatColor.RED + "nope.avi" );
            }
        }
        if ( args[ 0 ].equalsIgnoreCase( "setspawn" ) )
        {
            if ( args.length == 2 )
            {
                List<String> strings = plugin.getConfig().getStringList( args[ 1 ] + "spawn-locs" ) != null
                        ? plugin.getConfig().getStringList( args[ 1 ] + "spawn-locs" ) : new ArrayList<>();
                strings.add( p.getLocation().getWorld().getName() + "," +
                        String.valueOf( p.getLocation().getX() ) + "," +
                        String.valueOf( p.getLocation().getY() ) + "," +
                        String.valueOf( p.getLocation().getZ() ) + "," +
                        String.valueOf( p.getLocation().getYaw() ) + "," +
                        String.valueOf( p.getLocation().getPitch() ) );
                plugin.getConfig().set( args[ 1 ] + "spawn-locs", strings );
                plugin.getConfiguration().getSpawnLocations().add( p.getLocation() );
                plugin.saveConfig();
                p.sendMessage( ChatColor.RED + "Spawn has been set!" );
            } else
            {
                sender.sendMessage( ChatColor.RED + "Usage: /skywars setspawn <mapname>" );
            }
        }
        if ( args[ 0 ].equalsIgnoreCase( "setLobby" ) )
        {
            plugin.getConfig().set( "lobby.world", p.getLocation().getWorld().getName() );
            plugin.getConfig().set( "lobby.x", p.getLocation().getX() );
            plugin.getConfig().set( "lobby.y", p.getLocation().getY() );
            plugin.getConfig().set( "lobby.z", p.getLocation().getZ() );
            plugin.saveConfig();
            p.sendMessage( ChatColor.RED + "Lobby has been set!" );
        }
        if ( args[ 0 ].equalsIgnoreCase( "settier1npc" ) )
        {
            plugin.getConfig().set( "tier1.world", p.getLocation().getWorld().getName() );
            plugin.getConfig().set( "tier1.x", p.getLocation().getX() );
            plugin.getConfig().set( "tier1.y", p.getLocation().getY() );
            plugin.getConfig().set( "tier1.z", p.getLocation().getZ() );
            plugin.saveConfig();
            p.sendMessage( ChatColor.RED + "NPC spawn has been added!" );
        }
        if ( args[ 0 ].equalsIgnoreCase( "setdeathspawn" ) )
        {
            List<String> strings = plugin.getConfig().getStringList( "death-match-locs" ) != null
                    ? plugin.getConfig().getStringList( "death-match-locs" ) : new ArrayList<>();
            strings.add( p.getLocation().getWorld().getName() + "," +
                    String.valueOf( p.getLocation().getX() ) + "," +
                    String.valueOf( p.getLocation().getY() ) + "," +
                    String.valueOf( p.getLocation().getZ() ) + "," +
                    String.valueOf( p.getLocation().getYaw() ) + "," +
                    String.valueOf( p.getLocation().getPitch() ) );
            plugin.getConfig().set( "death-match-locs", strings );
            plugin.getConfiguration().getSpawnLocations().add( p.getLocation() );
            plugin.saveConfig();
            p.sendMessage( ChatColor.RED + "Death Spawn has been added!" );
        }
        if ( args[ 0 ].equalsIgnoreCase( "setspecspawn" ) )
        {
            if ( args.length == 2 )
            {
                plugin.getConfig().set( args[ 1 ] + "specspawn.world", p.getLocation().getWorld().getName() );
                plugin.getConfig().set( args[ 1 ] + "specspawn.game.x", p.getLocation().getX() );
                plugin.getConfig().set( args[ 1 ] + "specspawn.game.y", p.getLocation().getY() );
                plugin.getConfig().set( args[ 1 ] + "specspawn.game.z", p.getLocation().getZ() );
                plugin.saveConfig();
                p.sendMessage( ChatColor.RED + "Spec spawn has been set!" );
            } else
            {
                p.sendMessage( ChatColor.RED + "Usage: /skywars setspecspawn <mapname>" );
            }
        }
        if ( args[ 0 ].equalsIgnoreCase( "setmap" ) )
        {
            plugin.getConfig().set( "map.name", args[ 1 ] );
            plugin.saveConfig();
        }
        return true;
    }
}