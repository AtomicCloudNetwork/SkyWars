package net.atomiccloud.skywars.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionCreator
{

    private Potion potion;

    public PotionCreator(PotionType type)
    {
        potion = new Potion( type );
    }

    public PotionCreator setSplash(boolean isSplash)
    {
        potion.setSplash( isSplash );
        return this;
    }

    public ItemStack toItemStack(int amount)
    {
        return potion.toItemStack( amount );
    }
}
