package de.drachenpapa.zatacka.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CurveTest {

    private Curve curve;

    @BeforeEach
    public void setUp() {
        curve = new Curve(0, 0, 90, 2000);
    }

    @Test
    public void testTurnLeft() {
        curve.turnLeft();
        assertThat("Direction angle should increase by TURN_ANGLE when turning left", curve.getDirectionAngle(), is(100.0));
    }

    @Test
    public void testTurnRight() {
        curve.turnRight();
        assertThat("Direction angle should decrease by TURN_ANGLE when turning right", curve.getDirectionAngle(), is(80.0));
    }

    @Test
    public void testMarkAsDead() {
        curve.markAsDead();
        assertThat("Curve should be marked as dead after calling markAsDead", curve.isAlive(), is(false));
    }
}
