package net.atomiccloud.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util
{

    public static void broadcastMessage(String message, Object... args)
    {
        Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes( '&', String.format( message, args ) ) );
    }

    public static String formatTime(int time, boolean shortForm)
    {
        int minutes = time / 60;
        int seconds = time % 60;

        if ( !shortForm )
        {
            String minuteString = "";
            if ( minutes != 0 )
                minuteString = minutes + " minute";
            String secondString = seconds + " second";
            return ( minutes == 1 ? minuteString : minutes == 0 ? "" : minuteString + "s" )
                    + ( minutes != 0 ? " " : "" ) + ( seconds == 1 ? secondString : secondString + "s" );
        } else
        {
            return minutes + "m:" + seconds + "s";
        }
    }

}
