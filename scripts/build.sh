#!/bin/bash

APP_NAME="Zatacka"
MAIN_CLASS="de.drachenpapa.zatacka.ZatackaApplication"
JAR_PATH="./target/zatacka-1.0-SNAPSHOT.jar"
ICON_PATH="./icon/icon.png"
DIST_DIR="./dist"

echo "Building the project with Maven..."
mvn clean package

if [ ! -f "$JAR_PATH" ]; then
    echo "Error: JAR file not found at $JAR_PATH. Build might have failed."
    exit 1
fi

mkdir -p "$DIST_DIR"

echo "Creating app-image..."
jpackage --name "$APP_NAME" \
         --input "./target" \
         --main-jar "$(basename "$JAR_PATH")" \
         --main-class "$MAIN_CLASS" \
         --type "app-image" \
         --dest "$DIST_DIR"
#         --icon "$ICON_PATH" \

echo "Build and packaging done: $APP_NAME"
