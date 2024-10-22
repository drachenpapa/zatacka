package de.drachenpapa.zatacka.game;

import de.drachenpapa.zatacka.input.InputHandler;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The GameEngine is responsible for managing the game loop, game state, and rendering
 * the game field along with the players' curves. It implements the core mechanics
 * of the game and interacts with the {@link Player} and {@link Statistics} classes to manage
 * player actions and track scores.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class GameEngine extends JPanel implements Runnable {

    /** The width of the game screen in pixels. */
    public static final int WINDOW_WIDTH = 800;

    /** The height of the game screen in pixels. */
    public static final int WINDOW_HEIGHT = 600;

    /** The width of the game field (the area where the game takes place). */
    public static final int PLAY_AREA_WIDTH = 676;

    /** The height of the game field. */
    public static final int PLAY_AREA_HEIGHT = 594;

    /** A 2D boolean array representing the game field. Each cell stores whether part of a curve occupies that position. */
    private boolean[][] playAreaCells = new boolean[PLAY_AREA_HEIGHT][PLAY_AREA_WIDTH];

    /** The players participating in the game. */
    @Getter
    private final Player[] players;

    /** Statistics instance that tracks players' scores and status. */
    private final Statistics statistics;

    /** Instance responsible for rendering game elements. */
    private final GameRenderer gameRenderer;

    /** Flag to indicate if the next round should start. */
    private volatile boolean isReadyForNextRound = false;

    /** Flag to indicate if the game has just started. */
    private volatile boolean isGameStarted = true;

    /** Flag to control the main game loop. */
    private volatile boolean isGameRunning = true;

    /** Flag to indicate if the game has ended. */
    private volatile boolean isGameOver = false;

    /** The speed of the game, which determines how fast the game loop runs. */
    private final int gameSpeed;

    /** The maximum score a player can reach before the game ends. */
    private final int winningScore;

    /** The JFrame that displays the game. */
    private final JFrame gameFrame;

    /**
     * Constructs a GameEngine instance and initializes game settings, players,
     * and sets up the JFrame for rendering the game.
     *
     * @param players The array of players participating in the game.
     * @param speed   The speed of the game (1-5, with 1 being the slowest).
     */
    public GameEngine(Player[] players, int speed) {
        this.players = players;
        this.statistics = new Statistics(players.length);
        this.gameRenderer = new GameRenderer();

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.black);

        gameFrame = new JFrame("Zatacka");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setContentPane(this);
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);

        gameFrame.addKeyListener(new InputHandler(this));

        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        setCursor(getToolkit().createCustomCursor(cursorImg, new Point(0, 0), ""));

        this.gameSpeed = 10 * (6 - speed);
        this.winningScore = (players.length - 1) * 10;

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Quits the game by stopping the game loop and resetting the screen resolution
     * to its original state.
     */
    public void quitGame() {
        isGameRunning = false;
        gameFrame.dispose();
    }

    /**
     * Starts the next round by resetting the game field and repositioning all
     * players randomly within the game area.
     */
    private void resetGameForNextRound() {
        playAreaCells = new boolean[PLAY_AREA_HEIGHT][PLAY_AREA_WIDTH];

        for (Player player : players) {
            player.setCurve(new Curve(
                    (int) (Math.random() * GameEngine.WINDOW_WIDTH) + 100,
                    (int) (Math.random() * GameEngine.WINDOW_HEIGHT) + 100,
                    Math.random() * 360,
                    (int) (Math.random() * 10) + 1)
            );
        }

        statistics.setAllAlive();
    }

    /**
     * Checks if a player's curve has collided with another curve or the game
     * boundaries. If a collision is detected, the player is marked as dead.
     *
     * @param curve The curve of the player to check for collisions.
     * @return True if a collision occurred, false otherwise.
     */
    private boolean isCollisionDetected(Curve curve) {
        double x = curve.getXPosition();
        double y = curve.getYPosition();
        int boundary = 4;

        if (x >= PLAY_AREA_WIDTH - boundary) {
            curve.setXPosition(boundary);
            curve.setPreviousXPosition(boundary);
        } else if (x <= boundary) {
            curve.setXPosition(PLAY_AREA_WIDTH - boundary);
            curve.setPreviousXPosition(PLAY_AREA_WIDTH - boundary);
        }
        if (y >= PLAY_AREA_HEIGHT - boundary) {
            curve.setYPosition(boundary);
            curve.setPreviousYPosition(boundary);
        } else if (y <= boundary) {
            curve.setYPosition(PLAY_AREA_HEIGHT - boundary);
            curve.setPreviousYPosition(PLAY_AREA_HEIGHT - boundary);
        }

        return detectCurveCollision(curve);
    }

    /**
     * Detects if a player's curve collides with another curve by checking the
     * 2D game field.
     *
     * @param curve The player's curve to check.
     * @return True if the curve collides with another curve, false otherwise.
     */
    private boolean detectCurveCollision(Curve curve) {
        int x = curve.getXPosition();
        int y = curve.getYPosition();
        int size = 4;

        if (x < 0 || x + size >= PLAY_AREA_WIDTH || y < 0 || y + size >= PLAY_AREA_HEIGHT) {
            return true;
        }

        for (int i = y; i < y + size; i++) {
            for (int j = x; j < x + size; j++) {
                if (playAreaCells[i][j]) {
                    return true;
                }
                playAreaCells[i][j] = true;
            }
        }
        return false;
    }

    /**
     * Main game loop that updates the game state, processes player input, and
     * renders the game. It runs continuously while the game is active.
     */
    @Override
    public void run() {
        while (isGameRunning || isGameOver) {
            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException ignored) {
            }

            updateGameState();

            paintComponent(getGraphics());
        }
    }

    /**
     * Updates the game state by processing player movements and checking for collisions.
     */
    private void updateGameState() {
        for (Player player : players) {
            Curve curve = player.getCurve();
            curve.move();

            if (player.isLeftKeyPressed()) {
                curve.turnLeft();
            } else if (player.isRightKeyPressed()) {
                curve.turnRight();
            }
        }
    }

    /**
     * Renders the game depending on the current state of the game (e.g., starting
     * a new round, running the game, or displaying the final statistics).
     *
     * @param g The Graphics context to draw on.
     */
    @Override
    public void paintComponent(Graphics g) {
        if (isReadyForNextRound) {
            resetGameForNextRound();
            isReadyForNextRound = false;
            isGameStarted = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } else if (isGameOver) {
            gameRenderer.drawFinalStatistics(g, players, statistics);
            isGameOver = false;
        } else if (isGameStarted) {
            gameRenderer.drawGameField(g);
            renderScorePanel(g);
            isGameStarted = false;
            isGameRunning = true;
        } else if (isGameRunning) {
            drawPlayerCurves(g);
        }
    }

    /**
     * Draws the curves of all players on the game field. It checks for collisions
     * and updates the game state accordingly.
     *
     * @param g The Graphics context to draw on.
     */
    private void drawPlayerCurves(Graphics g) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Curve curve = player.getCurve();

            if (curve.isAlive() && !curve.isGeneratingGap()) {
                g.setColor(player.getColor());

                if (isCollisionDetected(curve)) {
                    handleCollision(g, curve, i);
                } else {
                    gameRenderer.drawPlayerCurve(g, curve);
                }
            }
        }
    }

    private void handleCollision(Graphics g, Curve curve, int playerIndex) {
        curve.markAsDead();
        statistics.setPlayerDead(playerIndex);
        statistics.increasePoints(players);
        renderScorePanel(g);
    }

    /**
     * Draws the score panel on the side of the game screen, showing the current
     * points of each player.
     *
     * @param g The Graphics context to draw on.
     */
     private void renderScorePanel(Graphics g) {
         gameRenderer.drawScores(g, players, statistics);
         checkForGameEnd();
     }

    /**
     * Checks if the game has reached an end condition, either by score or by the
     * elimination of all but one player. Updates the game state accordingly.
     */
     private void checkForGameEnd() {
         int[] scores = statistics.getScores();

         for (int i = 0; i < players.length; i++) {
             if (scores[i] >= winningScore) {
                 isGameOver = true;
                 isGameRunning = false;
                 break;
             }
         }
         if (statistics.getAlivePlayerCount() == 1) {
             isReadyForNextRound = true;
         }
     }
}
