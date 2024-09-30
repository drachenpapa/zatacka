package de.drachenpapa.zatacka.game;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Curve in the Zatacka game. The curve moves, turns, and can create gaps as it progresses.
 * This class provides methods for updating the curve's position, direction, and handling collision states.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
@Getter
@Setter
public class Curve {

    /** X-coordinate of the curve's current position. */
    private int xPosition;

    /** Y-coordinate of the curve's current position. */
    private int yPosition;

    /** Direction of the curve's movement, in degrees. */
    private double directionAngle;

    /** Counter tracking the length of the current gap in the curve. */
    private int gapLengthCounter;

    /** Timestamp of the last gap generated in the curve. */
    private long lastGapTimestamp;

    /** Interval for generating gaps in the curve. */
    private long gapInterval;

    /** Indicates if the curve is currently generating a gap. */
    private boolean isGapActive;

    /** Indicates whether the curve has collided (i.e., is "dead"). */
    private boolean isDead;

    /** Step size for curve movement. */
    private static final double STEP_SIZE = 6.0;

    /** Rotation angle for turning the curve. */
    private static final double TURN_ANGLE = 10.0;

    /** Minimum time for generating gaps (in milliseconds). */
    private static final long MIN_GAP_INTERVAL = 1000L;

    /** Maximum time for generating gaps (in milliseconds). */
    private static final long MAX_GAP_INTERVAL = 6000L;

    /** Minimum gap length. */
    private static final int MIN_GAP_LENGTH = 2;

    /** Maximum gap length. */
    private static final int MAX_GAP_LENGTH = 4;

    /**
     * Initializes a new curve with the specified starting position, direction, and gap generation interval.
     *
     * @param xPosition      Initial X-coordinate of the curve.
     * @param yPosition      Initial Y-coordinate of the curve.
     * @param directionAngle Initial movement direction of the curve, in degrees.
     * @param gapInterval    Time interval for generating gaps in milliseconds.
     */
    public Curve(int xPosition, int yPosition, double directionAngle, long gapInterval) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.directionAngle = directionAngle;
        this.gapInterval = gapInterval;
        this.isGapActive = false;
        this.isDead = false;
        this.gapLengthCounter = 0;
        this.lastGapTimestamp = System.currentTimeMillis();
    }

    /**
     * Updates the position of the curve based on its current direction.
     * The curve moves forward by a fixed step size.
     */
    public void move() {
        double radians = Math.toRadians(directionAngle);
        xPosition += (int) (Math.cos(radians) * STEP_SIZE);
        yPosition -= (int) (Math.sin(radians) * STEP_SIZE);
    }

    /**
     * Rotates the curve to the left by a fixed angle.
     */
    public void turnLeft() {
        directionAngle = (directionAngle + TURN_ANGLE) % 360;
    }

    /**
     * Rotates the curve to the right by a fixed angle.
     */
    public void turnRight() {
        directionAngle = (directionAngle - TURN_ANGLE + 360) % 360;
    }

    /**
     * Determines if the curve should create a gap at its current position.
     * A gap is generated at random intervals.
     *
     * @return {@code true} if the curve is currently generating a gap; {@code false} otherwise.
     */
    public boolean isGeneratingGap() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastGapTimestamp > gapInterval) {
            startNewGap(currentTime);
        }

        return isGapActive && continueGap();
    }

    /**
     * Starts a new gap by updating the gap interval and resetting the gap length counter.
     */
    private void startNewGap(long currentTime) {
        isGapActive = true;
        lastGapTimestamp = currentTime;
        gapInterval = MIN_GAP_INTERVAL + (long) (Math.random() * (MAX_GAP_INTERVAL - MIN_GAP_INTERVAL));
        gapLengthCounter = MIN_GAP_LENGTH + (int) (Math.random() * (MAX_GAP_LENGTH - MIN_GAP_LENGTH + 1));
    }

    /**
     * Continues generating the current gap and decrements the gap length counter.
     * If the counter reaches zero, the gap ends.
     *
     * @return {@code true} if the gap is still active; {@code false} otherwise.
     */
    private boolean continueGap() {
        if (gapLengthCounter > 0) {
            gapLengthCounter--;
            return true;
        } else {
            isGapActive = false;
            return false;
        }
    }

    /**
     * Checks if the curve is still active (alive).
     *
     * @return {@code true} if the curve is alive; {@code false} otherwise.
     */
    public boolean isAlive() {
        return !isDead;
    }

    /**
     * Marks the curve as dead, indicating a collision has occurred.
     */
    public void markAsDead() {
        isDead = true;
    }
}
