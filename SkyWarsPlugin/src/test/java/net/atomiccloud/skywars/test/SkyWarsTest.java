package net.atomiccloud.skywars.test;

import com.google.gson.Gson;
import net.atomiccloud.skywars.common.SkyWarsChest;
import net.atomiccloud.skywars.common.SkyWarsMap;
import net.atomiccloud.skywars.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SkyWarsTest
{
    private List<List<SkyWarsChest>> chests = new ArrayList<>();

    @Test
    public void testFormat()
    {
        char str = '\u0123';
        System.out.println( Character.toString( str ) );
    }

    @Test
    public void testMath()
    {
        System.out.println( Math.round( ( ( ( float ) 16 ) / ( ( float ) 9 ) ) + 1 ) * 9 );
    }

    @Test
    public void testCopy()
    {
        new FileUtil( new File( "C:\\Users\\hamza\\test" ) ).copyTo( new File( "C:\\Users\\hamza\\test1" ) );
        System.out.println( "done" );
    }

    @Test
    public void testDelete()
    {
        new FileUtil( new File( "C:\\Users\\hamza\\test" ) ).delete();
    }

    @Test
    public void testMaps()
    {
        SkyWarsMap map = new SkyWarsMap( "LoveLands", null, null, "Panda moe" );
        System.out.println( new Gson().toJson( map ) );
    }

    @Test
    public void testLoad() throws FileNotFoundException
    {
        File file = new File( "C:\\Users\\hamza\\Desktop\\Wasteland.json" );

        SkyWarsMap map = new Gson().fromJson( new FileReader( file ), SkyWarsMap.class );
        System.out.println( map.getAuthorsAsString() );
        map.getChests()[ 11 ].forEach( skyWarsChest -> System.out.println( skyWarsChest.getLocation().getX() ) );
    }

    @Test
    public void testObjects()
    {
        ArrayList<TestObject> originalList = new ArrayList<>();
        originalList.add( new TestObject() );
        List<TestObject> newList = ( List<TestObject> ) originalList.clone();
        newList.get( 0 ).incrInt();
        System.out.println( originalList.get( 0 ).getAnInt() );
    }


    private class TestObject
    {

        private int anInt = 0;

        public void incrInt()
        {
            anInt++;
        }

        public int getAnInt()
        {
            return anInt;
        }
    }


}
