package net.atomiccloud.skywars.common;

import org.bukkit.inventory.ItemStack;

public class ChestItem
{
    private ItemStack item;
    private RequiredType requiredType;
    private int uses = 1;
    private int currentUses;

    public ChestItem(ItemStack item, RequiredType requiredType, int uses)
    {
        this.item = item;
        this.requiredType = requiredType;
        this.uses = uses;
        this.currentUses = uses;
    }

    public ChestItem(ItemStack item, RequiredType requiredType)
    {
        this.item = item;
        this.requiredType = requiredType;
    }

    public ChestItem(ItemStack item)
    {
        this.item = item;
    }

    public void resetUses()
    {
        this.currentUses = this.uses;
    }

    public int getCurrentUses()
    {
        return currentUses;
    }

    public void decrUses()
    {
        currentUses--;
    }

    public boolean isRequiredType()
    {
        return requiredType != null;
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
        HELMET, CHEST_PLATE, LEGGINGS, BOOTS, SWORD, FOOD
    }
}
