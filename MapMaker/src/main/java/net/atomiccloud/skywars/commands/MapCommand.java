package net.atomiccloud.skywars.commands;

import com.google.common.collect.Lists;
import net.atomiccloud.skywars.MapMakerPlugin;
import net.atomiccloud.skywars.common.SkyWarsChest;
import net.atomiccloud.skywars.common.SkyWarsLocation;
import net.atomiccloud.skywars.common.SkyWarsMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapCommand implements CommandExecutor
{

    private SkyWarsMap map = new SkyWarsMap();
    private List<SkyWarsLocation> spawnPoints = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<List<SkyWarsChest>> chests = new ArrayList<>();
    private MapMakerPlugin plugin;

    public MapCommand(MapMakerPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args)
    {

        if ( commandSender instanceof Player )
        {
            String usage = "Usage: /map <name/addspawn/addauthor/addcenter/addchest/save/clear> <index/author/name>";
            if ( args.length < 1 )
            {
                commandSender.sendMessage( usage );
                return true;
            }

            Player player = ( Player ) commandSender;
            switch ( args[ 0 ].toLowerCase() )
            {
                case "name":
                    map.setName( joinArgs( args, 0, args.length ) );
                    break;
                case "addchest":
                    addChest( player, SkyWarsChest.Tier.TIER_1, Integer.parseInt( args[ 1 ] ) );
                    break;
                case "addcenter":
                    addChest( player, SkyWarsChest.Tier.TIER_2, Integer.parseInt( args[ 1 ] ) );
                    break;
                case "addauthor":
                    authors.add( args[ 1 ] );
                    break;
                case "addspawn":
                    spawnPoints.add( new SkyWarsLocation( player.getLocation() ) );
                    break;
                case "save":
                    saveMap();
                    break;
                case "clear":
                    spawnPoints.clear();
                    authors.clear();
                    chests.clear();
                    map = new SkyWarsMap();
                    break;
                default:
                    commandSender.sendMessage( usage );
                    break;
            }
        }

        return true;
    }

    private String joinArgs(String[] args, int min, int max)
    {
        StringBuilder builder = new StringBuilder();
        for ( int i = min; i < max; i++ )
        {
            builder.append( args[ i ] ).append( " " );
        }

        String str = builder.toString();
        return str.substring( 0, str.length() - 1 );
    }

    private void addChest(Player player, SkyWarsChest.Tier tier, int index)
    {
        Block block = player.getTargetBlock( Collections.singleton( Material.AIR ), 10 );
        if ( block.getType().equals( Material.CHEST ) )
        {
            SkyWarsChest chest = new SkyWarsChest( new SkyWarsLocation( block.getLocation() ), tier );
            if ( ( index + 1 ) > chests.size() )
            {
                chests.add( Lists.newArrayList( chest ) );
            } else
            {
                chests.get( index ).add( chest );
            }
        }
    }

    private void saveMap()
    {
        map.setAuthors( authors.stream().toArray( String[]::new ) );
        map.setSpawnLocations( spawnPoints.stream().toArray( SkyWarsLocation[]::new ) );
        @SuppressWarnings("unchecked")
        List<SkyWarsChest>[] chests = this.chests.stream().toArray( List[]::new );
        map.setChests( chests );
        File file = new File( plugin.getDataFolder(), map.getName() + ".json" );
        try ( FileWriter fileWriter = new FileWriter( file ) )
        {
            if ( !file.exists() )
                file.createNewFile();
            fileWriter.write( plugin.getGson().toJson( map ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
