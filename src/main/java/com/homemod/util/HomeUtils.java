package com.homemod.util;

import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class HomeUtils {
    
    /**
     * Creates a formatted home location message
     */
    public static Text createHomeLocationMessage(String homeName, BlockPos position) {
        return Text.literal("§aHome '" + homeName + "' set at: §e" + 
            position.getX() + ", " + position.getY() + ", " + position.getZ());
    }
    
    /**
     * Creates a teleporting message for the action bar
     */
    public static Text createTeleportingMessage(String homeName) {
        return Text.literal("§bTeleporting to home '" + homeName + "'...");
    }
    
    /**
     * Creates an error message for missing homes
     */
    public static Text createHomeNotFoundMessage(String homeName) {
        return Text.literal("§cHome '" + homeName + "' not found! Use /sethome first.");
    }
    
    /**
     * Creates a home limit reached message
     */
    public static Text createHomeLimitMessage(int currentHomes, int maxHomes) {
        return Text.literal("§cYou have reached the maximum number of homes (" + maxHomes + ")! Remove a home first with /removehome <name>.");
    }
    
    /**
     * Creates a home count message
     */
    public static Text createHomeCountMessage(int currentHomes, int maxHomes) {
        return Text.literal("§7You have " + currentHomes + "/" + maxHomes + " homes.");
    }
    
    /**
     * Creates a home already exists message
     */
    public static Text createHomeExistsMessage(String homeName) {
        return Text.literal("§cYou already have a home named '" + homeName + "'! Use /removehome first or choose a different name.");
    }
}
