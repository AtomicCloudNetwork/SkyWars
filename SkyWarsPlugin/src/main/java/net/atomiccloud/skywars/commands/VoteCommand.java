package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.common.SkyWarsMap;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VoteCommand implements CommandExecutor
{
    private SkyWarsPlugin plugin;

    private Set<UUID> playersThatVoted = new HashSet<>();

    public VoteCommand(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) &&
                !plugin.getGameManager().getGameState().equals( GameState.LOBBY_COUNTDOWN ) )
        {
            sender.sendMessage( ChatColor.RED + "Voting isn't active right now!" );
            return true;
        }
        if ( sender instanceof Player )
        {
            Player p = ( Player ) sender;

            if ( playersThatVoted.contains( p.getUniqueId() ) )
            {
                p.sendMessage( ChatColor.RED + "You have already voted!" );
                return true;
            }

            if ( args.length != 1 )
            {
                plugin.getGameManager().sendVoteMessage( p );
                return true;
            }

            int i;

            try
            {
                i = Integer.parseInt( args[ 0 ] );
            } catch ( NumberFormatException e )
            {
                plugin.getGameManager().sendVoteMessage( p );
                return true;
            }


            if ( i >= 1 && i <= plugin.getGameManager().getSelectedMaps().length )
            {
                SkyWarsMap map = plugin.getGameManager().getSelectedMaps()[ i - 1 ];
                plugin.getGameManager().getVotes().put( map, plugin
                        .getGameManager().getVotes().get( map ) + 1 );
                p.sendMessage( plugin.getPrefix() + "You have voted for " + map.getName() +
                        " by " + map.getAuthorsAsString() + "." );
                playersThatVoted.add( p.getUniqueId() );
                plugin.getGameManager().updateScoreboard( i );
            } else
            {
                plugin.getGameManager().sendVoteMessage( p );
            }
        }
        return true;
    }
}
