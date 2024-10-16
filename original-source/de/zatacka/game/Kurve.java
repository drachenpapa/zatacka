package de.zatacka.game;

public class Kurve {
	private double xPos;

	private double yPos;

	private double direction;

	private int holeCounter = 0;

	private long lastHole = 0;

	private long time;

	private boolean hole = false;

	private boolean dead = false;

	public Kurve(double xPos, double yPos, double direction, long time) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		this.time = time;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getXPos() {
		return (int) Math.round(xPos);
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public int getYPos() {
		return (int) Math.round(yPos);
	}

	public void move() {
		xPos += Math.cos(Math.toRadians(direction))*6;
		yPos -= Math.sin(Math.toRadians(direction))*6;
	}

	public void moveLeft() {
		direction = (direction + 10) % 360;
	}

	public void moveRight() {
		direction = (direction - 10) % 360;
	}

	public boolean hasHole() {
		long now = System.currentTimeMillis();

		if (now - lastHole > time) {
			hole = true;
			lastHole = now;
			time = Math.round(Math.random() * 6000) + 1000;
		}

		if (holeCounter > 0 && hole) {
			holeCounter--;
			return true;
		} else {
			hole = false;
			holeCounter = (int) Math.round(Math.random() * 2 + 2);
			return false;
		}
	}

	public boolean isDead() {
		return dead;
	}

	public void kill() {
		dead = true;
	}
}