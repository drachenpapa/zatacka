package de.drachenpapa.zatacka.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class PlayerTest {
    private Player player;
    private Curve newCurve;

    @BeforeEach
    void setUp() {
        player = new Player("Test Player", Color.RED, 'a', 'd');
        newCurve = new Curve(200, 300, 90, 5);
    }

    @Test
    void testInitialPlayerName() {
        assertThat("Player name should match the expected value", player.getPlayerName(), is("Test Player"));
    }

    @Test
    void testInitialColor() {
        assertThat("Player color should match the expected value", player.getColor(), is(Color.RED));
    }

    @Test
    void testInitialCurve() {
        assertThat("Player's curve should not be null upon initialization", player.getCurve(), is(notNullValue()));
    }

    @Test
    void testSetCurve() {
        player.setCurve(newCurve);
        assertThat("Player's curve should match the newly set curve", player.getCurve(), is(newCurve));
    }

    @Test
    void testLeftKeyPressed() {
        assertThat("Left key should not be pressed initially", player.isLeftKeyPressed(), is(false));
        player.setLeftKeyPressed(true);
        assertThat("Left key should be pressed after being set", player.isLeftKeyPressed(), is(true));
    }

    @Test
    void testRightKeyPressed() {
        assertThat("Right key should not be pressed initially", player.isRightKeyPressed(), is(false));
        player.setRightKeyPressed(true);
        assertThat("Right key should be pressed after being set", player.isRightKeyPressed(), is(true));
    }
}
