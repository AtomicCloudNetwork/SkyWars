package net.atomiccloud.skywars;

import net.atomiccloud.skywars.common.ChestItem;
import net.atomiccloud.skywars.util.ItemCreator;
import net.atomiccloud.skywars.util.PotionCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.junit.Test;

public class SkyWarsTest
{

    @Test
    public void testSerialization()
    {
        ChestItem[] normalItems = new ChestItem[]{
                createItem( Material.IRON_HELMET, ChestItem.RequiredType.HELMET ),
                createItem( Material.IRON_CHESTPLATE, ChestItem.RequiredType.CHEST_PLATE ),
                createItem( Material.IRON_LEGGINGS, ChestItem.RequiredType.LEGGINGS ),
                createItem( Material.IRON_BOOTS, ChestItem.RequiredType.BOOTS ),
                createItem( Material.IRON_CHESTPLATE, ChestItem.RequiredType.CHEST_PLATE ),
                createItem( Material.DIAMOND_HELMET, ChestItem.RequiredType.HELMET ),
                createItem( Material.DIAMOND_CHESTPLATE, ChestItem.RequiredType.CHEST_PLATE ),
                createItem( Material.DIAMOND_LEGGINGS, ChestItem.RequiredType.LEGGINGS ),
                createItem( Material.DIAMOND_BOOTS, ChestItem.RequiredType.BOOTS ),
                createItem( Material.DIAMOND_CHESTPLATE, ChestItem.RequiredType.CHEST_PLATE ),
                createItem( Material.DIAMOND_SWORD, ChestItem.RequiredType.SWORD ),
                createItem( Material.IRON_SWORD, ChestItem.RequiredType.SWORD ),
                new ChestItem( new ItemCreator( Material.DIAMOND_SWORD ).enchant( Enchantment.DAMAGE_ALL, 1 )
                        .toItemStack(), ChestItem.RequiredType.SWORD, 12 ),
                createItem( new ItemStack( Material.COOKED_BEEF, 16 ), ChestItem.RequiredType.FOOD ),
                createItem( Material.BOW, 1, 2 ),
                createItem( Material.ARROW, 6, 2 ),
                createItem( Material.ARROW, 10, 3 ),
                new ItemCreator( Material.BOW ).enchant( Enchantment.ARROW_DAMAGE, 1 ).toChestItem(),
                createItem( Material.SNOW_BALL, 16, 1 ),
                createItem( Material.EGG, 16, 1 ),
                createItem( new PotionCreator( PotionType.REGEN ).setSplash( true ).toItemStack( 1 ), 2 ),
                createItem( new PotionCreator( PotionType.SPEED ).setSplash( true ).toItemStack( 1 ), 3 ),
                createItem( new PotionCreator( PotionType.INSTANT_HEAL ).setSplash( true ).toItemStack( 3 ), 1 ),
                createItem( Material.WOOD, 16, 2 ),
                createItem( Material.WOOD, 8, 2 ),
                createItem( Material.STONE, 16, 2 ),
                createItem( Material.STONE, 8, 2 ),
                createItem( Material.WATER_BUCKET, 1, 2 ),
                createItem( Material.LAVA_BUCKET, 1, 1 ),
                createItem( Material.DIAMOND_PICKAXE, 3 ),
                createItem( Material.ENCHANTMENT_TABLE, 2 ),
                createItem( Material.EXP_BOTTLE, 32, 3 ),
                createItem( Material.WEB, 3, 2 ),
                createItem( Material.STICK, 8, 4 ),
                createItem( Material.DIAMOND, 2, 2 ),
                createItem( Material.IRON_INGOT, 2, 2 ),
                createItem( Material.TNT, 8, 2 ),
                createItem( Material.TNT, 10, 1 ),
                createItem( Material.DIAMOND_AXE, 1 ),
                createItem( Material.FISHING_ROD, 3 ),
                createItem( new Potion( PotionType.FIRE_RESISTANCE ).toItemStack( 1 ), 3 )
        };
    }

    private ChestItem createItem(ItemStack itemStack, int uses)
    {
        return new ChestItem( itemStack, null, uses );
    }

    private ChestItem createItem(Material material, int uses)
    {
        return createItem( new ItemStack( material ), uses );
    }

    private ChestItem createItem(Material material, int amount, int uses)
    {
        return createItem( new ItemStack( material, amount ), uses );
    }

    private ChestItem createItem(ItemStack itemStack, ChestItem.RequiredType requiredType)
    {
        return new ChestItem( itemStack, requiredType );
    }

    private ChestItem createItem(Material item, ChestItem.RequiredType requiredType)
    {
        return new ChestItem( new ItemStack( item, 1 ), requiredType );
    }
}
