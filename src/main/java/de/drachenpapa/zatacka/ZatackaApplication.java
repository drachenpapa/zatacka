package de.drachenpapa.zatacka;

import de.drachenpapa.zatacka.ui.SettingsUI;

/**
 * Entry point for the Zatacka game application.
 * Initializes and displays the pre-dialog for game options.
 * <p>
 * Author: Henning Steinberg (@drachenpapa)
 * Version: 1.0
 */
public class ZatackaApplication {

    /**
     * Main method to start the Zatacka application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new SettingsUI();
    }
}
