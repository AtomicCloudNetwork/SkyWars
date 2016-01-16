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

    @Override
    public Location clone()
    {
        return super.clone();
    }

    public Location toBukkitLocation()
    {
        if ( getWorld() == null )
            setWorld( Bukkit.getWorld( worldName ) );
        return this;
    }
}
