package com.homemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.homemod.config.HomeConfig;
import com.homemod.data.HomeData;
import com.homemod.data.HomeManager;
import com.homemod.util.HomeUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class HomeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("home")
            .executes(context -> execute(context.getSource(), null))
            .then(argument("name", StringArgumentType.word())
                .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "name")))
            )
        );
    }
    
    private static int execute(ServerCommandSource source, String homeName) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("This command can only be used by players!"));
            return 0;
        }
        
        String finalHomeName = homeName != null ? homeName : "home";
        
        // Get home data from manager
        HomeData homeData = HomeManager.getInstance().getHome(player.getUuid(), finalHomeName);
        
        if (homeData == null) {
            source.sendError(HomeUtils.createHomeNotFoundMessage(finalHomeName));
            return 0;
        }
        
        // Get the target world/dimension
        ServerWorld targetWorld = player.getServer().getWorld(
            net.minecraft.registry.RegistryKey.of(
                net.minecraft.registry.RegistryKeys.WORLD, 
                homeData.getDimension()
            )
        );
        
        if (targetWorld == null) {
            source.sendError(Text.literal("§cError: Target dimension not found!"));
            return 0;
        }
        
        // Get home position
        BlockPos homePos = homeData.getPosition();
        // Load the chunk before teleporting
        targetWorld.getChunk(homePos.getX() >> 4, homePos.getZ() >> 4, net.minecraft.world.chunk.ChunkStatus.FULL, true);
        Vec3d teleportPos = new Vec3d(
            homePos.getX() + 0.5, 
            homePos.getY() + 1.0, 
            homePos.getZ() + 0.5
        );
        
        // Check if we need to change dimensions
        boolean isSameDimension = player.getWorld().getRegistryKey().getValue().equals(homeData.getDimension());
        
        if (isSameDimension) {
            // Same dimension - simple teleport
            player.teleport(teleportPos.x, teleportPos.y, teleportPos.z, false);
        } else {
            // Different dimension - change world and teleport
            player.teleport(targetWorld, teleportPos.x, teleportPos.y, teleportPos.z, 
                player.getYaw(), player.getPitch());
        }
        
        // Save to config to ensure data persistence
        HomeConfig.saveHomes(HomeManager.getInstance().getAllHomesMap());
        
        // Send teleporting message to action bar (center of screen)
        Text message = HomeUtils.createTeleportingMessage(finalHomeName);
        player.sendMessage(message, true); // true = action bar (center of screen)
        
        // Send dimension info to chat
        Text dimensionMessage = Text.literal("§7Dimension: " + homeData.getDimension().getPath());
        source.sendMessage(dimensionMessage);
        
        // Send confirmation message to chat
        Text chatMessage = Text.literal("§aSuccessfully teleported to home '" + finalHomeName + "'!");
        source.sendMessage(chatMessage);
        
        return 1;
    }
}
