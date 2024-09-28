// Kurve-Methoden für Zatacka
// Henning Wrede, Matthias Junge & René Marksteiner

package de.neusta.game;

/**
 * This Class models the Curve and it's attitude.
 * You can get and change the positions and conditions.
 *
 * @version 1.0
 * @author  René Marksteiner
 */

public class Kurve {
	/** Actual X-Coordinate of the Curve. */
	private double xPos;

	/** Actual Y-Coordinate of the Curve. */
	private double yPos;

	/** Actual direction of the Curve. */
	private double direction;

	/** Counter for the holes in the Curve. */
	private int holeCounter = 0;

	/** Timestamp of the last hole. */
	private long lastHole = 0;

	/**
	 * Sets the time, when the next hole will be there.
	 */
	private long time;

	/** Flag for the start of a hole. */
	private boolean hole = false;

	/** Status of the curve, is there a collision? */
	private boolean dead = false;

	/**
	 * Creates a curve with the settings.
	 */
	public Kurve(double xPos, double yPos, double direction, long time) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		this.time = time;
	}

	/**
	 * Sets the actual X-Coordinate of the Curve.
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * Getting the aktual X-Coordinate of the Curve.
	 * @return int xPos.
	 */
	public int getXPos() {
		return (int) Math.round(xPos);
	}

	/**
	 * Sets the actual Y-Coordinate of the Curve.
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * Getting the actual Y-Coordinate of the Curve.
	 * @return int yPos.
	 */
	public int getYPos() {
		return (int) Math.round(yPos);
	}

	/**
	 * Calculates the next position of the Curve.
	 */
	public void move() {
		// neue X-Position wird zur alten X-Position hinzugerechnet
		// *7 bestimmt den Abstand zwischen zwei Punkten
		xPos += Math.cos(Math.toRadians(direction))*6;
		// *7 bestimmt den Abstand zwischen zwei Punkten
		// neue Y-Position wird von der alten Y-Position abgezogen
		yPos -= Math.sin(Math.toRadians(direction))*6;
	}

	/**
	 * The movement of the Curve will be at 5 grade left.
	 */
	public void moveLeft() {
		/*
		 * 5 Grad zur Fahrtrichtung addieren. Der resultierende Wert liegt
		 * zwischen 0 und 360
		 */
		direction = (direction + 10) % 360;
	}

	/**
	 * The movement of the Curve will be at 5 grade right.
	 */
	public void moveRight() {
		/*
		 * 5 Grad von der Fahrtrichtung subtrahieren. Der resultierende Wert
		 * liegt zwischen 0 und 360
		 */
		direction = (direction - 10) % 360;
	}

	/**
	 * Getting if at the acutal position of the Curve is a hole.
	 * @return Boolean hole
	 */
	public boolean hasHole() {
		// aktuelle Zeit in ms
		long now = System.currentTimeMillis();

		// wenn time s vergangen ist seit der letzten Lücke
		if (now - lastHole > time) {
			// Start eines Loches
			hole = true;
			// letztes Loch jetzt
			lastHole = now;
			// setze neue Zeit für eine Lücke
			time = Math.round(Math.random() * 6000) + 1000;
		}

		// wenn eine Lücke existiert und die Länge min. 1
		if (holeCounter > 0 && hole) {
			// ein Teil der Lücke wurde "gezeichnet". holeCounter veringern
			holeCounter--;
			// Kurve hat ein Loch
			return true;
		} else {
			// kein Loch
			hole = false;
			// neuen Zähler fürs nächste Loch erzeugen
			holeCounter = (int) Math.round(Math.random() * 2 + 2);
			// Kurve hat kein Loch
			return false;
		}
	}

	/**
	 * Returns if the Curve is dead, so there was a collision
	 * @return Boolean dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Kills the Curve.
	 */
	public void kill() {
		dead = true;
	}
}