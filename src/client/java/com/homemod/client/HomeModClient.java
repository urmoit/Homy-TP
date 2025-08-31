package com.homemod.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("HomeMod-Client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("HomeMod client initialized!");
        // Client-side initialization can go here
        // For now, the commands are handled server-side
    }
}
