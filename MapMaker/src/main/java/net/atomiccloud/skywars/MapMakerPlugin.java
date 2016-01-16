package net.atomiccloud.skywars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.atomiccloud.skywars.commands.ConfigCommand;
import net.atomiccloud.skywars.commands.MapCommand;
import net.atomiccloud.skywars.commands.MapMakerCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public class MapMakerPlugin extends JavaPlugin
{

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void onEnable()
    {
        if ( !getDataFolder().exists() )
        {
            getDataFolder().mkdir();
        }
        registerCommands();
    }

    private void registerCommands()
    {
        Stream.of( new MapMakerCommand( "config", new ConfigCommand( this ) ), new MapMakerCommand( "map", new MapCommand( this ) ) )
                .forEach( command -> getCommand( command.getName() ).setExecutor( command.getExecutor() ) );
    }

    public Gson getGson()
    {
        return gson;
    }

}
