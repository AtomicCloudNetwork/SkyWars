package net.atomiccloud.skywars.common;

import org.bukkit.inventory.ItemStack;

public class SpecialChestItem extends ChestItem
{
    private float chance;

    public SpecialChestItem(ItemStack item, RequiredType requiredType, float chance)
    {
        super( item, requiredType );
        this.chance = chance;
    }

    public float getChance()
    {
        return chance;
    }
}
