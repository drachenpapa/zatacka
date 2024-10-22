package de.drachenpapa.zatacka.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatisticsTest {
    private Statistics statistics;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        statistics = new Statistics(3);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        player3 = mock(Player.class);
    }

    @Test
    void testInitialScores() {
        assertThat("Scores should initially be zero for all players.", statistics.getScores(), is(new int[]{0, 0, 0}));
    }

    @Test
    void testInitialAliveStatus() {
        assertThat("All players should be alive initially.", statistics.getAlivePlayerCount(), is(3));
    }

    @Test
    void testIncreasePoints() {
        Curve curve1 = mock(Curve.class);
        Curve curve2 = mock(Curve.class);
        Curve curve3 = mock(Curve.class);

        when(player1.getCurve()).thenReturn(curve1);
        when(player2.getCurve()).thenReturn(curve2);
        when(player3.getCurve()).thenReturn(curve3);

        when(curve1.isAlive()).thenReturn(true);
        when(curve2.isAlive()).thenReturn(true);
        when(curve3.isAlive()).thenReturn(false);

        statistics.increasePoints(new Player[]{player1, player2, player3});

        assertThat("Scores should be incremented for alive players.", statistics.getScores(), is(new int[]{1, 1, 0}));
    }

    @Test
    void testSetPlayerDead() {
        statistics.setPlayerDead(1);
        assertThat("There should be 2 players alive after one is marked dead.", statistics.getAlivePlayerCount(), is(2));
    }

    @Test
    void testSetAllAlive() {
        statistics.setPlayerDead(0);
        statistics.setPlayerDead(1);
        statistics.setAllAlive();

        assertThat("All players should be alive after calling setAllAlive.", statistics.getAlivePlayerCount(), is(3));
    }
}
