package net.atomiccloud.skywars.util;

public class ArrayBuilder
{
    private String[] array;
    private String appendBy;

    public ArrayBuilder(String[] array, String appendBy)
    {
        this.array = array;
        this.appendBy = appendBy;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( String args : array )
        {
            stringBuilder.append( args ).append( appendBy );
        }
        return stringBuilder.delete( stringBuilder.length() -
                appendBy.length(), stringBuilder.length() ).toString();
    }
}
