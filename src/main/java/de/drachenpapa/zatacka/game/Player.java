package de.drachenpapa.zatacka.game;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

/**
 * The {@code Player} class represents a player in the Zatacka game.
 * Each player controls a curve and interacts with the game environment through keyboard inputs.
 * This class models a player's essential properties like their name, control keys, and curve.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class Player {

    /** Name of the player. */
    @Getter
    private final String playerName;

    /** Color representing the player's curve. */
    @Getter
    private final Color color;

    /** Key used for turning the curve to the left. */
    @Getter
    private final char leftKey;

    /** Key used for turning the curve to the right. */
    @Getter
    private final char rightKey;

    /** The player's curve which moves and interacts in the game. */
    @Setter
    @Getter
    private Curve curve;

    /** Tracks whether the left key is currently pressed. */
    private boolean isLeftKeyPressed = false;

    /** Tracks whether the right key is currently pressed. */
    private boolean isRightKeyPressed = false;

    /**
     * Constructs a new {@code Player} with specified name, color, and control keys.
     * The initial position and direction of the player's curve are randomly generated.
     *
     * @param playerName  Name of the player.
     * @param color       Color of the player's curve.
     * @param leftKey     Key for turning the curve left.
     * @param rightKey    Key for turning the curve right.
     */
    public Player(String playerName, Color color, char leftKey, char rightKey) {
        this.playerName = playerName;
        this.color = color;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.curve = createRandomCurve();
    }

    /**
     * Generates a random curve with a random initial position and direction.
     *
     * @return A new {@link Curve} instance with random parameters.
     */
    private Curve createRandomCurve() {
        int xPosition = (int) (Math.random() * GameEngine.WINDOW_WIDTH + 100);
        int yPosition = (int) (Math.random() * GameEngine.WINDOW_HEIGHT + 100);
        double direction = Math.random() * 360;
        int size = (int) (Math.random() * 10) + 1;
        return new Curve(xPosition, yPosition, direction, size);
    }

    /**
     * Checks if the left key is currently pressed by the player.
     *
     * @return {@code true} if the left key is pressed, otherwise {@code false}.
     */
    public boolean isLeftKeyPressed() {
        return isLeftKeyPressed;
    }

    /**
     * Checks if the right key is currently pressed by the player.
     *
     * @return {@code true} if the right key is pressed, otherwise {@code false}.
     */
    public boolean isRightKeyPressed() {
        return isRightKeyPressed;
    }

    /**
     * Sets the state of the left key being pressed or released.
     *
     * @param isLeftKeyPressed {@code true} if the left key is pressed, otherwise {@code false}.
     */
    public void setLeftKeyPressed(boolean isLeftKeyPressed) {
        this.isLeftKeyPressed = isLeftKeyPressed;
    }

    /**
     * Sets the state of the right key being pressed or released.
     *
     * @param isRightKeyPressed {@code true} if the right key is pressed, otherwise {@code false}.
     */
    public void setRightKeyPressed(boolean isRightKeyPressed) {
        this.isRightKeyPressed = isRightKeyPressed;
    }
}
