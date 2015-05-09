package net.atomiccloud.skywars.util;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<E>
{
    List<E> list = new ArrayList<>();
    private String appendBy;

    @SuppressWarnings("unchecked")
    public ListBuilder(List<String> list, String appendBy)
    {
        if ( list != null )
            this.list = (List<E>) list;
        this.appendBy = appendBy;
    }

    public List<E> add(E element)
    {
        list.add( element );
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( String args : (List<String>) list )
        {
            stringBuilder.append( args ).append( appendBy );
        }
        return stringBuilder.delete( stringBuilder.length() -
                appendBy.length(), stringBuilder.length() ).toString();
    }
}
