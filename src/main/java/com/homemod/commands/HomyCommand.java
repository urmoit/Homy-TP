package com.homemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.homemod.HomeMod;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class HomyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("homy")
            .executes(context -> execute(context.getSource()))
        );
    }

    private static int execute(ServerCommandSource source) {
        // Version
        source.sendMessage(Text.literal("§aHomy TP §7- Version: §e" + HomeMod.getVersion()));

        // Discord
        MutableText discord = Text.literal("§9Community Discord").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/yourdiscord")));
        source.sendMessage(Text.literal("§b[ ").append(discord).append(Text.literal(" §b]")));

        // CurseForge
        MutableText curseforge = Text.literal("§6CurseForge").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/homy-tp")));
        source.sendMessage(Text.literal("§b[ ").append(curseforge).append(Text.literal(" §b]")));

        // Modrinth (not clickable)
        source.sendMessage(Text.literal("§b[ §aModrinth (coming soon) §b]"));

        // GitHub
        MutableText github = Text.literal("§7GitHub").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/yourusername/homemod")));
        source.sendMessage(Text.literal("§b[ ").append(github).append(Text.literal(" §b]")));

        return 1;
    }
}

