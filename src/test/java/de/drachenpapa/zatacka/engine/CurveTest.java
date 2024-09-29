package de.drachenpapa.zatacka.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

class CurveTest {
    private Curve curve;

    @BeforeEach
    void setUp() {
        curve = new Curve(100.0, 150.0, 90.0, 2000L);
    }

    @Test
    void testGetXPosition() {
        assertThat("The X position should be 100", curve.getXPosition(), is(100));
    }

    @Test
    void testGetYPosition() {
        assertThat("The Y position should be 150", curve.getYPosition(), is(150));
    }

    @Test
    void testMove() {
        curve.move();
        assertThat("After moving, the X position should be greater than the initial value of 100", curve.getXPosition(), is(greaterThan(100)));
        assertThat("After moving, the Y position should be less than the initial value of 150", curve.getYPosition(), is(lessThan(150)));
    }

    @Test
    void testTurnLeft() {
        double initialX = curve.getXPosition();
        double initialY = curve.getYPosition();

        curve.turnLeft();
        curve.move();

        assertThat("After turning left and moving, the curve should still be alive", curve.isAlive(), is(true));
        assertThat("The X position should have changed after moving", curve.getXPosition(), is(not(initialX)));
        assertThat("The Y position should have changed after moving", curve.getYPosition(), is(not(initialY)));
    }

    @Test
    void testTurnRight() {
        double initialX = curve.getXPosition();
        double initialY = curve.getYPosition();

        curve.turnRight();
        curve.move();

        assertThat("After turning right and moving, the curve should still be alive", curve.isAlive(), is(true));
        assertThat("The X position should have changed after moving", curve.getXPosition(), is(not(initialX)));
        assertThat("The Y position should have changed after moving", curve.getYPosition(), is(not(initialY)));
    }

    @Test
    void testIsAliveInitially() {
        assertThat("The curve should be alive initially", curve.isAlive(), is(true));
    }

    @Test
    void testMarkAsDead() {
        curve.markAsDead();
        assertThat("The curve should not be alive after being marked as dead", curve.isAlive(), is(false));
    }

    @Test
    void testIsGeneratingGapInitially() {
        assertThat("The curve should not generate a gap initially", curve.isGeneratingGap(), is(false));
    }

    @Test
    void testGapGeneration() throws InterruptedException {
        Thread.sleep(2000);
        assertThat("After waiting, the curve should now generate a gap", curve.isGeneratingGap(), is(true));
    }

    @Test
    void testContinueGap() {
        curve.isGeneratingGap();
        assertThat("The curve should have an active gap after it starts generating", curve.isGeneratingGap(), is(true));

        for (int i = 0; i < 5; i++) {
            assertThat("The curve should continue generating the gap on subsequent checks", curve.isGeneratingGap(), is(true));
        }
    }
}
