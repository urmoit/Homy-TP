package com.homemod.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeManager {
    private static HomeManager instance;
    private final Map<UUID, Map<String, HomeData>> playerHomes;
    
    private HomeManager() {
        this.playerHomes = new HashMap<>();
    }
    
    public static HomeManager getInstance() {
        if (instance == null) {
            instance = new HomeManager();
        }
        return instance;
    }
    
    public void setHome(UUID playerUUID, HomeData homeData) {
        playerHomes.computeIfAbsent(playerUUID, k -> new HashMap<>())
                   .put(homeData.getName(), homeData);
    }
    
    public HomeData getHome(UUID playerUUID, String homeName) {
        Map<String, HomeData> homes = playerHomes.get(playerUUID);
        if (homes == null) {
            return null;
        }
        return homes.get(homeName);
    }
    
    public Map<String, HomeData> getAllHomes(UUID playerUUID) {
        return playerHomes.getOrDefault(playerUUID, new HashMap<>());
    }
    
    public boolean hasHome(UUID playerUUID, String homeName) {
        Map<String, HomeData> homes = playerHomes.get(playerUUID);
        return homes != null && homes.containsKey(homeName);
    }
    
    public void removeHome(UUID playerUUID, String homeName) {
        Map<String, HomeData> homes = playerHomes.get(playerUUID);
        if (homes != null) {
            homes.remove(homeName);
        }
    }

    public void loadHomes(Map<UUID, Map<String, HomeData>> homes) {
        this.playerHomes.clear();
        this.playerHomes.putAll(homes);
    }
    
    public Map<UUID, Map<String, HomeData>> getAllHomesMap() {
        return new HashMap<>(this.playerHomes);
    }
}
