package de.drachenpapa.zatacka.engine;

import lombok.Getter;

import java.util.Arrays;

/**
 * This class creates statistics including scores and status of all players.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class Statistics {
    /** The scores of each player. */
    @Getter
    private final int[] points;

    /** The status of each player (alive or dead). */
    private final boolean[] playersAlive;

    /**
     * Constructs a Statistics object for a specified number of players.
     *
     * @param maxPlayers Number of players.
     */
    public Statistics(int maxPlayers) {
        this.points = new int[maxPlayers];
        this.playersAlive = new boolean[maxPlayers];
        Arrays.fill(playersAlive, true);  // Initialize all players as alive
    }

    /**
     * Increases the points of all players who are alive.
     *
     * @param players Array of Player.
     */
    public void increasePoints(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].getCurve().isDead()) {
                points[i]++;
            }
        }
    }

    /**
     * Sets the status of all players to alive.
     */
    public void setAllAlive() {
        Arrays.fill(playersAlive, true);  // Reset all players to alive
    }

    /**
     * Returns the number of players who are alive.
     *
     * @return Number of players alive.
     */
    public int getPlayersAlive() {
        int count = 0;
        for (boolean alive : playersAlive) {
            if (alive) {
                count++;
            }
        }
        return count;
    }

    /**
     * Sets the status of the specified player to dead.
     *
     * @param player Index of the player to kill.
     */
    public void setDead(int player) {
        playersAlive[player] = false;
    }
}
