package net.atomiccloud.skywars.commands;

import net.atomiccloud.skywars.SkyWarsPlugin;
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
        if ( !plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) )
        {
            sender.sendMessage( ChatColor.RED + "Voting not active right now!" );
            return true;
        }
        if ( sender instanceof Player )
        {
            Player p = (Player) sender;
            if ( playersThatVoted.contains( p.getUniqueId() ) )
            {
                p.sendMessage( ChatColor.RED + "You already voted!" );
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

            switch ( i )
            {
                case 1:
                    plugin.getGameManager().getVotes().put( plugin.getGameManager().getMaps()[ 0 ].name(), plugin
                            .getGameManager().getVotes().get( plugin.getGameManager().getMaps()[ 0 ].name() ) + 1 );
                    p.sendMessage( plugin.getPrefix() + "You have voted for &a" + plugin.getGameManager().getMaps()[
                            0 ].getName() +
                            " *7" );
                    break;
                case 2:
                    plugin.getGameManager().getVotes().put( plugin.getGameManager().getMaps()[ 1 ].name(), plugin
                            .getGameManager().getVotes().get( plugin.getGameManager().getMaps()[ 1 ].name() ) + 1 );
                    p.sendMessage( plugin.getPrefix() + "You have voted for map 2" );
                    break;
                case 3:
                    plugin.getGameManager().getVotes().put( "Random",
                            plugin.getGameManager().getVotes().get( "Random" ) + 1 );
                    p.sendMessage( plugin.getPrefix() + "You have voted for a Random map" );
                    break;
                default:
                    plugin.getGameManager().sendVoteMessage( p );
                    break;
            }
            playersThatVoted.add( p.getUniqueId() );
            plugin.getGameManager().updateScoreboard( i );
        }
        return true;
    }


}
