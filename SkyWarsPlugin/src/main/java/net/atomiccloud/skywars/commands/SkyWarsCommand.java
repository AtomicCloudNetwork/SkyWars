package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        Player player = (Player) sender;

        if ( args.length < 1 )
        {
            player.sendMessage( ChatColor.RED + "Please Specify an action!" );
            return true;
        }

        switch ( args[ 0 ].toLowerCase() )
        {
            case "forcedeathmatch":
                if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
                {
                    plugin.getGameManager().setGameState( GameState.DEATH_MATCH );
                    for ( int i = 0; i < plugin.getGameManager().getPlayersInGame().size(); i++ )
                    {
                        Player tempPlayer = Bukkit.getPlayer( plugin.getGameManager().getPlayersInGame().get( i ) );
                        tempPlayer.addPotionEffect( new PotionEffect(
                                PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 10, false, false ) );
                        tempPlayer.teleport( plugin.getConfiguration().getDeathMatchLocations().get( i ) );
                    }
                } else
                {
                    player.sendMessage( ChatColor.RED + "nope.avi" );
                }
                break;
            case "setspawn":
                if ( args.length == 2 )
                {
                    handleSetSpawn( player, args[ 1 ] );
                } else
                {
                    sender.sendMessage( ChatColor.RED + "Usage: /skywars setspawn <mapname>" );
                }
                break;
            case "setlobby":
                handleSetLobby( player );
            break;
            case "setdeathspawn":
                handleSetDeathmatchSpawn( player );
                break;
        }
        return true;
    }

    private void handleSetSpawn(Player player, String mapName)
    {
        List<String> strings = plugin.getConfig().getStringList( mapName + "spawn-locs" ) != null
                ? plugin.getConfig().getStringList( mapName + "spawn-locs" ) : new ArrayList<>();
        strings.add( player.getLocation().getWorld().getName() + "," +
                String.valueOf( player.getLocation().getX() ) + "," +
                String.valueOf( player.getLocation().getY() ) + "," +
                String.valueOf( player.getLocation().getZ() ) + "," +
                String.valueOf( player.getLocation().getYaw() ) + "," +
                String.valueOf( player.getLocation().getPitch() ) );
        plugin.getConfig().set( mapName + "spawn-locs", strings );
        plugin.saveConfig();
        player.sendMessage( ChatColor.RED + "Spawn has been set!" );
    }

    private void handleSetDeathmatchSpawn(Player player) {
        List<String> strings = plugin.getConfig().getStringList( "death-match-locs" ) != null
                ? plugin.getConfig().getStringList( "death-match-locs" ) : new ArrayList<>();
        strings.add( player.getLocation().getWorld().getName() + "," +
                String.valueOf( player.getLocation().getX() ) + "," +
                String.valueOf( player.getLocation().getY() ) + "," +
                String.valueOf( player.getLocation().getZ() ) + "," +
                String.valueOf( player.getLocation().getYaw() ) + "," +
                String.valueOf( player.getLocation().getPitch() ) );
        plugin.getConfig().set( "death-match-locs", strings );
        plugin.getConfiguration().getSpawnLocations().add( player.getLocation() );
        plugin.saveConfig();
        player.sendMessage( ChatColor.RED + "Death Spawn has been added!" );
    }

    private void handleSetLobby(Player player) {
        plugin.getConfig().set( "lobby.world", player.getLocation().getWorld().getName() );
        plugin.getConfig().set( "lobby.x", player.getLocation().getX() );
        plugin.getConfig().set( "lobby.y", player.getLocation().getY() );
        plugin.getConfig().set( "lobby.z", player.getLocation().getZ() );
        plugin.saveConfig();
        player.sendMessage( ChatColor.RED + "Lobby has been set!" );
    }
}