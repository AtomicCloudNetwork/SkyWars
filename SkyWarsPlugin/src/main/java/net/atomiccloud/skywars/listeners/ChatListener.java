package net.atomiccloud.skywars.listeners;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{

    private SkyWarsPlugin plugin;

    public ChatListener(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
    {
        if ( !plugin.getGameManager().getPlayersInGame().contains( event.getPlayer().getName() ) )
        {
            event.setFormat( ChatColor.GRAY + "[SPECTATOR] " + event.getFormat() );
            event.getRecipients().stream().filter( player ->
                    !plugin.getGameManager().getSpectators().contains( player.getName() ) )
                    .filter( player -> event.getRecipients().remove( player ) );
        }
    }
}
