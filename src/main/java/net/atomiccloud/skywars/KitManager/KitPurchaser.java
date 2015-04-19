package net.atomiccloud.skywars.KitManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.atomiccloud.skywars.SkyWarsPlugin;

import org.bukkit.entity.Player;

public class KitPurchaser {
	
	@SuppressWarnings("unused")
	private SkyWarsPlugin Main;
	
	  public KitPurchaser(SkyWarsPlugin m)
	   {
	    this.Main = m;
	  }
	
	public static MySQL db;
	private static String getQuery = "SELECT `purchases` FROM `Purchase` WHERE `Name` = ?;";
	private static String setQuery = "UPDATE `Purchase` SET `purchases`= ? WHERE `Name` = ?;";
	
  public static void setupDB() throws SQLException {
        db = new MySQL( SkyWarsPlugin.instance, "146.148.73.170", "3306", "KitManager", "root", "cir550");
		db.openConnection();
		db.openConnection();
        System.out.println("DataBase has been setup");
	    Statement statement = db.getConnection().createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Purchase` (`Name` varchar(64),`purchases` boolean);");
  }
  
  
  
   public static boolean getTier1Kit(Player player) throws SQLException {
       PreparedStatement check = db.getConnection().prepareStatement( getQuery );
       check.setString(1, player.getUniqueId().toString());
       ResultSet res = check.executeQuery();
    return res.next() && res.getBoolean("purchases");
  }
   
   
   
  public static void setHasTeir1Kit(Player player, boolean has) throws SQLException {
    PreparedStatement statement = db.getConnection().prepareStatement( setQuery );
    statement.setBoolean(1, has);
    statement.setString(2, player.getUniqueId().toString());
    statement.executeUpdate();
  }

}
