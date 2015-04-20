package net.atomiccloud.skywars.util;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCord
{

    public static void toHub(Player player)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try
        {
            out.writeUTF( "Connect" );
            out.writeUTF( "Hub" );
        } catch ( IOException ignored )
        {
        }
        player.sendPluginMessage( SkyWarsPlugin.getInstance(), "BungeeCord", b.toByteArray() );
    }

}