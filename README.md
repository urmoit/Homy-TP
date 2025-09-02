# Homy TP

A simple, lightweight Fabric mod that adds home teleportation commands to Minecraft. Perfect for survival servers and SMPs!

## Features

- **/sethome [name]** — Set your home location (default name is "home").
- **/home [name]** — Instantly teleport to your home. The chunk is always loaded before teleporting, so you never get stuck in the void!
- **/homes** — List all your homes and their coordinates. Available to all players.
- **/removehome [name]** — Remove a home by name.
- **/ophomes** — (OP only) List every player’s homes on the server, including coordinates and dimension, with usernames.
- **/homy** — Shows mod version, clickable community links, and project pages.

## Commands

### /sethome
Sets your current location as your home. You can specify a custom name for multiple homes.

**Usage:**
- `/sethome` — Sets your current location as "home"
- `/sethome mansion` — Sets your current location as "mansion"

### /home
Teleports you to your home location. Always loads the chunk before teleporting.

**Usage:**
- `/home` — Teleports to "home"
- `/home mansion` — Teleports to "mansion"

### /homes
Lists all your homes and their coordinates. No OP required.

### /removehome
Removes a home by name.

**Usage:**
- `/removehome mansion` — Removes the home named "mansion"

### /ophomes
OP-only command. Lists all homes for all players, showing usernames and coordinates.

### /homy
Shows the mod version, clickable Discord, CurseForge, and GitHub links, and a Modrinth (coming soon) notice.

## Community & Links

- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/homy-tp)
- [GitHub](https://github.com/yourusername/homemod)
- [Community Discord](https://discord.gg/yourdiscord)
- Modrinth (coming soon)

## Dependencies

- Fabric Loader 0.17.0+
- Fabric API
- **MymodLibrary** (your custom library mod)

## Installation

1. Install Fabric Loader
2. Install Fabric API
3. Install Chosenlib
4. Install Homy TP
5. Start your server/client

## Server Compatibility

Works in both single-player and multiplayer. Each player can have their own homes. All commands (except /ophomes) are available to all players.

## Building

To build the mod:

```bash
./gradlew build
```

The built JAR will be in the `build/libs/` directory.

## License

MIT License — feel free to modify and distribute!

---

**Homy TP v1.2.0** — Cross-version ready! If you need support for a specific Minecraft version, let us know on Discord.

