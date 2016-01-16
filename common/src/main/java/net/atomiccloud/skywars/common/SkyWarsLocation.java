package net.atomiccloud.skywars.common;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SkyWarsLocation extends Location
{

    private String worldName;

    public SkyWarsLocation(String worldName, double x, double y, double z, float yaw, float pitch)
    {
        super( null, x, y, z, yaw, pitch );
        this.worldName = worldName;
    }

    public SkyWarsLocation(Location location)
    {
        this( location.getWorld().getName(), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch() );
    }

    public String getWorldName()
    {
        return worldName;
    }

    public Location toBukkitLocation()
    {
        if ( getWorld() == null )
            setWorld( Bukkit.getWorld( getWorldName() ) );
        return this;
    }
}
