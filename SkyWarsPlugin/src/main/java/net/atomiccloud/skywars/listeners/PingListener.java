package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener
{

    private SkyWarsPlugin plugin;

    public PingListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent e)
    {
        switch ( plugin.getGameManager().getGameState() )
        {
            case IN_GAME:
                e.setMotd( "§cIn Game" );
                break;
            case PRE_GAME:
                e.setMotd( "§aLobby" );
                break;
            case LOBBY_COUNTDOWN:
                e.setMotd( "§aCountdown" );
                break;
            case POST_GAME:
                e.setMotd( "§4Post Game" );
                break;
        }
    }
}