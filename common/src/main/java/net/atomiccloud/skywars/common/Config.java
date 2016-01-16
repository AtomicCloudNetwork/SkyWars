package net.atomiccloud.skywars.common;

import net.atomiccloud.skywars.common.SkyWarsLocation;
import org.bukkit.Location;

public class Config
{

    private SkyWarsLocation spawnLocation;
    private SkyWarsLocation[] deathMatchLocations;

    public void setSpawnLocation(SkyWarsLocation spawnLocation)
    {
        this.spawnLocation = spawnLocation;
    }

    public void setDeathMatchLocations(SkyWarsLocation[] deathMatchLocations)
    {
        this.deathMatchLocations = deathMatchLocations;
    }

    public Location getSpawnLocation()
    {
        return spawnLocation;
    }

    public Location[] getDeathMatchLocations()
    {
        return deathMatchLocations;
    }
}
