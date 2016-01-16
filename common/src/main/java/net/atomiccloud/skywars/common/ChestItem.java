package net.atomiccloud.skywars.common;

import org.bukkit.inventory.ItemStack;

public class ChestItem
{
    private ItemStack item;
    private RequiredType requiredType;

    public ChestItem(ItemStack item, RequiredType requiredType)
    {
        this.item = item;
        this.requiredType = requiredType;
    }

    public ItemStack getItem()
    {
        return item;
    }

    public RequiredType getRequiredType()
    {
        return requiredType;
    }

    public enum RequiredType
    {
        HELMET, CHEST_PLATE, LEGGINGS, BOOTS, SWORD
    }
}
