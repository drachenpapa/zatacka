package de.drachenpapa.zatacka.game;

import java.awt.*;

/**
 * The {@code GameRenderer} class is responsible for rendering various components
 * of the game, including the game field, the score panel, and the final statistics.
 * It uses the provided {@link Graphics} context to draw these elements onto the game screen.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
class GameRenderer {

    /**
     * Draws the game field, including the outer boundary and the play area
     * where the players' curves will be displayed.
     *
     * @param g The {@link Graphics} context used for drawing the game field.
     */
    void drawGameField(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, GameEngine.WINDOW_WIDTH, GameEngine.WINDOW_HEIGHT);
        g.setColor(Color.black);
        g.fillRect(3, 3, GameEngine.PLAY_AREA_WIDTH, GameEngine.PLAY_AREA_HEIGHT);
    }

    /**
     * Draws the score panel on the side of the game screen, displaying the current
     * points of each player. This provides real-time feedback during the game.
     *
     * @param g          The {@link Graphics} context used for drawing the score panel.
     * @param players    An array of {@link Player} objects representing the players.
     * @param statistics The {@link Statistics} used to retrieve the players' current scores.
     */
    void drawScores(Graphics g, Player[] players, Statistics statistics) {
        g.setColor(Color.gray);
        g.fillRect(682, 3, 115, GameEngine.WINDOW_HEIGHT - 6);

        int[] scores = statistics.getScores();

        for (int i = 0; i < players.length; i++) {
            g.setColor(players[i].getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 16));
            g.drawString(players[i].getPlayerName(), 690, 30 + (i * 50));
            g.drawString(scores[i] + " pts", 690, 50 + (i * 50));
        }
    }

    /**
     * Draws the final scores of all players when the game ends.
     * This is called at the conclusion of the game to display the final statistics,
     * showing the names of all players and their respective scores.
     *
     * @param g          The {@link Graphics} context used for drawing the final scores.
     * @param players    An array of {@link Player} objects representing the players.
     * @param statistics The {@link Statistics} used to retrieve the final scores.
     */
    void drawFinalStatistics(Graphics g, Player[] players, Statistics statistics) {
        int[] finalScores = statistics.getScores();
        g.setColor(Color.black);
        g.fillRect(0, 0, GameEngine.WINDOW_WIDTH, GameEngine.WINDOW_HEIGHT);

        g.setColor(Color.white);
        g.setFont(new Font("SANS_SERIF", Font.BOLD, 72));
        g.drawString("Final Scores", 200, 100); // Title

        for (int i = 0; i < players.length; i++) {
            g.setColor(players[i].getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 36));
            g.drawString(players[i].getPlayerName(), 200, 175 + (i * 75));
            g.drawString(finalScores[i] + " pts", 500, 175 + (i * 75));
        }
    }
}
