package de.drachenpapa.zatacka;

import de.drachenpapa.zatacka.ui.SettingsUI;

/**
 * The {@code ZatackaApplication} class serves as the entry point for the Zatacka game application.
 * It initializes the user interface for game settings and starts the application.
 *
 * <p>
 * This class is responsible for launching the game by displaying the {@link SettingsUI},
 * where users can configure game options before starting the actual gameplay.
 * </p>
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class ZatackaApplication {

    /**
     * The main method serves as the starting point for the Zatacka application.
     *
     * This method is responsible for displaying the {@link SettingsUI} to the user,
     * allowing them to configure game options before starting the game.
     *
     * @param args Command-line arguments (not utilized in this application).
     */
    public static void main(String[] args) {
        new SettingsUI();
    }
}
