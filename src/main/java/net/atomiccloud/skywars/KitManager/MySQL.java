package net.atomiccloud.skywars.KitManager;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.logging.Level;

public class MySQL extends StorageDatabase
{
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;
    private Connection connection;

    public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password)
    {
        super( plugin );
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }

    public Connection openConnection()
    {
        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
            this.connection = DriverManager.getConnection( "jdbc:mysql://" + this.hostname + ":" + this.port + "/" +
                    this.database, this.user, this.password );
        } catch ( SQLException e )
        {
            this.plugin.getLogger().log( Level.SEVERE, "Could not connect to MySQL server! because: " + e.getMessage
                    () );
        } catch ( ClassNotFoundException e )
        {
            this.plugin.getLogger().log( Level.SEVERE, "JDBC Driver not found!" );
        }
        return this.connection;
    }

    public boolean checkConnection()
    {
        return this.connection != null;
    }

    public Connection getConnection()
    {
        return this.connection;
    }

    public void closeConnection()
    {
        if ( this.connection != null )
            try
            {
                this.connection.close();
            } catch ( SQLException e )
            {
/*  63 */
                this.plugin.getLogger().log( Level.SEVERE, "Error closing the MySQL Connection!" );
/*  64 */
                e.printStackTrace();
            }
    }

    public ResultSet querySQL(String query)
    {
/*  70 */
        Connection c = null;
 
/*  72 */
        if ( checkConnection() )
/*  73 */ c = getConnection();
        else
        {
/*  75 */
            c = openConnection();
        }
 
/*  78 */
        Statement s = null;
        try
        {
/*  81 */
            s = c.createStatement();
        } catch ( SQLException e1 )
        {
/*  83 */
            e1.printStackTrace();
        }
 
/*  86 */
        ResultSet ret = null;
        try
        {
/*  89 */
            ret = s.executeQuery( query );
        } catch ( SQLException e )
        {
/*  91 */
            e.printStackTrace();
        }
 
/*  94 */
        closeConnection();
 
/*  96 */
        return ret;
    }

    public void updateSQL(String update)
    {
/* 101 */
        Connection c = null;
 
/* 103 */
        if ( checkConnection() )
/* 104 */ c = getConnection();
        else
        {
/* 106 */
            c = openConnection();
        }
 
/* 109 */
        Statement s = null;
        try
        {
/* 112 */
            s = c.createStatement();
/* 113 */
            s.executeUpdate( update );
        } catch ( SQLException e1 )
        {
/* 115 */
            e1.printStackTrace();
        }
 
/* 118 */
        closeConnection();
    }
}