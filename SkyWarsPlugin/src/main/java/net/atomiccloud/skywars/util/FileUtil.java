package net.atomiccloud.skywars.util;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil
{

    private File src;

    public FileUtil(File src)
    {
        Preconditions.checkNotNull( src );
        this.src = src;
    }

    public void copyTo(File dest)
    {
        copyDir( src, dest );
    }

    private void copyDir(File src, File dest)
    {
        if ( src.isDirectory() )
        {
            if ( !dest.exists() )
            {
                dest.mkdir();
            }

            for ( String child : src.list() )
            {
                copyDir( new File( src, child ), new File( dest, child ) );
            }
        } else
        {
            try
            {
                Files.copy( src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING );
            } catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void delete()
    {
        deleteDir( src );
    }

    private void deleteDir(File file)
    {
        if ( file.isDirectory() )
        {
            for ( String child : file.list() )
            {
                deleteDir( new File( file, child ) );
            }
        }
        file.delete();
    }
}
