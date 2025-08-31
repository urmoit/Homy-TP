# HomeMod

A simple Fabric mod that adds home teleportation functionality to Minecraft.

## Features

- **/sethome [name]** - Set your home location (default name is "home")
- **/home [name]** - Teleport to your home location (default name is "home")

## Commands

### /sethome
Sets your current location as your home. You can specify a custom name for multiple homes.

**Usage:**
- `/sethome` - Sets your current location as "home"
- `/sethome mansion` - Sets your current location as "mansion"

**Response:** Confirmation message in chat showing the coordinates where home was set.

### /home
Teleports you to your home location. Shows "Teleporting..." message on screen.

**Usage:**
- `/home` - Teleports to "home"
- `/home mansion` - Teleports to "mansion"

**Response:** "Teleporting to home [name]..." message displayed on screen.

## Dependencies

This mod requires:
- Fabric Loader 0.17.0+
- Fabric API
- **MymodLibrary** (your custom library mod)

## Installation

1. Install Fabric Loader for Minecraft 1.21.1
2. Install Fabric API
3. Install MymodLibrary
4. Install HomeMod
5. Start your server/client

## Server Compatibility

The commands work on both single-player and multiplayer servers. Each player can have their own homes, and the commands are available to all players on the server.

## Building

To build the mod:

```bash
./gradlew build
```

The built JAR will be in `build/libs/` directory.

## License

MIT License - feel free to modify and distribute!
