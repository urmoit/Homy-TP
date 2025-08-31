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

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RemoveHomeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("removehome")
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
        
        if (homeName == null || homeName.isEmpty()) {
            source.sendError(Text.literal("Please specify a home name to remove!"));
            return 0;
        }
        
        // Check if the home exists
        HomeData homeData = HomeManager.getInstance().getHome(player.getUuid(), homeName);
        if (homeData == null) {
            source.sendError(HomeUtils.createHomeNotFoundMessage(homeName));
            return 0;
        }
        
        // Remove the home
        HomeManager.getInstance().removeHome(player.getUuid(), homeName);
        
        // Save to config
        HomeConfig.saveHomes(HomeManager.getInstance().getAllHomesMap());
        
        // Send confirmation message
        Text message = Text.literal("§aHome '" + homeName + "' has been removed successfully!");
        source.sendMessage(message);
        
        // Show updated home count
        int currentHomes = HomeConfig.getCurrentHomeCount(player.getUuid());
        int maxHomes = HomeConfig.getMaxHomes();
        source.sendMessage(HomeUtils.createHomeCountMessage(currentHomes, maxHomes));
        
        return 1;
    }
}
