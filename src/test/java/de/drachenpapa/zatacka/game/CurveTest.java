package de.drachenpapa.zatacka.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class CurveTest {

    private Curve curve;

    @BeforeEach
    public void setUp() {
        curve = new Curve(0, 0, 90, 2000);
    }

    @Test
    public void testMove() {
        curve.move();
        assertThat("X position should increase by STEP_SIZE", curve.getXPosition(), is(6));
        assertThat("Y position should remain the same", curve.getYPosition(), is(0));
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
    public void testIsGeneratingGap() {
        long mockTime = System.currentTimeMillis();

        Curve spyCurve = spy(curve);
        doReturn(mockTime).when(spyCurve).currentTimeMillis();

        spyCurve.setLastGapTimestamp(mockTime - 3000);
        boolean isGapGenerating = spyCurve.isGeneratingGap();

        assertThat("Gap should be generating after the interval has passed", isGapGenerating, is(true));
        assertThat("Gap should be active after starting to generate", spyCurve.isGapActive(), is(true));
    }

    @Test
    public void testMarkAsDead() {
        curve.markAsDead();
        assertThat("Curve should be marked as dead after calling markAsDead", curve.isAlive(), is(false));
    }

    @Test
    public void testContinueGap() {
        curve.setGapLengthCounter(2);
        boolean result = curve.isGeneratingGap();

        assertThat("Continue gap should return true when there are still gap lengths remaining", result, is(true));
        assertThat("Gap length counter should decrease after continuing the gap", curve.getGapLengthCounter(), is(1));
    }
}
