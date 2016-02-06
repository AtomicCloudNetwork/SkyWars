package net.atomiccloud.skywars.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import net.atomiccloud.skywars.SkyWarsPlayer;
import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.Team;
import net.atomiccloud.skywars.common.ChestItem;
import net.atomiccloud.skywars.common.SkyWarsChest;
import net.atomiccloud.skywars.common.SkyWarsMap;
import net.atomiccloud.skywars.common.SpecialChestItem;
import net.atomiccloud.skywars.timers.*;
import net.atomiccloud.skywars.timers.Timer;
import net.atomiccloud.skywars.util.ItemCreator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;

public class GameManager
{

    private Random random = new Random();
    private GameState gameState = GameState.PRE_GAME;
    private Scoreboard votesBoard;
    private SkyWarsMap winningMap;
    private List<SkyWarsMap> maps = new ArrayList<>();
    private SkyWarsMap[] selectedMaps = new SkyWarsMap[ 3 ];
    private SkyWarsMap randomMap = new SkyWarsMap( "Random", null, null, "Unknown" );
    private Map<SkyWarsMap, Integer> votes = new HashMap<>();
    private Map<String, SkyWarsPlayer> players = new HashMap<>();
    private Map<UUID, UUID> damageCache = new HashMap<>();
    private ChestItem[] items;
    private ChestItem[] centerItems;
    private Inventory spectatorInv;
    private Set<UUID> currentViewers = new HashSet<>();

    private BukkitTask currentTask;
    private Timer currentTimer;
    private SkyWarsPlugin plugin;

    public GameManager(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void selectMaps()
    {
        for ( int i = 0; i < selectedMaps.length - 1; i++ )
        {
            selectedMaps[ i ] = maps.get( getRandom().nextInt( maps.size() ) );
            getMaps().remove( selectedMaps[ i ] );
        }

        selectedMaps[ selectedMaps.length - 1 ] = randomMap;

        for ( SkyWarsMap map : selectedMaps )
        {
            votes.put( map, 0 );
        }
        makeScoreboard();
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        if ( currentTask != null )
        {
            currentTask.cancel();
            currentTask = null;
        }
        if ( this.gameState != gameState )
        {
            switch ( gameState )
            {
                case LOBBY_COUNTDOWN:
                    currentTimer = new LobbyTimer( plugin );
                    break;
                case START_COUNTDOWN:
                    currentTimer = new CountdownTimer( plugin );
                    break;
                case IN_GAME:
                    currentTimer = new GameTimer( plugin );
                    break;
                case DEATH_MATCH:
                    currentTimer = new DeathMatchTimer( plugin );
                    break;
                case POST_GAME:
                    currentTimer = new RestartTimer( plugin );
                    break;
            }
            currentTask = currentTimer.runTaskTimer( plugin, 0, 20 );
        }
        this.gameState = gameState;
    }

    public void populateSpectatorInventory()
    {
        getCurrentViewers().forEach( uuid -> Bukkit.getPlayer( uuid ).closeInventory() );
        List<Player> players = Bukkit.getOnlinePlayers().stream().filter( player ->
                getPlayer( player ).getTeam().equals( Team.PLAYER ) ).collect( Collectors.toList() );
        int size = Math.round( players.size() / 9 ) * 9;
        if ( size == 0 ) size = 9;
        spectatorInv = Bukkit.createInventory( null, size, "Teleporter" );
        for ( int i = 0; i < players.size(); i++ )
        {
            Player player = players.get( i );
            ItemStack itemStack = new ItemCreator( Material.SKULL_ITEM, ( short ) 3 ).name( ChatColor.GRAY + player.getName() )
                    .lore( "&7Click here to teleport!" ).toItemStack();
            SkullMeta skullMeta = ( SkullMeta ) itemStack.getItemMeta();
            skullMeta.setOwner( player.getName() );
            itemStack.setItemMeta( skullMeta );
            spectatorInv.setItem( i, itemStack );
        }
    }

    public Set<UUID> getCurrentViewers()
    {
        return currentViewers;
    }

    public Map<UUID, UUID> getDamageCache()
    {
        return damageCache;
    }

    public void sendVoteMessage(Player player)
    {
        player.sendMessage( ChatColor.GOLD + "Vote for a map with /vote #" );
        for ( int i = 0; i < getSelectedMaps().length - 1; i++ )
        {
            int mapNum = i + 1;
            BaseComponent[] message = TextComponent.fromLegacyText(
                    ChatColor.translateAlternateColorCodes( '&', "&6&l" + mapNum + ". &6"
                            + getSelectedMaps()[ i ].getName() +
                            " (&b" + getVotes().get( getSelectedMaps()[ i ] ) + " votes&6) &7by "
                            + getSelectedMaps()[ i ].getAuthorsAsString() + "." ) );
            for ( BaseComponent baseComponent : message )
            {
                baseComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote " + mapNum ) );
            }
            player.spigot().sendMessage( message );
        }
        BaseComponent[] message = TextComponent.fromLegacyText(
                ChatColor.translateAlternateColorCodes( '&', "&6&l3. &3" +
                        "Random" + " &6(&b" + getVotes().get( randomMap ) + " votes&6)" ) );
        for ( BaseComponent baseComponent : message )
        {
            baseComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote " + getSelectedMaps().length ) );
        }
        player.spigot().sendMessage( message );
    }

    public Inventory getSpectatorInventory()
    {
        return spectatorInv;
    }

    public void setItems(ChestItem[] items)
    {
        this.items = items;
    }

    public void setCenterItems(ChestItem[] centerItems)
    {
        this.centerItems = centerItems;
    }

    private void makeScoreboard()
    {
        votesBoard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = votesBoard.registerNewObjective( "votes", "dummy" );
        objective.setDisplayName( ChatColor.GREEN + "Votes" );
        objective.setDisplaySlot( DisplaySlot.SIDEBAR );

        objective.getScore( ChatColor.GOLD + "1. " +
                ChatColor.GRAY + getSelectedMaps()[ 0 ].getName() ).setScore( votes.get( getSelectedMaps()[ 0 ] ) );
        objective.getScore( ChatColor.GOLD + "2. " +
                ChatColor.GRAY + getSelectedMaps()[ 1 ].getName() ).setScore( votes.get( getSelectedMaps()[ 1 ] ) );
        objective.getScore( ChatColor.GOLD + "3. " +
                ChatColor.DARK_AQUA + "Random" ).setScore( votes.get( randomMap ) );
    }


    public void updateScoreboard(int i)
    {
        switch ( i )
        {
            case 1:
                votesBoard.getObjective( "votes" ).getScore(
                        ChatColor.GOLD + "1. " + ChatColor.GRAY + getSelectedMaps()[ 0 ].getName() ).setScore(
                        votes.get( getSelectedMaps()[ 0 ] ) );
                break;
            case 2:
                votesBoard.getObjective( "votes" ).getScore(
                        ChatColor.GOLD + "2. " + ChatColor.GRAY + getSelectedMaps()[ 1 ].getName() ).setScore(
                        votes.get( getSelectedMaps()[ 1 ] ) );
                break;
            case 3:
                votesBoard.getObjective( "votes" ).getScore(
                        ChatColor.GOLD + "3. " + ChatColor.DARK_AQUA + "Random" ).setScore( votes.get( randomMap ) );
                break;
        }
    }

    public void populateChests()
    {
        Preconditions.checkNotNull( winningMap );
        for ( List<SkyWarsChest> skyWarsChests : winningMap.getChests() )
        {
            List<ChestItem> items;
            if ( skyWarsChests.get( 0 ).getTier().equals( SkyWarsChest.Tier.TIER_2 ) )
            {
                items = new ArrayList<>( Arrays.asList( this.centerItems ) );
            } else
            {
                items = new ArrayList<>( Arrays.asList( this.items ) );
            }
            Collections.shuffle( skyWarsChests, getRandom() );
            Collections.shuffle( items, getRandom() );
            handleRequiredItems( skyWarsChests, items );
            List<ChestItem> otherItems = items.stream().filter( item -> !item.isRequiredType() ).collect( Collectors.toList() );
            skyWarsChests.stream().forEach( skyWarsChest -> {
                Location location = skyWarsChest.getLocation().toBukkitLocation();
                Block block = location.getWorld().getBlockAt( location );
                if ( block.getType().equals( Material.CHEST ) )
                {
                    Collections.shuffle( otherItems, getRandom() );
                    Chest chest = ( Chest ) block.getState();
                    otherItems.stream().filter( chestItem ->
                            !( chestItem.getCurrentUses() <= 0 ) ).limit( 6 ).forEach( chestItem -> {
                        if ( !( chestItem instanceof SpecialChestItem ) )
                        {
                            setItem( chest.getBlockInventory(), chestItem.getItem() );
                        } else
                        {
                            SpecialChestItem specialChestItem = ( SpecialChestItem ) chestItem;
                            if ( getRandom().nextDouble() <= specialChestItem.getChance() )
                            {
                                setItem( chest.getBlockInventory(), specialChestItem.getItem() );
                            }
                        }
                        chestItem.decrUses();
                    } );
                }
            } );
            if ( skyWarsChests.get( 0 ).getTier().equals( SkyWarsChest.Tier.TIER_2 ) )
            {
                for ( ChestItem centerItem : centerItems )
                {
                    centerItem.resetUses();
                }
            } else
            {
                for ( ChestItem item : this.items )
                {
                    item.resetUses();
                }
            }
        }
    }

    private void handleRequiredItems(List<SkyWarsChest> skyWarsChests, List<ChestItem> items)
    {
        Set<ChestItem.RequiredType> requiredTypes = Sets.newHashSet( ChestItem.RequiredType.values() );
        requiredTypes.forEach( requiredType -> {
            Location location = skyWarsChests.get( plugin.getGameManager().getRandom().nextInt( skyWarsChests.size() ) ).getLocation().toBukkitLocation();
            Block block = location.getBlock();
            if ( block.getType().equals( Material.CHEST ) )
            {
                try
                {
                    ItemStack requiredItem = items.stream().filter( item ->
                            item.getRequiredType() == requiredType ).findAny().get().getItem();
                    if ( requiredItem != null )
                        setItem( ( ( Chest ) block.getState() ).getBlockInventory(), requiredItem );
                } catch ( NoSuchElementException ignored )
                {
                }
            }
        } );
    }

    private boolean isFull(Inventory inventory)
    {
        for ( ItemStack itemStack : inventory.getContents() )
        {
            if ( itemStack == null ) return false;
        }
        return true;
    }

    private void setItem(Inventory inventory, ItemStack item)
    {
        int slot = getRandom().nextInt( inventory.getSize() );
        if ( inventory.getItem( slot ) != null )
        {
            if ( !isFull( inventory ) )
                setItem( inventory, item );
        } else
        {
            inventory.setItem( slot, item );
        }
    }

    public SkyWarsMap mapFromString(String mapName)
    {
        for ( SkyWarsMap map : getMaps() )
        {
            if ( mapName.equals( map.getName() ) ) return map;
        }
        return null;
    }

    public SkyWarsMap[] getSelectedMaps()
    {
        return selectedMaps;
    }

    public Scoreboard getVotesBoard()
    {
        return votesBoard;
    }

    public Map<SkyWarsMap, Integer> getVotes()
    {
        return votes;
    }

    public void addPlayer(Player player)
    {
        players.put( player.getName(), new SkyWarsPlayer( player.getName(), player.getUniqueId(), Team.PLAYER ) );
    }

    public Timer getCurrentTimer()
    {
        return currentTimer;
    }

    public void removePlayer(Player player)
    {
        players.remove( player.getName() );
    }

    public SkyWarsPlayer getPlayer(String playerName)
    {
        return players.get( playerName );
    }

    public SkyWarsPlayer getPlayer(Player player)
    {
        return getPlayer( player.getName() );
    }

    public Collection<SkyWarsPlayer> getPlayers()
    {
        return Collections.unmodifiableCollection( players.values() );
    }

    public SkyWarsMap getWinningMap()
    {
        return winningMap;
    }

    public void setWinningMap(SkyWarsMap winningMap)
    {
        this.winningMap = winningMap;
    }

    public SkyWarsMap getRandomMap()
    {
        return randomMap;
    }

    public List<SkyWarsMap> getMaps()
    {
        return maps;
    }

    public Random getRandom()
    {
        return random;
    }
}