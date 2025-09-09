package com.homemod.data;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackManager {

    private static BackManager instance;
    private final Map<UUID, LastLocation> lastLocations;

    private BackManager() {
        this.lastLocations = new HashMap<>();
    }

    public static BackManager getInstance() {
        if (instance == null) {
            instance = new BackManager();
        }
        return instance;
    }

    public void setLastLocation(UUID playerUuid, LastLocation location) {
        lastLocations.put(playerUuid, location);
    }

    public LastLocation getLastLocation(UUID playerUuid) {
        return lastLocations.get(playerUuid);
    }

    public boolean hasLastLocation(UUID playerUuid) {
        return lastLocations.containsKey(playerUuid);
    }

    public static class LastLocation {
        private final RegistryKey<World> dimension;
        private final Vec3d pos;

        public LastLocation(RegistryKey<World> dimension, Vec3d pos) {
            this.dimension = dimension;
            this.pos = pos;
        }

        public RegistryKey<World> getDimension() {
            return dimension;
        }

        public Vec3d getPos() {
            return pos;
        }
    }
}
