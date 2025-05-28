package de.drachenpapa.zatacka;

import de.drachenpapa.zatacka.settings.SettingsUI;

/**
 * Main entry point for the Zatacka game application.
 * Initializes and starts the game UI.
 */
public class ZatackaApplication {

    public static final String GAME_TITLE = "Zatacka";

    public static void main(String[] args) {
        new SettingsUI();
    }
}
