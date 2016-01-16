package net.atomiccloud.skywars.common;

public class SkyWarsChest
{

    private SkyWarsLocation location;
    private Tier tier;

    public SkyWarsChest(SkyWarsLocation location, Tier tier) {
        this.location = location;
        this.tier = tier;
    }

    public SkyWarsLocation getLocation()
    {
        return location;
    }

    public Tier getTier()
    {
        return tier;
    }

    public enum Tier
    {
        TIER_1, TIER_2
    }
}
