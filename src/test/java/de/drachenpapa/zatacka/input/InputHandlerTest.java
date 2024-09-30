package de.drachenpapa.zatacka.input;

import de.drachenpapa.zatacka.game.GameEngine;
import de.drachenpapa.zatacka.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InputHandlerTest {
    private GameEngine engine;
    private Player player1;
    private Player player2;
    private InputHandler inputHandler;

    @BeforeEach
    void setUp() {
        engine = mock(GameEngine.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        Player[] players = new Player[]{player1, player2};
        when(engine.getPlayers()).thenReturn(players);

        inputHandler = new InputHandler(engine);
    }

    @Test
    void testKeyPressedLeftKey() {
        char leftKey = 'A';
        when(player1.getLeftKey()).thenReturn(leftKey);

        KeyEvent keyEvent = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 0, leftKey);
        inputHandler.keyPressed(keyEvent);

        verify(player1).setLeftKeyPressed(true);
        verify(player2, never()).setLeftKeyPressed(true);
    }

    @Test
    void testKeyPressedRightKey() {
        char rightKey = 'D';
        when(player1.getRightKey()).thenReturn(rightKey);

        KeyEvent keyEvent = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 0, rightKey);
        inputHandler.keyPressed(keyEvent);

        verify(player1).setRightKeyPressed(true);
        verify(player2, never()).setRightKeyPressed(true);
    }

    @Test
    void testKeyPressedEscapeKey() {
        KeyEvent keyEvent = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        inputHandler.keyPressed(keyEvent);

        verify(engine).quitGame();
    }

    @Test
    void testKeyReleasedLeftKey() {
        char leftKey = 'A';
        when(player1.getLeftKey()).thenReturn(leftKey);

        KeyEvent keyEvent = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, 0, leftKey);
        inputHandler.keyReleased(keyEvent);

        verify(player1).setLeftKeyPressed(false);
        verify(player2, never()).setLeftKeyPressed(false);
    }

    @Test
    void testKeyReleasedRightKey() {
        char rightKey = 'D';
        when(player1.getRightKey()).thenReturn(rightKey);

        KeyEvent keyEvent = new KeyEvent(new java.awt.Component() {}, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, 0, rightKey);
        inputHandler.keyReleased(keyEvent);

        verify(player1).setRightKeyPressed(false);
        verify(player2, never()).setRightKeyPressed(false);
    }
}
