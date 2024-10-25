#!/bin/bash

APP_NAME="Zatacka"
MAIN_CLASS="de.drachenpapa.zatacka.ZatackaApplication"
JAR_PATH="./target/zatacka-1.0-SNAPSHOT.jar"
ICON_PATH="./icon/icon.png"

cd ..

echo "Building the project with Maven..."
mvn clean package

if [ ! -f "$JAR_PATH" ]; then
    echo "Error: JAR file not found at $JAR_PATH. Build might have failed."
    exit 1
fi

OS=$(uname -s)

echo "Starting jpackage for ${APP_NAME} on ${OS}..."

if [[ "$OS" == "Linux" ]]; then
    echo "Creating .deb for Linux..."
    jpackage --name "$APP_NAME" \
             --input "./target" \
             --main-jar "$(basename "$JAR_PATH")" \
             --main-class "$MAIN_CLASS" \
             --type "deb" \
             --dest "./dist"
#             --icon "$ICON_PATH" \

elif [[ "$OS" == "Darwin" ]]; then
    echo "Creating .pkg for macOS..."
    jpackage --name "$APP_NAME" \
             --input "./target" \
             --main-jar "$(basename "$JAR_PATH")" \
             --main-class "$MAIN_CLASS" \
             --type "pkg" \
             --dest "./dist"
#             --icon "$ICON_PATH" \

elif [[ "$OS" == "MINGW"* || "$OS" == "CYGWIN"* || "$OS" == "MSYS_NT"* ]]; then
    echo "Creating .exe for Windows..."
    jpackage --name "$APP_NAME" \
             --input "./target" \
             --main-jar "$(basename "$JAR_PATH")" \
             --main-class "$MAIN_CLASS" \
             --type "exe" \
             --dest "./dist"
#             --icon "$ICON_PATH" \

else
    echo "Operating system is not supported!"
    exit 1
fi

echo "jpackage completed.
