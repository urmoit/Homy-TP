package com.homemod.commands;

import com.homemod.data.HomeData;
import com.homemod.data.HomeManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.mojang.brigadier.CommandDispatcher;

import java.util.Map;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.literal;
import com.mojang.authlib.GameProfile;

public class OphomesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("ophomes")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(context -> execute(context.getSource()))
        );
    }

    private static int execute(ServerCommandSource source) {
        Map<UUID, Map<String, HomeData>> allHomes = HomeManager.getInstance().getAllHomesMap();
        if (allHomes.isEmpty()) {
            source.sendMessage(Text.literal("§eNo homes found for any player."));
            return 1;
        }
        source.sendMessage(Text.literal("§a=== All Players' Homes ==="));
        for (Map.Entry<UUID, Map<String, HomeData>> playerEntry : allHomes.entrySet()) {
            UUID playerUUID = playerEntry.getKey();
            Map<String, HomeData> homes = playerEntry.getValue();
            String playerName = playerUUID.toString();
            GameProfile profile = source.getServer().getUserCache().getByUuid(playerUUID).orElse(null);
            if (profile != null && profile.getName() != null) {
                playerName = profile.getName();
            }
            source.sendMessage(Text.literal("§bPlayer: " + playerName));
            for (HomeData home : homes.values()) {
                Text homeInfo = Text.literal("§e" + home.getName() + "§7: §f" +
                        home.getPosition().getX() + ", " +
                        home.getPosition().getY() + ", " +
                        home.getPosition().getZ() +
                        " §8(" + home.getDimension().getPath() + ")");
                source.sendMessage(homeInfo);
            }
        }
        return 1;
    }
}
