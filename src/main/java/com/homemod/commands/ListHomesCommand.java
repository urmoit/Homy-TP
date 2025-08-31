package com.homemod.commands;

import com.homemod.config.HomeConfig;
import com.homemod.data.HomeData;
import com.homemod.data.HomeManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;

public class ListHomesCommand {
    
    public static void register(com.mojang.brigadier.CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("homes")
            .executes(context -> execute(context.getSource()))
        );
    }
    
    private static int execute(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("This command can only be used by players!"));
            return 0;
        }
        
        Map<String, HomeData> playerHomes = HomeManager.getInstance().getAllHomes(player.getUuid());
        int currentHomes = playerHomes.size();
        int maxHomes = HomeConfig.getMaxHomes();
        
        if (currentHomes == 0) {
            source.sendMessage(Text.literal("§eYou don't have any homes set. Use /sethome [name] to create one!"));
            source.sendMessage(Text.literal("§7You can have up to " + maxHomes + " homes."));
            return 1;
        }
        
        // Show home count
        source.sendMessage(Text.literal("§a=== Your Homes (" + currentHomes + "/" + maxHomes + ") ==="));
        
        // List all homes
        for (Map.Entry<String, HomeData> entry : playerHomes.entrySet()) {
            HomeData home = entry.getValue();
            Text homeInfo = Text.literal("§e" + home.getName() + "§7: §f" + 
                home.getPosition().getX() + ", " + 
                home.getPosition().getY() + ", " + 
                home.getPosition().getZ() + 
                " §8(" + home.getDimension().getPath() + ")");
            source.sendMessage(homeInfo);
        }
        
        if (currentHomes < maxHomes) {
            source.sendMessage(Text.literal("§7You can create " + (maxHomes - currentHomes) + " more home(s)."));
        }
        
        return 1;
    }
}
