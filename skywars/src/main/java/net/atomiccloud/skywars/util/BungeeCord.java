package net.atomiccloud.skywars.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCord
{

    private Plugin plugin;

    public BungeeCord(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void toLobby(Player player)
    {
        try ( ByteArrayOutputStream b = new ByteArrayOutputStream();
              DataOutputStream out = new DataOutputStream( b ) )
        {
            out.writeUTF( "Connect" );
            out.writeUTF( "skylobby" );
            player.sendPluginMessage( plugin, "BungeeCord", b.toByteArray() );
        } catch ( IOException ignored )
        {
        }
    }

}