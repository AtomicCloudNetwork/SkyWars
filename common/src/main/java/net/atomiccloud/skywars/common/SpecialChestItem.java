package net.atomiccloud.skywars.common;

import org.bukkit.inventory.ItemStack;

public class SpecialChestItem extends ChestItem
{
    private double chance;

    public SpecialChestItem(ItemStack item, RequiredType requiredType, double chance)
    {
        super( item, requiredType );
        this.chance = chance;
    }

    public SpecialChestItem(ItemStack item, RequiredType requiredType, double chance, int uses)
    {
        super( item, requiredType, uses );
        this.chance = chance;
    }

    public double getChance()
    {
        return chance;
    }
}
