package net.atomiccloud.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Title
{
    private String title;
    private String subtitle;

    private int fadeIn;
    private int stay;
    private int fadeOut;

    private static Class<?> titleClass;

    private static Method serialize;
    private static Method getHandle;
    private static Method sendPacket;

    private static Field playerConnection;
    private static Field actionField;

    private static Constructor titleConstructor;
    private static Constructor titleTimeConstructor;

    private static Object[] actions;

    static
    {
        String version = Bukkit.getServer().getClass().getPackage().getName().split( "\\." )[ 3 ];

        try
        {
            Class<?> entityPlayerClass = Class.forName( "net.minecraft.server." + version + ".EntityPlayer" );
            Class<?> craftPlayerClass = Class.forName( "org.bukkit.craftbukkit." + version + ".entity.CraftPlayer" );
            Class<?> typePlayerConnection = Class.forName( "net.minecraft.server." + version + ".PlayerConnection" );
            Class<?> serializerClass = Class.forName( "net.minecraft.server." + version + ".IChatBaseComponent$ChatSerializer" );

            titleClass = Class.forName( "net.minecraft.server." + version + ".PacketPlayOutTitle" );
            actionField = titleClass.getDeclaredField( "a" );
            actionField.setAccessible( true );
            Class<?> actionEnum = Class.forName( "net.minecraft.server." + version + ".PacketPlayOutTitle$EnumTitleAction" );
            Class<?> iChatBaseComponent = Class.forName( "net.minecraft.server." + version + ".IChatBaseComponent" );
            serialize = serializerClass.getMethod( "a", String.class );
            actions = actionEnum.getEnumConstants();
            getHandle = craftPlayerClass.getMethod( "getHandle" );
            playerConnection = entityPlayerClass.getField( "playerConnection" );
            sendPacket = typePlayerConnection.getMethod( "sendPacket",
                    Class.forName( "net.minecraft.server." + version + ".Packet" ) );
            titleConstructor = titleClass.getConstructor( actionEnum, iChatBaseComponent, int.class, int.class, int.class );
            titleTimeConstructor = titleClass.getConstructor( int.class, int.class, int.class );
        } catch ( ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e )
        {
            Bukkit.getLogger().severe( e.getMessage() );
        }
    }

    public Title()
    {
        fadeIn = 20;
        stay = 40;
        fadeOut = 20;
    }

    public Title(String title, String subtitle)
    {

        this.title = title;
        this.subtitle = subtitle;
        fadeIn = 20;
        stay = 40;
        fadeOut = 20;
    }

    public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public void setFadeIn(int fadeIn)
    {
        this.fadeIn = fadeIn;
    }

    public void setStay(int stay)
    {
        this.stay = stay;
    }

    public void setFadeOut(int fadeOut)
    {
        this.fadeOut = fadeOut;
    }

    public void send(Player player)
    {
        try
        {
            String[] messages = new String[]{ title, subtitle };
            for ( int i = 0; i < 2; i++ )
            {
                if ( messages[ i ] != null )
                {
                    Object json = serialize.invoke( null, "{\"text\": \"" + messages[ i ] + "\"}" );
                    Object title = titleConstructor.newInstance(
                            actions[ i ], json, fadeIn, stay, fadeOut );

                    Object handle = getHandle.invoke( player );

                    Object connection = playerConnection.get( handle );

                    sendPacket.invoke( connection, title );
                }
            }
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e )
        {
            e.printStackTrace();
        }
    }

    public void clear(Player player)
    {
        try
        {
            Object title = titleClass.newInstance();

            actionField.set( title, actions[ 3 ] );

            Object handle = getHandle.invoke( player );
            Object connection = playerConnection.get( handle );

            sendPacket.invoke( connection, title );

        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e )
        {
            e.printStackTrace();
        }
    }

    public void reset(Player player)
    {
        try
        {
            Object title = titleClass.newInstance();
            actionField.set( title, actions[ 4 ] );

            Object handle = getHandle.invoke( player );
            Object connection = playerConnection.get( handle );

            sendPacket.invoke( connection, title );
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e )
        {
            e.printStackTrace();
        }
    }

    public void sendTimeUpdate(Player player)
    {
        sendTimeUpdate( player, fadeIn, stay, fadeOut );
    }

    public void sendTimeUpdate(Player player, int fadeIn, int stay, int fadeOut)
    {
        try
        {
            Object time = titleTimeConstructor.newInstance( fadeIn, stay, fadeOut );

            Object handle = getHandle.invoke( player );
            Object connection = playerConnection.get( handle );

            sendPacket.invoke( connection, time );
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException e )
        {
            e.printStackTrace();
        }
    }
}