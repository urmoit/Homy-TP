package com.homemod.events;

import com.homemod.config.HomeConfig;
import com.homemod.data.HomeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ServerEvents {
    
    public static void init() {
        // Save homes when server shuts down
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            HomeConfig.saveHomes(HomeManager.getInstance().getAllHomesMap());
        });
        
        // Auto-save homes periodically (every 5 minutes)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 6000 == 0) { // 6000 ticks = 5 minutes (20 ticks per second)
                HomeConfig.saveHomes(HomeManager.getInstance().getAllHomesMap());
            }
        });
    }
}




