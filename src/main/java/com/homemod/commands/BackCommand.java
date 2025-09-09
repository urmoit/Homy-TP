package com.homemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.homemod.data.BackManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class BackCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("back")
            .executes(context -> execute(context.getSource()))
        );
    }

    private static int execute(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("This command can only be used by players!"));
            return 0;
        }

        if (!BackManager.getInstance().hasLastLocation(player.getUuid())) {
            source.sendError(Text.literal("You have no previous location to teleport back to."));
            return 0;
        }

        BackManager.LastLocation lastLocation = BackManager.getInstance().getLastLocation(player.getUuid());
        World world = source.getServer().getWorld(lastLocation.getDimension());

        if (world == null) {
            source.sendError(Text.literal("The world you were in no longer exists."));
            return 0;
        }
        
        // Store current location for future /back
        BackManager.getInstance().setLastLocation(player.getUuid(), new BackManager.LastLocation(player.getWorld().getRegistryKey(), player.getPos()));

        player.teleport(
            source.getServer().getWorld(lastLocation.getDimension()),
            lastLocation.getPos().x,
            lastLocation.getPos().y,
            lastLocation.getPos().z,
            player.getYaw(),
            player.getPitch()
        );

        source.sendMessage(Text.literal("Teleporting to your last location..."));

        return 1;
    }
}
