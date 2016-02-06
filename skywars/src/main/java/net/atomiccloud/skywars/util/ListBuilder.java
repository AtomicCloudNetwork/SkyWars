package net.atomiccloud.skywars.util;

import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ListBuilder<E>
{
    private List<E> list = new ArrayList<>();
    private String appendBy;

    public ListBuilder(String appendBy)
    {
        Preconditions.checkNotNull( appendBy, "appendBy can not be null!" );
        this.appendBy = appendBy;
    }

    public ListBuilder(List<E> list, String appendBy)
    {
        Preconditions.checkNotNull( list, "list can not be null!" );
        Preconditions.checkNotNull( appendBy, "appendBy can not be null!" );
        this.list = list;
        this.appendBy = appendBy;
    }

    public ListBuilder add(E element)
    {
        list.add( element );
        return this;
    }

    public List<E> toList()
    {
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( String args : ( List<String> ) list )
        {
            stringBuilder.append( args ).append( appendBy );
        }
        return stringBuilder.delete( stringBuilder.length() -
                appendBy.length(), stringBuilder.length() ).toString();
    }
}
