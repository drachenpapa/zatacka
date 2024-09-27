package de.drachenpapa.zatacka.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.Color;

/**
 * Represents a player in the Zatacka game, with attributes such as name, color, controls, and the curve they control.
 * Lombok is used to generate boilerplate code like getters, setters, and constructors.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private boolean client;           // Indicates if the player is a client in multiplayer
    private Color color;              // The color of the player's curve
    private Curve curve;              // The curve controlled by the player
    private char leftControl;         // The left control key assigned to the player
    private String name;              // The name of the player
    private char rightControl;        // The right control key assigned to the player
    private boolean leftPressed;      // Indicates if the left control key is currently pressed
    private boolean rightPressed;     // Indicates if the right control key is currently pressed

    /**
     * Initializes a player with a random curve.
     */
    public Player(String name, Color color, char leftControl, char rightControl, boolean client) {
        this.client = client;
        this.color = color;
        this.leftControl = leftControl;
        this.name = name;
        this.rightControl = rightControl;
        this.curve = createRandomCurve();
    }

    /**
     * Creates a curve with random initial position, direction, and time interval.
     *
     * @return A new Curve instance with random parameters.
     */
    private Curve createRandomCurve() {
        double xPos = Math.round(Math.random() * ZatackaEngine.WIDTH) + 100;
        double yPos = Math.round(Math.random() * ZatackaEngine.HEIGHT) + 100;
        double direction = Math.random() * 360;
        long time = Math.round(Math.random() * 10) + 1;

        return new Curve(xPos, yPos, direction, time);
    }
}
