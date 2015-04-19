package net.atomiccloud.skywars.KitManager;

import net.atomiccloud.skywars.util.BukkitRunnable;
import net.atomiccloud.skywars.SkyWarsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class KitNPC implements Listener
{


    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event)
    {
        if ( event.getEntity() instanceof Villager )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void conbust(EntityCombustEvent e)
    {
        if ( e.getEntity() instanceof Villager )
        {
            e.setCancelled( true );
        }
    }


    @EventHandler
    public void onDamage1(EntityDamageEvent e)
    {
        if ( e.getEntityType().equals( EntityType.VILLAGER ) )
        {
            Villager v = (Villager) e.getEntity();
            if ( v != null )
            {
                e.setCancelled( true );
                e.setDamage( 0.D );

            }
        }
    }


    public void refreshTierOne()
    {
        final World w = Bukkit.getWorld( SkyWarsPlugin.instance.getConfig().getString( "tier1.world" ) );
        double x = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.x" );
        double y = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.y" );
        double z = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.z" );
        ( (BukkitRunnable) () -> w.getEntities().stream().filter( e -> e instanceof Villager ).forEach( e -> w
                .getEntities().stream().filter( entity ->
                ( (Villager) e ).getCustomName() != null ).filter( entity -> entity.getType().equals( EntityType
                        .VILLAGER ) ).filter( entity ->
                ( (Villager) e ).getCustomName().equalsIgnoreCase( "§a§l§oDumpster" ) ).forEach( entity ->
                e.teleport( new Location( w, x, y, z ) ) ) ) ).runAfterEvery( 0, 500, TimeUnit.MILLISECONDS );
    }


    public void tierOneNPC()
    {
        final World w = Bukkit.getWorld( SkyWarsPlugin.instance.getConfig().getString( "tier1.world" ) );
        double x = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.x" );
        double y = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.y" );
        double z = SkyWarsPlugin.instance.getConfig().getDouble( "tier1.z" );

        Location loc = new Location( w, x, y, z );

        Villager v = (Villager) loc.getWorld().spawnCreature( loc, CreatureType.VILLAGER );
        v.setCustomName( "§a§l§oDumpster" );
        v.setCustomNameVisible( true );
        v.getEquipment().clear();
        v.addPotionEffect( new PotionEffect( PotionEffectType.SLOW, 3560000, 356000 ) );


    }
}
