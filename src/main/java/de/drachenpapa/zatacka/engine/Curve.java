package de.drachenpapa.zatacka.engine;

import lombok.Getter;
import lombok.Setter;

/**
 * This class models the behavior and properties of a curve in the Zatacka game.
 * It handles the curve's position, direction, and hole logic.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
@Getter
@Setter
public class Curve {

    private static final double TURN_ANGLE = 10.0;
    private static final int MIN_HOLE_TIME = 1000;
    private static final int MAX_HOLE_TIME = 6000;
    private static final double STEP_SIZE = 6.0;

    /** The current position and direction of the curve. */
    private double xPos;
    private double yPos;
    private double direction;

    /** Counter and timestamp for managing the holes in the curve. */
    private int holeCounter;
    private long lastHoleTimestamp;

    /** Time interval for the next hole occurrence. */
    private long holeInterval;

    /** Indicates whether a hole is currently being drawn. */
    private boolean isDrawingHole;

    /** Indicates if the curve has collided (is dead). */
    private boolean isDead;

    /**
     * Constructs a curve with initial settings.
     *
     * @param xPos      The starting X-coordinate of the curve.
     * @param yPos      The starting Y-coordinate of the curve.
     * @param direction  The initial direction of the curve (in degrees).
     * @param holeInterval The time interval for the next hole.
     */
    public Curve(double xPos, double yPos, double direction, long holeInterval) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.holeInterval = holeInterval;
        this.holeCounter = 0;
        this.lastHoleTimestamp = 0;
        this.isDrawingHole = false;
        this.isDead = false;
    }

    /**
     * Moves the curve forward based on its current direction.
     */
    public void move() {
        xPos += Math.cos(Math.toRadians(direction)) * STEP_SIZE;
        yPos -= Math.sin(Math.toRadians(direction)) * STEP_SIZE;
    }

    /**
     * Turns the curve 10 degrees to the left (counter-clockwise).
     */
    public void moveLeft() {
        direction = (direction + TURN_ANGLE) % 360;
    }

    /**
     * Turns the curve 10 degrees to the right (clockwise).
     */
    public void moveRight() {
        direction = (direction - TURN_ANGLE + 360) % 360;
    }

    /**
     * Determines if the curve is currently drawing a hole.
     *
     * @return {@code true} if the curve is drawing a hole, {@code false} otherwise.
     */
    public boolean hasHole() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastHoleTimestamp > holeInterval) {
            startNewHole();
        }

        if (isDrawingHole && holeCounter > 0) {
            holeCounter--;
            return true;
        } else {
            isDrawingHole = false;
            return false;
        }
    }

    /**
     * Starts a new hole with updated parameters.
     */
    private void startNewHole() {
        isDrawingHole = true;
        lastHoleTimestamp = System.currentTimeMillis();
        holeInterval = (int) (Math.random() * (MAX_HOLE_TIME - MIN_HOLE_TIME + 1)) + MIN_HOLE_TIME;
        holeCounter = (int) (Math.random() * 3) + 2;
    }

    /**
     * Marks the curve as dead (collision detected).
     */
    public void kill() {
        isDead = true;
    }
}
