package de.drachenpapa.zatacka.input;

import de.drachenpapa.zatacka.game.GameEngine;
import de.drachenpapa.zatacka.game.Player;
import lombok.RequiredArgsConstructor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The {@code InputHandler} class manages key events during gameplay.
 * It updates the players' controls based on key presses and releases,
 * allowing them to turn their curves left or right. The class also handles quitting the game
 * when the escape key is pressed.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
@RequiredArgsConstructor
public class InputHandler extends KeyAdapter {

    /** Reference to the {@link GameEngine} instance, used to control player curves and game logic. */
    private final GameEngine engine;

    /**
     * Invoked when a key is pressed. Updates the player's control state based on the key input.
     * Handles turning the curves and quitting the game.
     *
     * @param e The {@link KeyEvent} representing the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        char pressedKeyChar = e.getKeyChar();
        int pressedKeyCode = e.getKeyCode();

        for (Player player : engine.getPlayers()) {
            if (player.getLeftKey() == pressedKeyChar) {
                player.setLeftKeyPressed(true);
            } else if (player.getRightKey() == pressedKeyChar) {
                player.setRightKeyPressed(true);
            } else if (pressedKeyCode == KeyEvent.VK_ESCAPE) {
                engine.quitGame();
            }
        }
    }


    /**
     * Invoked when a key is released. Resets the player's control state when the key is no longer pressed.
     *
     * @param e The {@link KeyEvent} representing the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        char releasedKey = e.getKeyChar();

        for (Player player : engine.getPlayers()) {
            if (player.getLeftKey() == releasedKey) {
                player.setLeftKeyPressed(false);
            } else if (player.getRightKey() == releasedKey) {
                player.setRightKeyPressed(false);
            }
        }
    }
}
