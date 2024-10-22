package de.drachenpapa.zatacka.game;

import lombok.Getter;

import java.util.Arrays;

/**
 * The {@code Statistics} class tracks player scores and their alive status throughout the game.
 * It provides methods for updating and retrieving player data, such as scores and whether they are still alive.
 * This class helps manage game progression and determine game results.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class Statistics {

    /** Array storing the current scores of each player. */
    @Getter
    private final int[] scores;

    /** Array indicating the alive status of each player. */
    private final boolean[] playersAlive;

    /**
     * Initializes a new {@code Statistics} object for a game with a given number of players.
     * The scores of all players are initialized to zero, and all players are marked as alive.
     *
     * @param maxPlayers The maximum number of players in the game.
     */
    public Statistics(int maxPlayers) {
        this.scores = new int[maxPlayers];
        this.playersAlive = new boolean[maxPlayers];
        resetStatistics();
    }

    /**
     * Increases the score of all players that are currently alive.
     *
     * @param players Array of {@link Player} objects representing the players.
     */
    public void increasePoints(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getCurve().isAlive()) {
                scores[i]++;
            }
        }
    }

    /**
     * Sets the status of all players to alive, typically called at the start of a new round.
     */
    void setAllAlive() {
        Arrays.fill(playersAlive, true);
    }

    /**
     * Returns the number of players currently alive in the game.
     *
     * @return The count of players who are still alive.
     */
    int getAlivePlayerCount() {
        int aliveCount = 0;
        for (boolean alive : playersAlive) {
            if (alive) {
                aliveCount++;
            }
        }
        return aliveCount;
    }

    /**
     * Marks a specific player as dead based on their index.
     *
     * @param playerIndex The index of the player to mark as dead.
     */
    void setPlayerDead(int playerIndex) {
        playersAlive[playerIndex] = false;
    }

    /**
     * Resets all player scores to zero and marks all players as alive.
     */
    private void resetStatistics() {
        Arrays.fill(scores, 0);
        Arrays.fill(playersAlive, true);
    }
}
