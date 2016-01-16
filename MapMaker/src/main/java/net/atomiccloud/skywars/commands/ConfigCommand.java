package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.MapMakerPlugin;
import net.atomiccloud.skywars.common.Config;
import net.atomiccloud.skywars.common.SkyWarsLocation;
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
            String usage = "Usage: /config <setspawn/adddeathloc/save>";
            if ( args.length != 1 )
            {
                commandSender.sendMessage( usage );
                return true;
            }

            switch ( args[ 0 ].toLowerCase() )
            {
                case "setspawn":
                    config.setSpawnLocation( new SkyWarsLocation( ( ( Player ) commandSender ).getLocation() ) );
                    commandSender.sendMessage( "Set spawn." );
                    break;
                case "adddeathloc":
                    deathMatchLocations.add( new SkyWarsLocation( ( ( Player ) commandSender ).getLocation() ) );
                    commandSender.sendMessage( "added death-match location" );
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
