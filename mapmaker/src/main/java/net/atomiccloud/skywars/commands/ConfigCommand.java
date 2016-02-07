package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.MapMakerPlugin;
import net.atomiccloud.skywars.common.Config;
import net.atomiccloud.skywars.common.SkyWarsLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigCommand implements CommandExecutor
{

    private Config config = new Config();
    private List<SkyWarsLocation> deathMatchLocations = new ArrayList<>();
    private MapMakerPlugin plugin;

    public ConfigCommand(MapMakerPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args)
    {
        if ( commandSender instanceof Player )
        {
            String usage = ChatColor.DARK_AQUA + "Usage: /config <setspawn/adddeathmatchspawnpoint/save>";
            if ( args.length != 1 )
            {
                commandSender.sendMessage( usage );
                return true;
            }

            Location location;
            String locationFormatted;
            switch ( args[ 0 ].toLowerCase() )
            {
                case "setspawn":
                    location = ( ( Player ) commandSender ).getLocation();
                    locationFormatted = ChatColor.GREEN + String.format( "(%f,%f,%f,%f,%f)",
                            location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw() );
                    config.setSpawnLocation( new SkyWarsLocation( location ) );
                    commandSender.sendMessage( ChatColor.DARK_GREEN + "Spawn Point was set to " + locationFormatted );
                    break;
                case "adddeathmatchspawnpoint":
                case "adddeathloc":
                    location = ( ( Player ) commandSender ).getLocation();
                    locationFormatted = ChatColor.RED + String.format( "(%f,%f,%f,%f,%f)",
                            location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw() );
                    deathMatchLocations.add( new SkyWarsLocation( location ) );
                    commandSender.sendMessage( ChatColor.DARK_RED + "Added death match spawn point at " + locationFormatted );
                    break;
                case "save":
                    saveConfig();
                    break;
                case "clear":
                    config = new Config();
                    deathMatchLocations.clear();
                    break;
                default:
                    commandSender.sendMessage( usage );
                    break;
            }
        }
        return true;
    }


    private void saveConfig()
    {
        config.setDeathMatchLocations( deathMatchLocations.stream().toArray( SkyWarsLocation[]::new ) );
        File file = new File( plugin.getDataFolder(), "config.json" );
        try
        {
            if ( !file.exists() )
                file.createNewFile();
            try ( FileWriter fileWriter = new FileWriter( file ) )
            {
                fileWriter.write( plugin.getGson().toJson( config ) );
            }
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
