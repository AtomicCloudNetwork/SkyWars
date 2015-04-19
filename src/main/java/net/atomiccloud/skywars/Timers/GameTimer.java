package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.KitManager.KitManager;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;
    private int countdown = 800;

    public GameTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setLevel( countdown );
        }
        switch ( countdown )
        {
            case 500:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 500 seconds." );
                //BORDER
                break;
            case 400:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 400 seconds." );
                //BORDER
                break;
            case 300:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 300 seconds." );
                //BORDER
                break;
            case 200:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 200 seconds." );
                //BORDER
                break;
            case 100:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 100 seconds." );
                //BORDER
                break;
            case 50:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 50 seconds!" );
                //BORDER
                break;
            case 20:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 20 seconds!" );
                //BORDER
                break;
            case 10:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 10 seconds." );
                //BORDER
                break;
            case 3:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 3 seconds!" );
                //BORDER
                break;
            case 2:
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 2 seconds!" );
                //BORDER
                break;
            case 1:
                Bukkit.getServer().getOnlinePlayers().forEach( BungeeCord::toHub );
                Bukkit.broadcastMessage( plugin.getPrefix() + ChatColor.GOLD + "Game is ending in 1 seconds!" );
                //BORDER
                break;
            case 0:
                //STATS
                //COSMITES
                //START TELEPORTER COUNTDOWN
                //BORDER
                Bukkit.getServer().getOnlinePlayers().stream().filter( players -> KitManager.teir1.contains( players
                        .getName() ) ).forEach( players -> {
                    players.getInventory().addItem( new ItemStack( Material.LEATHER_HELMET ) );
                    players.getInventory().addItem( new ItemStack( Material.LEATHER_CHESTPLATE ) );
                    players.getInventory().addItem( new ItemStack( Material.LEATHER_LEGGINGS ) );
                    players.getInventory().addItem( new ItemStack( Material.LEATHER_BOOTS ) );
                    players.getInventory().addItem( new ItemStack( Material.ROTTEN_FLESH, 5 ) );
                    players.getInventory().addItem( new ItemStack( Material.SPONGE, 5 ) );
                    players.getInventory().addItem( new ItemStack( Material.WOOD_PICKAXE ) );
                    players.getInventory().addItem( new ItemStack( Material.WOOD_AXE ) );
                } );
                break;
        }
        countdown--;
    }
}
