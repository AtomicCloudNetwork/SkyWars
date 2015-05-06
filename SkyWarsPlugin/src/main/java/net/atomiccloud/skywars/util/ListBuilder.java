package net.atomiccloud.skywars.util;

import java.util.ArrayList;
import java.util.List;

public class ListBuilder<E>
{
    List<E> list = new ArrayList<>();

    public List<E> add(E element)
    {
        list.add( element );
        return list;
    }
}
