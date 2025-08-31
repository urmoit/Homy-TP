package com.homemod.data;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;

public class HomeData {
    private final String name;
    private final BlockPos position;
    private final Identifier dimension;
    
    public HomeData(String name, BlockPos position, Identifier dimension) {
        this.name = name;
        this.position = position;
        this.dimension = dimension;
    }
    
    public String getName() {
        return name;
    }
    
    public BlockPos getPosition() {
        return position;
    }
    
    public Identifier getDimension() {
        return dimension;
    }
    
    @Override
    public String toString() {
        return "HomeData{name='" + name + "', position=" + position + ", dimension=" + dimension + "}";
    }
}
