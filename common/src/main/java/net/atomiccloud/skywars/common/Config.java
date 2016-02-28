package net.atomiccloud.skywars.common;

public class Config
{

    private SkyWarsLocation spawnLocation;
    private SkyWarsLocation[] deathMatchLocations;
    private ChestItem[] normalChestItems;
    private ChestItem[] centerChestItems;


    public void setSpawnLocation(SkyWarsLocation spawnLocation)
    {
        this.spawnLocation = spawnLocation;
    }

    public void setDeathMatchLocations(SkyWarsLocation[] deathMatchLocations)
    {
        this.deathMatchLocations = deathMatchLocations;
    }

    public void setNormalChestItems(ChestItem[] chestItems)
    {
        this.normalChestItems = chestItems;
    }

    public void setCenterChestItems(ChestItem[] centerChestItems)
    {
        this.centerChestItems = centerChestItems;
    }

    public SkyWarsLocation getSpawnLocation()
    {
        return spawnLocation;
    }

    public SkyWarsLocation[] getDeathMatchLocations()
    {
        return deathMatchLocations;
    }

    public ChestItem[] getNormalChestItems()
    {
        return normalChestItems;
    }

    public ChestItem[] getCenterChestItems()
    {
        return centerChestItems;
    }
}
