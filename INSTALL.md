# HomeMod Installation Guide

## Prerequisites

Before installing HomeMod, you need:

1. **Minecraft 1.21.1** (Java Edition)
2. **Fabric Loader 0.17.0+** for Minecraft 1.21.1
3. **Fabric API** for Minecraft 1.21.1
4. **MymodLibrary** (your custom library mod)

## Installation Steps

### Step 1: Install Fabric Loader
1. Download Fabric Loader from [fabricmc.net](https://fabricmc.net/use/)
2. Run the installer and select Minecraft 1.21.1
3. Launch the game once to create the mods folder

### Step 2: Install Fabric API
1. Download Fabric API from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api) or [Modrinth](https://modrinth.com/mod/fabric-api)
2. Place the JAR file in your `mods` folder
3. Make sure it's for Minecraft 1.21.1

### Step 3: Install MymodLibrary
1. Build MymodLibrary first:
   ```bash
   cd MymodLibrary/fabric-example-mod
   ./gradlew build
   ```
2. Copy `build/libs/chosenlib-1.0.0.jar` to your `mods` folder

### Step 4: Install HomeMod
1. Build HomeMod:
   ```bash
   cd Homemod/fabric-example-mod
   ./gradlew build
   ```
2. Copy `build/libs/homemod-1.0.0.jar` to your `mods` folder

### Step 5: Verify Installation
1. Start Minecraft with the Fabric profile
2. Check the logs for "HomeMod initialized successfully!"
3. Join a world and try `/sethome` and `/home` commands

## File Structure
Your `mods` folder should contain:
```
mods/
├── fabric-api-0.116.5+1.21.1.jar
├── chosenlib-1.0.0.jar (MymodLibrary)
└── homemod-1.0.0.jar
```

## Troubleshooting

### "Mod not found" error
- Make sure all three mods are in the `mods` folder
- Verify you're using the correct Minecraft version (1.21.1)
- Check that Fabric Loader is properly installed

### Commands not working
- Ensure you're on a server or single-player world
- Check the logs for any error messages
- Verify MymodLibrary is loaded before HomeMod

### Build errors
- Make sure you have Java 21 installed
- Verify MymodLibrary is built first
- Check that all dependencies are resolved

## Commands Reference

| Command | Description | Example |
|---------|-------------|---------|
| `/sethome [name]` | Set your current location as home | `/sethome mansion` |
| `/home [name]` | Teleport to your home | `/home mansion` |

## Support

If you encounter issues:
1. Check the Minecraft logs for error messages
2. Verify all dependencies are correctly installed
3. Make sure you're using compatible versions

## License

HomeMod is licensed under the MIT License. Feel free to modify and distribute!
