package net.atomiccloud.skywars.common;

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

    public SkyWarsLocation getSpawnLocation()
    {
        return spawnLocation;
    }

    public SkyWarsLocation[] getDeathMatchLocations()
    {
        return deathMatchLocations;
    }
}
