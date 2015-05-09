package net.atomiccloud.skywars.test;

import com.google.gson.Gson;
import net.atomiccloud.skywars.SkyWarsMap;
import net.atomiccloud.skywars.util.ArrayBuilder;
import org.junit.Test;

import java.io.File;

public class SkyWarsTest
{
    @Test
    public void testFormat()
    {
        String str = "%s hi";
        System.out.println( String.format( str, "st", "sp" ) );
    }

    @Test
    public void testArray()
    {
        String[] array1 = new String[]{ "PandemicDev" };
        System.out.println( new ArrayBuilder( array1, " & " ).toString() );
        String[] array2 = new String[]{ "Victhetiger", "PandemicDev" };
        System.out.println( new ArrayBuilder( array2, " & " ).toString() );
    }

    @Test
    public void testMaps()
    {
        SkyWarsMap map = new SkyWarsMap( "LoveLands", new ListBuilder<String>().add( "PandemicDev" ),
                new File( "/home/PandemicDev/LoveLands" ), null );
        System.out.println( new Gson().toJson( map ) );
    }


}
