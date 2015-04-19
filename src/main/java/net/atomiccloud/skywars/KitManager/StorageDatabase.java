package net.atomiccloud.skywars.KitManager;

import java.sql.Connection;
import org.bukkit.plugin.Plugin;

public abstract class StorageDatabase
{
  protected Plugin plugin;

  protected StorageDatabase(Plugin plugin)
  {
    this.plugin = plugin;
  }

  public abstract Connection openConnection();

  public abstract boolean checkConnection();

  public abstract Connection getConnection();

  public abstract void closeConnection();
}