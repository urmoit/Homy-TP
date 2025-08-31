package com.homemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.homemod.config.HomeConfig;
import com.homemod.data.HomeData;
import com.homemod.data.HomeManager;
import com.homemod.util.HomeUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SetHomeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("sethome")
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
        
        // Check if player already has a home with this name
        if (HomeManager.getInstance().hasHome(player.getUuid(), finalHomeName)) {
            source.sendError(HomeUtils.createHomeExistsMessage(finalHomeName));
            return 0;
        }
        
        // Check if player has reached the maximum number of homes
        if (!HomeConfig.canAddHome(player.getUuid())) {
            int maxHomes = HomeConfig.getMaxHomes();
            int currentHomes = HomeConfig.getCurrentHomeCount(player.getUuid());
            source.sendError(HomeUtils.createHomeLimitMessage(currentHomes, maxHomes));
            return 0;
        }
        
        Vec3d pos = player.getPos();
        BlockPos blockPos = player.getBlockPos();
        
        // Store home data
        HomeData homeData = new HomeData(finalHomeName, blockPos, player.getWorld().getRegistryKey().getValue());
        HomeManager.getInstance().setHome(player.getUuid(), homeData);
        
        // Save to config
        HomeConfig.saveHomes(HomeManager.getInstance().getAllHomesMap());
        
        // Send confirmation message to chat
        Text message = HomeUtils.createHomeLocationMessage(finalHomeName, blockPos);
        source.sendMessage(message);
        
        // Show home count
        int currentHomes = HomeConfig.getCurrentHomeCount(player.getUuid());
        int maxHomes = HomeConfig.getMaxHomes();
        source.sendMessage(HomeUtils.createHomeCountMessage(currentHomes, maxHomes));
        
        return 1;
    }
}
