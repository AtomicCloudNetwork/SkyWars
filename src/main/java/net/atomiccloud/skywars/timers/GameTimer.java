package net.atomiccloud.skywars.timers;

import net.atomiccloud.skywars.SkyWarsPlugin;
import net.atomiccloud.skywars.game.GameState;
import net.atomiccloud.skywars.util.BungeeCord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable
{

    private SkyWarsPlugin plugin;
    private int countdown = 600;

    public GameTimer(SkyWarsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        for ( Player player : Bukkit.getOnlinePlayers() )
        {
            player.setLevel( countdown );
        }
        switch ( countdown )
        {
            case 500:
                broadcastMessage();
                //BORDER
                break;
            case 400:
                broadcastMessage();
                //BORDER
                break;
            case 300:
                broadcastMessage();
                //BORDER
                break;
            case 200:
                broadcastMessage();
                //BORDER
                break;
            case 100:
                broadcastMessage();
                //BORDER
                break;
            case 50:
                broadcastMessage();
                //BORDER
                break;
            case 20:
                broadcastMessage();
                //BORDER
                break;
            case 10:
                broadcastMessage();
                //BORDER
                break;
            case 3:
                broadcastMessage();
                //BORDER
                break;
            case 2:
                broadcastMessage();
                //BORDER
                break;
            case 1:
                broadcastMessage();
                //BORDER
                break;
            case 0:
                //STATS
                //COSMITES
                //START TELEPORTER COUNTDOWN
                //BORDER
                plugin.getGameManager().setGameState( GameState.DEATH_MATCH );
                for ( int i = 0; i < plugin.getGameManager().getPlayersInGame().size(); i++ )
                {
                    Player player = Bukkit.getPlayer( plugin.getGameManager().getPlayersInGame().get( i ) );
                    player.addPotionEffect( new PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 10, false, false ) );
                    player.teleport( plugin.getConfiguration().getDeathMatchLocations().get( i ) );
                }
                break;
        }
        countdown--;
    }

    private void broadcastMessage()
    {
        Bukkit.broadcastMessage( countdown == 1 ? plugin.getPrefix() +
                ChatColor.GOLD + "Game is ending in " + countdown + " second!" :
                plugin.getPrefix() + ChatColor.GOLD + "Death match starting in " + countdown + " seconds!" );
    }
}