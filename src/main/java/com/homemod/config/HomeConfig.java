package com.homemod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.homemod.HomeMod;
import com.homemod.data.HomeData;
import com.homemod.data.HomeManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_DIR = "config/homemod";
    private static final String HOMES_FILE = "homes.json";
    private static final int MAX_HOMES_PER_PLAYER = 5;
    
    private static Path getConfigDir() {
        return Paths.get(CONFIG_DIR);
    }
    
    private static Path getHomesFile() {
        return getConfigDir().resolve(HOMES_FILE);
    }
    
    public static void init() {
        try {
            Path configDir = getConfigDir();
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
                HomeMod.LOGGER.info("Created config directory: {}", configDir.toAbsolutePath());
            }
            
            Path homesFile = getHomesFile();
            if (!Files.exists(homesFile)) {
                Files.createFile(homesFile);
                saveHomes(new HashMap<>());
                HomeMod.LOGGER.info("Created homes config file: {}", homesFile.toAbsolutePath());
            }
            
            loadHomes();
        } catch (IOException e) {
            HomeMod.LOGGER.error("Failed to initialize config system", e);
        }
    }
    
    public static void saveHomes(Map<UUID, Map<String, HomeData>> homes) {
        try {
            // Convert to serializable format
            Map<String, Map<String, SerializableHomeData>> serializableHomes = new HashMap<>();
            
            for (Map.Entry<UUID, Map<String, HomeData>> entry : homes.entrySet()) {
                Map<String, SerializableHomeData> playerHomes = new HashMap<>();
                for (Map.Entry<String, HomeData> homeEntry : entry.getValue().entrySet()) {
                    HomeData home = homeEntry.getValue();
                    playerHomes.put(homeEntry.getKey(), new SerializableHomeData(
                        home.getName(),
                        home.getPosition().getX(),
                        home.getPosition().getY(),
                        home.getPosition().getZ(),
                        home.getDimension().toString()
                    ));
                }
                serializableHomes.put(entry.getKey().toString(), playerHomes);
            }
            
            String json = GSON.toJson(serializableHomes);
            Files.write(getHomesFile(), json.getBytes());
        } catch (IOException e) {
            HomeMod.LOGGER.error("Failed to save homes", e);
        }
    }
    
    public static void loadHomes() {
        try {
            Path homesFile = getHomesFile();
            if (!Files.exists(homesFile)) {
                return;
            }
            
            String json = new String(Files.readAllBytes(homesFile));
            Type type = new TypeToken<Map<String, Map<String, SerializableHomeData>>>(){}.getType();
            Map<String, Map<String, SerializableHomeData>> serializableHomes = GSON.fromJson(json, type);
            
            if (serializableHomes != null) {
                Map<UUID, Map<String, HomeData>> homes = new HashMap<>();
                
                for (Map.Entry<String, Map<String, SerializableHomeData>> entry : serializableHomes.entrySet()) {
                    UUID playerUUID = UUID.fromString(entry.getKey());
                    Map<String, HomeData> playerHomes = new HashMap<>();
                    
                    for (Map.Entry<String, SerializableHomeData> homeEntry : entry.getValue().entrySet()) {
                        SerializableHomeData serializableHome = homeEntry.getValue();
                        HomeData home = new HomeData(
                            serializableHome.name,
                            new BlockPos(serializableHome.x, serializableHome.y, serializableHome.z),
                            Identifier.of(serializableHome.dimension)
                        );
                        playerHomes.put(homeEntry.getKey(), home);
                    }
                    
                    homes.put(playerUUID, playerHomes);
                }
                
                HomeManager.getInstance().loadHomes(homes);
                HomeMod.LOGGER.info("Loaded {} players' homes from config", homes.size());
            }
        } catch (IOException e) {
            HomeMod.LOGGER.error("Failed to load homes", e);
        }
    }
    
    public static boolean canAddHome(UUID playerUUID) {
        Map<String, HomeData> playerHomes = HomeManager.getInstance().getAllHomes(playerUUID);
        return playerHomes.size() < MAX_HOMES_PER_PLAYER;
    }
    
    public static int getMaxHomes() {
        return MAX_HOMES_PER_PLAYER;
    }
    
    public static int getCurrentHomeCount(UUID playerUUID) {
        Map<String, HomeData> playerHomes = HomeManager.getInstance().getAllHomes(playerUUID);
        return playerHomes.size();
    }
    
    // Serializable data class for JSON
    private static class SerializableHomeData {
        public final String name;
        public final int x, y, z;
        public final String dimension;
        
        public SerializableHomeData(String name, int x, int y, int z, String dimension) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
            this.dimension = dimension;
        }
    }
}
