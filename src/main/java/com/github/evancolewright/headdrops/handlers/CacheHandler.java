package com.github.evancolewright.headdrops.handlers;

import com.github.evancolewright.headdrops.HeadDropsPlugin;
import com.github.evancolewright.headdrops.model.PlayerHeadData;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class CacheHandler
{
    private final HeadDropsPlugin plugin;
    private final Gson gson;
    private List<PlayerHeadData> headLocationCache = new ArrayList<>();

    public CacheHandler(HeadDropsPlugin plugin)
    {
        this.plugin = plugin;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationAdapter())
                .create();
    }

    public void addPlayerHeadData(PlayerHeadData playerHeadData)
    {
        this.headLocationCache.add(playerHeadData);
    }

    public void removePlayerHeadData(Location location)
    {
        this.headLocationCache.removeIf(playerHeadData -> playerHeadData.getLocation().equals(location));
    }

    public Optional<PlayerHeadData> getPlayerHeadData(Location location)
    {
        return this.headLocationCache
                .stream()
                .filter(playerHeadData -> playerHeadData.getLocation().equals(location))
                .findFirst();
    }

    public void loadCache()
    {
        File headLocationsFile = new File(this.plugin.getDataFolder() + File.separator + "data", "headLocations.json");
        if (!headLocationsFile.exists())
            return;  // Needed for first run of the plugin before the data file is created.
        try (Reader reader = new FileReader(headLocationsFile))
        {
            this.headLocationCache = new ArrayList<>(Arrays.asList(this.gson.fromJson(reader, PlayerHeadData[].class)));
        } catch (IOException exception)
        {
            this.plugin.getLogger().severe("[ERROR] Failed to load persisted head values.  Please report this issue at https://github.com/evancolewright/headdrops.");
            exception.printStackTrace();
        }
    }

    public void saveCache()
    {
        File dataFolder = new File(this.plugin.getDataFolder() + File.separator + "/data");
        dataFolder.mkdirs();
        File headLocationsFile = new File(dataFolder, "headLocations.json");
        try (Writer writer = new FileWriter(headLocationsFile))
        {
            this.gson.toJson(headLocationCache, writer);
        } catch (IOException exception)
        {
            this.plugin.getLogger().severe("[ERROR] Failed to persist head values.  Please report this issue at https://github.com/evancolewright/headdrops.");
            exception.printStackTrace();
        }
    }

    /**
     * Adapter class to serialize / deserialize {@link Location} objects to/from JSOn.
     *
     * @author Evan Wright
     */
    private static class LocationAdapter implements JsonDeserializer<Location>, JsonSerializer<Location>
    {
        @Override
        public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
        {
            JsonObject jsonObject = (JsonObject) jsonElement;
            return new Location(
                    Bukkit.getWorld(jsonObject.get("world").getAsString()),
                    jsonObject.get("x").getAsInt(),
                    jsonObject.get("y").getAsInt(),
                    jsonObject.get("z").getAsInt()
            );
        }

        @Override
        public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("world", location.getWorld().getName());
            jsonObject.addProperty("x", location.getBlockX());
            jsonObject.addProperty("y", location.getBlockY());
            jsonObject.addProperty("z", location.getBlockZ());
            return jsonObject;
        }
    }
}
