package de.drachenpapa.zatacka.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class GameEngineTest {
    private Player[] players;
    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        players = new Player[] {
                new Player("Player 1", Color.RED, '1', 'q'),
                new Player("Player 2", Color.BLUE, 'y', 'x')};
        gameEngine = new GameEngine(players, 3);
    }

    @Test
    void testStartNextRound() {
        gameEngine.startNextRound();
        for (Player player : players) {
            assertThat("Player's curve should not be null after starting a new round", player.getCurve(), is(notNullValue()));
            assertThat("Player's curve should be alive after starting a new round", player.getCurve().isAlive(), is(true));
        }
    }

    @Test
    void testCheckCollisionNoCollision() {
        Curve curve = new Curve(100, 100, 0, 1);
        assertThat("Collision check should return false for non-colliding curve", gameEngine.checkCollision(curve), is(false));
    }

    @Test
    void testDetectCurveCollisionWithOccupiedSpace() {
        Curve curve = new Curve(10, 10, 0, 1);
        gameEngine.getPlayers()[0].setCurve(curve);
        gameEngine.checkCollision(curve);
        assertThat("Detecting curve collision should return true when the curve occupies an already occupied space", gameEngine.detectCurveCollision(curve), is(true));
    }
}
