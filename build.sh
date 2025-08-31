#!/bin/bash

echo "Building HomeMod..."
echo

echo "Building HomeMod..."
echo
echo "Note: Make sure MymodLibrary is built and available as a mod dependency"
echo

# Build the mod
./gradlew build

if [ $? -eq 0 ]; then
    echo
    echo "Build successful! JAR file is in build/libs/"
    echo
    echo "To install:"
echo "1. Copy the JAR from build/libs/ to your mods folder"
echo "2. Make sure MymodLibrary is also installed as a separate mod"
echo "3. Restart Minecraft"
    echo
else
    echo
    echo "Build failed! Check the error messages above."
    echo
fi
