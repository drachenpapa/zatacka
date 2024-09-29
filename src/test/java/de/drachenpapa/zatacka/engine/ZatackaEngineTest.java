package de.drachenpapa.zatacka.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ZatackaEngineTest {
    private Player player1;
    private Player player2;
    private Player[] players;
    private ZatackaEngine engine;

    @BeforeEach
    void setUp() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        players = new Player[]{player1, player2};
        engine = new ZatackaEngine(players, 3);
    }

    @Test
    void testEngineInitialization() {
        assertThat("Players should be initialized correctly.", engine.getPlayers(), is(players));
    }

    @Test
    void testStartNextRoundResetsAliveStatus() {
        engine.startNextRound();
        verify(player1, times(1)).setCurve(any());
        verify(player2, times(1)).setCurve(any());
    }

    @Test
    void testCheckCollisionWithBoundary() {
        Curve curve = mock(Curve.class);
        when(curve.getXPosition()).thenReturn(ZatackaEngine.SCREEN_WIDTH + 1);
        when(curve.getYPosition()).thenReturn(100);
        when(curve.isAlive()).thenReturn(true);

        boolean collisionDetected = engine.checkCollision(curve);

        assertThat("Collision should be detected at the boundary.", collisionDetected, is(true));
        verify(curve, times(1)).setXPosition(4);
    }

    @Test
    void testDetectCurveCollisionWithOccupiedSpace() {
        Curve curve = mock(Curve.class);
        when(curve.getXPosition()).thenReturn(10);
        when(curve.getYPosition()).thenReturn(10);
        when(curve.isAlive()).thenReturn(true);

        engine.startNextRound();

        engine.gameField[10][10] = true;

        boolean collisionDetected = engine.detectCurveCollision(curve);

        assertThat("Collision should be detected with another curve.", collisionDetected, is(true));
    }

    @Test
    void testDrawScoresDisplaysCorrectInformation() {
        when(player1.getColor()).thenReturn(Color.RED);
        when(player1.getPlayerName()).thenReturn("Player 1");
        when(player2.getColor()).thenReturn(Color.BLUE);
        when(player2.getPlayerName()).thenReturn("Player 2");

        Statistics statisticsMock = mock(Statistics.class);
        when(statisticsMock.getScores()).thenReturn(new int[]{5, 3});

        engine.statistics = statisticsMock;

        Graphics g = mock(Graphics.class);
        engine.drawScores(g);

        verify(g, times(1)).setColor(Color.GRAY);
        verify(g, times(1)).fillRect(682, 3, 115, ZatackaEngine.SCREEN_HEIGHT);
        verify(g, times(1)).setFont(new Font("SANS_SERIF", Font.BOLD, 16));
        verify(g, times(1)).drawString("Player 1", 690, 30);
        verify(g, times(1)).drawString("5 pts", 690, 50);
        verify(g, times(1)).drawString("Player 2", 690, 80);
        verify(g, times(1)).drawString("3 pts", 690, 100);
    }

    @Test
    void testGameEndsWhenPlayerReachesMaxScore() {
        Statistics statisticsMock = mock(Statistics.class);
        when(statisticsMock.getScores()).thenReturn(new int[]{10, 3});

        engine.statistics = statisticsMock;

        Graphics g = mock(Graphics.class);
        engine.drawScores(g);

        verify(statisticsMock, times(1)).setPlayerDead(anyInt());
    }

    @Test
    void testPlayerMarkedAsDeadOnCollision() {
        Curve curve = mock(Curve.class);
        when(curve.isAlive()).thenReturn(true);
        when(curve.getXPosition()).thenReturn(10);
        when(curve.getYPosition()).thenReturn(10);
        when(player1.getCurve()).thenReturn(curve);

        engine.checkCollision(curve);

        verify(curve, times(1)).markAsDead();
    }
}
