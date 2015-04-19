package net.atomiccloud.skywars.KitManager;

import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//import net.DynamicJk.AtomicCore.Cosmites.Redis;

public class KitManager implements Listener
{


    public static ArrayList<String> teir1 = new ArrayList<>();


    @EventHandler
    public void onClick(InventoryClickEvent e) throws SQLException
    {
        ItemStack clicked = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        if ( e.getInventory().getName().equals( "§cPurchase Dumpster kit:" ) )
        {
            if ( clicked.getItemMeta().getDisplayName().equals( "§aPurchase DS Kit §7[§b700 CS§7]" ) )
            {
                /*if(Redis.getCoins(p) >= 700){
					
					p.sendMessage(SW.prefix + " Kit has been purchased!");
					KitPurchaser.setHasTeir1Kit(p, true);
					Redis.removeCoins(p, 700);
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 10, 10);
					teir1.add(p.getName());
					
				}else{
					p.sendMessage(SW.prefix + " You require 700 Cosmites to purchase kit!");
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 10, 10);
				}*/


            }


        }
        if ( clicked.getItemMeta().getDisplayName().equals( "§cCancel Purchase" ) )
        {
            p.closeInventory();
        }
    }


    public static Inventory getCompassInv(Player p)
    {
        Inventory inv = Bukkit.createInventory( null, 27, "§cPurchase Dumpster kit:" );
        ItemStack Factions = new ItemStack( Material.STAINED_CLAY,
                1, (short) 5 );
        ItemMeta FactionsMeta = Factions.getItemMeta();
        FactionsMeta.setDisplayName( "§aPurchase DS Kit §7[§b700 CS§7]" );
        ArrayList<String> lore181 = new ArrayList<String>();
        lore181.add( " " );
        lore181.add( "§e§oPurchase Dumpster kit" );
        lore181.add( " " );
        Factions.setItemMeta( FactionsMeta );
        inv.setItem( 11, Factions );
        ItemStack Factions1 = new ItemStack( Material.STAINED_CLAY,
                1, (short) 14 );
        ItemMeta FactionsMeta1 = Factions1.getItemMeta();
        FactionsMeta1.setDisplayName( "§cCancel Purchase" );
        Factions1.setItemMeta( FactionsMeta1 );
        inv.setItem( 15, Factions1 );


        return inv;

    }


    @EventHandler
    public void OnInteract(PlayerInteractEntityEvent e) throws IOException, SQLException
    {
        Player p = e.getPlayer();
        e.setCancelled( true );
        if ( e.getRightClicked().getType() == EntityType.VILLAGER )
        {
            Villager v = (Villager) e.getRightClicked();

            if ( v.getCustomName().equalsIgnoreCase( "§a§l§oDumpster" ) )
            {
                if ( KitPurchaser.getTier1Kit( p ) )
                {
                    teir1.add( p.getName() );
                    p.sendMessage( SkyWarsPlugin.prefix + " Kit has been selected!" );
                    p.playSound( p.getLocation(), Sound.HORSE_ARMOR, 20, 20 );
                }
                p.openInventory( getCompassInv( p ) );
                p.sendMessage( SkyWarsPlugin.prefix + " Purchase this kit" );
                p.playSound( p.getLocation(), Sound.CHEST_OPEN, 20, 20 );
            }
        }
    }
}
