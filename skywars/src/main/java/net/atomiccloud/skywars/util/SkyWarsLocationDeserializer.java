package net.atomiccloud.skywars.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.atomiccloud.skywars.common.SkyWarsLocation;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;

public class SkyWarsLocationDeserializer implements JsonDeserializer<SkyWarsLocation>
{

    @Override
    public SkyWarsLocation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        SkyWarsLocation skyWarsLocation = jsonDeserializationContext.deserialize( jsonElement, SkyWarsLocation.class );
        skyWarsLocation.setWorld( Bukkit.getWorld( skyWarsLocation.getWorldName() ) );
        return skyWarsLocation;
    }
}
