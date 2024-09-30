package de.drachenpapa.zatacka.input;

import de.drachenpapa.zatacka.game.GameEngine;
import de.drachenpapa.zatacka.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.event.KeyEvent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class InputHandlerTest {

    private GameEngine gameEngine;
    private InputHandler inputHandler;
    private Player player;

    @BeforeEach
    void setUp() {
        gameEngine = mock(GameEngine.class);
        player = new Player("Test Player", Color.RED, 'a', 'd');

        inputHandler = new InputHandler(gameEngine);

        when(gameEngine.getPlayers()).thenReturn(new Player[]{player});
    }

    @Test
    void testKeyPressedLeftKey() {
        KeyEvent leftKeyEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');
        inputHandler.keyPressed(leftKeyEvent);

        assertThat("The left key should be pressed", player.isLeftKeyPressed(), is(true));
        assertThat("The right key should not be pressed", player.isRightKeyPressed(), is(false));
    }

    @Test
    void testKeyPressedRightKey() {
        KeyEvent rightKeyEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'd');
        inputHandler.keyPressed(rightKeyEvent);

        assertThat("The right key should be pressed", player.isRightKeyPressed(), is(true));
        assertThat("The left key should not be pressed", player.isLeftKeyPressed(), is(false));
    }

    @Test
    void testKeyPressedEscapeKey() {
        KeyEvent escapeKeyEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        inputHandler.keyPressed(escapeKeyEvent);

        verify(gameEngine, times(1)).quitGame();
    }

    @Test
    void testKeyReleasedLeftKey() {
        player.setLeftKeyPressed(true); // Simulate left key pressed
        KeyEvent leftKeyReleaseEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');
        inputHandler.keyReleased(leftKeyReleaseEvent);

        assertThat("The left key should not be pressed after release", player.isLeftKeyPressed(), is(false));
    }

    @Test
    void testKeyReleasedRightKey() {
        player.setRightKeyPressed(true); // Simulate right key pressed
        KeyEvent rightKeyReleaseEvent = new KeyEvent(new java.awt.Canvas(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'd');
        inputHandler.keyReleased(rightKeyReleaseEvent);

        assertThat("The right key should not be pressed after release", player.isRightKeyPressed(), is(false));
    }
}
