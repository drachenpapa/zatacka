package de.neusta.game;

/**
 * This class creates a statistic including scores and status of all players.
 * 
 * @version 1.0
 * @author René Marksteiner
 */

public class Statistik {
	/** scores of all players */
	private int points[];

	/** containing the status of the players */
	private boolean[] players_alive;

	/**
	 * constructor
	 * 
	 * @param max_players number of players
	 */
	public Statistik(int max_players) {
		// Array der Größe max_players für Punktestand erzeugen
		this.points = new int[max_players];
		// Punkte für jeden Spieler auf 0 setzen
		for (int i = 0; i < points.length; i++) {
			points[i] = 0;
		}
		// Array der Größe max_players für Spielerstatus erzeugen
		this.players_alive = new boolean[max_players];
		// Status für jeden Spieler auf alive setzen
		for (int i = 0; i < points.length; i++) {
			players_alive[i] = true;
		}
	}

	/**
	 * increases the points of all players alive.
	 * 
	 * @param spieler Array of Spieler
	 */
	public void increasePoints(Spieler[] spieler) {
		for (int i = 0; i < spieler.length; i++) {
			// wenn Spieler am Leben -> Punkte + 1
			if (!spieler[i].kurve.isDead()) {
				points[i]++;
			}
		}
	}

	/**
	 * returns the score of each player.
	 * 
	 * @return Integer[] Score of all players
	 */
	public int[] getPoints() {
		return points;
	}

	/**
	 * sets the status of each player on alive.
	 */
	public void setAllAlive() {
		for (int i = 0; i < points.length; i++) {
			players_alive[i] = true;
		}
	}

	/**
	 * returns how many players are alive.
	 * 
	 * @return int number of players alive
	 */
	public int getPlayersAlive() {
		int erg = 0;
		for (int i = 0; i < points.length; i++) {
			// wenn Spieler lebt -> Zähler + 1
			if (players_alive[i] == true) {
				erg++;
			}
		}
		// Zähler zurückgeben
		return erg;
	}

	/**
	 * sets the Status of the specified playter on dead.
	 * 
	 * @param spieler player to kill
	 */
	public void setDead(int spieler) {
		players_alive[spieler] = false;
	}

}