package net.atomiccloud.skywars.util;

import net.atomiccloud.skywars.common.ChestItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator
{

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private List<String> lores = new ArrayList<>();

    public ItemCreator(Material material)
    {
        this( material, ( short ) 0 );
    }

    public ItemCreator(Material material, short data)
    {
        this.itemStack = new ItemStack( material, 1, data );
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * @return Returns the created Item in the form of an {@link ItemStack}.
     */
    public ItemStack toItemStack()
    {
        itemMeta.setLore( lores );
        itemStack.setItemMeta( itemMeta );
        return itemStack;
    }

    public ChestItem toChestItem(ChestItem.RequiredType requiredType)
    {
        return new ChestItem( toItemStack(), requiredType );
    }

    public ChestItem toChestItem()
    {
        return toChestItem( null );
    }

    /**
     * Sets the {@link ItemMeta#setDisplayName(String)} option with color code translation.
     */
    public ItemCreator name(String name)
    {
        itemMeta.setDisplayName( colorize( name ) );
        return this;
    }

    /**
     * Adds a lore to {@link ItemCreator#lores} after translating color codes.
     */
    public ItemCreator lore(String lore)
    {
        lores.add( colorize( lore ) );
        return this;
    }

    /**
     * Adds a multiple lores to {@link ItemCreator#lores} after translating color codes.
     */
    public ItemCreator lores(String... lores)
    {
        for ( String lore : lores )
        {
            lore( lore );
        }
        return this;
    }

    public ItemCreator enchant(Enchantment enchantment, int level)
    {
        itemMeta.addEnchant( enchantment, level, true );
        return this;
    }

    /**
     * Sets the {@link org.bukkit.inventory.meta.ItemMeta.Spigot#setUnbreakable(boolean)} option.
     */
    public ItemCreator setUnbreakable(boolean value)
    {
        itemMeta.spigot().setUnbreakable( value );
        return this;
    }

    private String colorize(String str)
    {
        return ChatColor.translateAlternateColorCodes( '&', str );
    }

}
