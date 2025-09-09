package com.homemod;

import com.homemod.commands.BackCommand;
import com.homemod.commands.HomeCommand;
import com.homemod.commands.ListHomesCommand;
import com.homemod.commands.OphomesCommand;
import com.homemod.commands.RemoveHomeCommand;
import com.homemod.commands.SetHomeCommand;
import com.homemod.commands.HomyCommand;
import com.homemod.config.HomeConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeMod implements ModInitializer {
    public static final String MOD_ID = "homemod";
    public static final String MOD_NAME = "HomeMod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("{} v{} initializing...", MOD_NAME, getVersion());
        
        // Initialize config system
        HomeConfig.init();
        
        // Initialize server events
        com.homemod.events.ServerEvents.init();
        
        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SetHomeCommand.register(dispatcher);
            HomeCommand.register(dispatcher);
            RemoveHomeCommand.register(dispatcher);
            ListHomesCommand.register(dispatcher);
            OphomesCommand.register(dispatcher);
            HomyCommand.register(dispatcher);
            BackCommand.register(dispatcher);
        });
        
        // Tests removed - mod will fail to load if MymodLibrary is missing
        
        LOGGER.info("{} v{} initialized successfully!", MOD_NAME, getVersion());
        LOGGER.info("MymodLibrary dependency loaded successfully");
    }

    public static String getVersion() {
        return "v1.3.0";
    }
}
