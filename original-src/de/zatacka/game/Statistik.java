package de.zatacka.game;

public class Statistik {
	private int points[];
	private boolean[] players_alive;

	public Statistik(int max_players) {
		this.points = new int[max_players];

		for (int i = 0; i < points.length; i++) {
			points[i] = 0;
		}

		this.players_alive = new boolean[max_players];
		for (int i = 0; i < points.length; i++) {
			players_alive[i] = true;
		}
	}

	public void increasePoints(Spieler[] spieler) {
		for (int i = 0; i < spieler.length; i++) {
			if (!spieler[i].kurve.isDead()) {
				points[i]++;
			}
		}
	}

	public int[] getPoints() {
		return points;
	}

	public void setAllAlive() {
		for (int i = 0; i < points.length; i++) {
			players_alive[i] = true;
		}
	}

	public int getPlayersAlive() {
		int erg = 0;
		for (int i = 0; i < points.length; i++) {
			if (players_alive[i] == true) {
				erg++;
			}
		}
		return erg;
	}

	public void setDead(int spieler) {
		players_alive[spieler] = false;
	}
}