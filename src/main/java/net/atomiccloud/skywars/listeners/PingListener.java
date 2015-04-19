package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener
{

    private SkyWarsPlugin plugin;

    public PingListener(SkyWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent e)
    {
        if ( plugin.getGameManager().getGameState().equals( GameState.IN_GAME ) )
        {
            e.setMotd( "§cINGAME" );
        }
        if ( plugin.getGameManager().getGameState().equals( GameState.PRE_GAME ) )
        {
            e.setMotd( "§aLobby" );
        }
        if ( ( plugin.getGameManager().getGameState().equals( GameState.POST_GAME ) ) )
        {
            e.setMotd( "§4Post Game " );
        }
    }
}
