package de.drachenpapa.zatacka.engine;

import java.awt.event.*;

/**
 * This class handles key events and calls the appropriate methods.
 * It controls the movement of the curves.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class GameInputHandler extends KeyAdapter {
    private final ZatackaEngine ref;

    /**
     * Creates a control object for managing the curves.
     *
     * @param ref Reference to the Zatacka object to call methods for curve movement.
     */
    public GameInputHandler(ZatackaEngine ref) {
        this.ref = ref;
    }

    /**
     * Responds to a key press event.
     *
     * @param e The KeyEvent triggered by a key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (keyChar == KeyEvent.VK_ESCAPE) {
            ref.quit();
            return;
        }
        handleKeyPress(keyChar);
    }

    /**
     * Responds to a key release event.
     *
     * @param e The KeyEvent triggered by a key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        handleKeyRelease(e.getKeyChar());
    }

    /**
     * Handles the logic for key press events for all players.
     *
     * @param keyChar The character representation of the key pressed.
     */
    private void handleKeyPress(char keyChar) {
        for (Player player : ref.getPlayers()) {
            if (player.getLeftControl() == keyChar) {
                player.setLeftPressed(true);
            } else if (player.getRightControl() == keyChar) {
                player.setRightPressed(true);
            }
        }
    }

    /**
     * Handles the logic for key release events for all players.
     *
     * @param keyChar The character representation of the key released.
     */
    private void handleKeyRelease(char keyChar) {
        for (Player player : ref.getPlayers()) {
            if (player.getLeftControl() == keyChar) {
                player.setLeftPressed(false);
            } else if (player.getRightControl() == keyChar) {
                player.setRightPressed(false);
            }
        }
    }
}
