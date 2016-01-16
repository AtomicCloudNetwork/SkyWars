package net.atomiccloud.skywars;

import com.google.gson.Gson;
import net.atomiccloud.acndatabase.common.Database;
import net.atomiccloud.acndatabase.common.DatabaseAPI;
import net.atomiccloud.skywars.commands.VoteCommand;
import net.atomiccloud.skywars.common.ChestItem;
import net.atomiccloud.skywars.common.Config;
import net.atomiccloud.skywars.common.SkyWarsMap;
import net.atomiccloud.skywars.common.SpecialChestItem;
import net.atomiccloud.skywars.game.GameManager;
import net.atomiccloud.skywars.listeners.*;
import net.atomiccloud.skywars.util.FileUtil;
import net.atomiccloud.skywars.util.ItemCreator;
import net.atomiccloud.skywars.util.PotionCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class SkyWarsPlugin extends JavaPlugin
{

    private Gson gson = new Gson();
    private String prefix = ChatColor.GOLD.toString();
    private Database database;

    private GameManager gameManager;
    private Config config;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        gameManager = new GameManager( this );
        handleMaps();
        getGameManager().selectMaps();
        setItems();
        Bukkit.getMessenger().registerOutgoingPluginChannel( this, "BungeeCord" );
        getCommand( "vote" ).setExecutor( new VoteCommand( this ) );
        registerListeners();
        database = DatabaseAPI.getDatabase();
    }

    @Override
    public void onDisable()
    {
        SkyWarsMap winningMap = getGameManager().getWinningMap();
        if ( winningMap != null )
        {
            Bukkit.unloadWorld( winningMap.getName(), false );
            new FileUtil( new File( getServer().getWorldContainer(), winningMap.getName() ) ).delete();
        }
    }

    private void registerListeners()
    {
        for ( Listener listener : new Listener[]{ new LoginListener( this ), new BlockListener( this ),
                new DeathListener( this ), new DamageListener( this ), new MiscellaneousListener( this ),
                new LeaveListener( this ), new ExplosionListener( this ), new InventoryListener( this ),
                new RespawnListener( this ), new InteractionListener( this ) } )
        {
            getServer().getPluginManager().registerEvents( listener, this );
        }
    }

    private void setItems()
    {
        getGameManager().setItems( new ChestItem[]{
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
        } );
        getGameManager().setCenterItems( new ChestItem[]{
                createItem( new PotionCreator( PotionType.JUMP ).setSplash( true ).toItemStack( 1 ), 3 ),
                createItem( new PotionCreator( PotionType.INSTANT_DAMAGE ).setSplash( true ).toItemStack( 1 ), 3 ),
                new SpecialChestItem( new ItemCreator( Material.GLOWSTONE_DUST ).name( "Dustify" )
                        .enchant( Enchantment.KNOCKBACK, 10 ).toItemStack(), null, 0.10D, 2 ),
                createItem( Material.GOLDEN_APPLE, 8, 2 ),
                createItem( Material.GOLDEN_APPLE, 6, 2 ),
                createItem( Material.GOLDEN_APPLE, 3, 4 ),
                new ChestItem( new ItemCreator( Material.DIAMOND_PICKAXE ).enchant( Enchantment.DIG_SPEED, 3 ).toItemStack(), null, 3 ),
                createItem( Material.SNOW_BALL, 64, 4 ),
                createItem( Material.EGG, 64, 4 ),
                createItem( Material.TNT, 20, 2 ),
                createItem( Material.WEB, 6, 3 ),
                createItem( Material.ENDER_PEARL, 8, 2 ),
                createItem( Material.ENDER_PEARL, 3, 2 ),
                createItem( Material.ENDER_PEARL, 1, 4 ),
                createItem( Material.FLINT_AND_STEEL, 1, 3 ),
                createItem( new Potion( PotionType.FIRE_RESISTANCE ).toItemStack( 1 ), 2 ),
                createItem( Material.WATER_BUCKET, 1, 3 ),
                createItem( Material.LAVA_BUCKET, 1, 2 ),
                new ChestItem( new ItemCreator( Material.DIAMOND_CHESTPLATE ).enchant( Enchantment.PROTECTION_ENVIRONMENTAL, 4 ).toItemStack(), null, 3 ),
                new ChestItem( new ItemCreator( Material.DIAMOND_HELMET ).enchant( Enchantment.PROTECTION_ENVIRONMENTAL, 4 ).toItemStack(), null, 3 ),
                new ItemCreator( Material.BOW ).enchant( Enchantment.ARROW_DAMAGE, 5 ).toChestItem(),
                createItem( Material.STONE, 64, 6 ),
                createItem( Material.WOOD, 64, 6 ),
                new SpecialChestItem( new ItemCreator( Material.BOW ).enchant( Enchantment.ARROW_KNOCKBACK, 2 ).toItemStack(), null, 0.50D, 3 ),
                // new SpecialChestItem( new ItemCreator( Material.BOW ).enchant( Enchantment.ARROW_FIRE, 1 ).toItemStack(), null, 0.20D, 2 ),
                new SpecialChestItem( new ItemCreator( Material.DIAMOND_SWORD ).name( "Atomic Sword" ).enchant( Enchantment.DAMAGE_ALL, 1 )
                        .enchant( Enchantment.FIRE_ASPECT, 2 ).toItemStack(), null, 0.50D, 3 ),
                createItem( new ItemCreator( Material.DIAMOND_SWORD ).enchant( Enchantment.DAMAGE_ALL, 1 ).toItemStack(), 4 ),

        } );
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

    public void saveDefaultConfig()
    {
        if ( !getDataFolder().exists() )
        {
            if ( getDataFolder().mkdir() )
            {
                getLogger().info( "Config folder created!" );
            }
        }

        File file = new File( getDataFolder(), "config.json" );

        if ( !file.exists() )
        {
            try
            {
                Files.copy( getResource( "config.json" ), file.toPath() );
                getLogger().info( "Config file saved!" );
            } catch ( IOException ex )
            {
                getLogger().severe( ex.getMessage() );
            }
        }

        try
        {
            config = gson.fromJson( new FileReader( new File( getDataFolder(), "config.json" ) ), Config.class );
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    private void handleMaps()
    {
        File file = new File( getDataFolder(), "maps" );

        if ( !file.exists() )
        {
            file.mkdir();
        }

        File[] files = file.listFiles();
        if ( files != null )
        {
            for ( File tempFile : files )
            {
                if ( tempFile.getName().endsWith( ".json" ) )
                {
                    try
                    {
                        SkyWarsMap map = gson.fromJson( new FileReader( tempFile ), SkyWarsMap.class );
                        map.setMapFile( new File( file, map.getName() ) );
                        getGameManager().getMaps().add( map );
                    } catch ( FileNotFoundException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Config getConfiguration()
    {
        return config;
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }

    public String getPrefix()
    {
        return prefix;
    }
}
