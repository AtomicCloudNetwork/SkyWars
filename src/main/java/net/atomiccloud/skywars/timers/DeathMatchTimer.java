package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.listeners.MoveListener;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.lang.reflect.Method;

public class DeathMatchTimer extends BukkitRunnable
{
    private SkyWarsPlugin plugin;

    private int countdown = 130;

    private MoveListener listener = new MoveListener();

    public DeathMatchTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(
                listener, plugin );
    }

    @Override
    public void run()
    {
        switch ( countdown )
        {
            case 130:
                broadcastStart();
                break;
            case 125:
                broadcastStart();
                break;
            case 124:
                broadcastStart();
                break;
            case 123:
                broadcastStart();
                break;
            case 122:
                broadcastStart();
                break;
            case 121:
                broadcastStart();
                break;
            case 120:
                unregisterEvents( listener, plugin );
                Bukkit.broadcastMessage( plugin.getPrefix() + "Death match started!" );
                break;
            case 60:
                broadcastEnd();
                break;
            case 30:
                broadcastEnd();
                break;
            case 10:
                broadcastEnd();
                break;
            case 5:
                broadcastEnd();
                break;
            case 4:
                broadcastEnd();
                break;
            case 3:
                broadcastEnd();
                break;
            case 2:
                broadcastEnd();
                break;
            case 1:
                broadcastEnd();
                Bukkit.getOnlinePlayers().forEach( BungeeCord::toHub );
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
        countdown--;
    }

    private void broadcastStart()
    {
        Bukkit.broadcastMessage( countdown == 1 ? plugin.getPrefix() + "Death match starting in "
                + ( countdown - 120 ) + " second!"
                : plugin.getPrefix() + "Death match starting in " + ( countdown - 120 ) + " seconds!" );
    }

    private void broadcastEnd()
    {
        Bukkit.broadcastMessage( countdown == 1 ? plugin.getPrefix() + "Death match ending in " + countdown + "second!"
                : plugin.getPrefix() + "Death match ending in " + countdown + "seconds!" );
    }

    @SuppressWarnings("unchecked")
    private static void unregisterEvents(Listener listener, Plugin plugin)
    {
        try
        {
            for ( Method method : listener.getClass().getMethods() )
            {
                if ( method.getAnnotation( EventHandler.class ) != null )
                {
                    unregisterEvent( (Class<? extends Event>) method.getParameterTypes()[ 0 ], listener, plugin );
                }

            }
        } catch ( Exception ignored )
        {
        }
    }

    private static void unregisterEvent(Class<? extends Event> eventClass, Listener listener, Plugin plugin)
    {
        HandlerList.getRegisteredListeners( plugin ).stream().filter( regListener -> regListener.getListener() ==
                listener ).forEach( regListener -> {
            try
            {
                ( (HandlerList) eventClass.getMethod( "getHandlerList" ).invoke( null ) ).unregister( regListener );
            } catch ( Exception ignored )
            {
            }
        } );
    }
}
