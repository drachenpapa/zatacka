package de.drachenpapa.zatacka.engine;

import lombok.Getter;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * ZatackaEngine is responsible for managing the game loop, game state, and
 * rendering the game field and players' curves. It implements the core mechanics
 * of the game and interacts with the Player and Statistics classes to manage
 * player actions and track scores.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class ZatackaEngine extends Canvas implements Runnable {

    /**
     * The width of the game screen in pixels.
     */
    public static final int SCREEN_WIDTH = 800;

    /**
     * The height of the game screen in pixels.
     */
    public static final int SCREEN_HEIGHT = 600;

    /**
     * The width of the game field (the area where the game takes place).
     */
    private final int GAME_WIDTH = 676;

    /**
     * The height of the game field.
     */
    private final int GAME_HEIGHT = 594;

    /**
     * A 2D boolean array representing the game field. Each cell stores whether
     * part of a curve occupies that position.
     */
    private boolean[][] gameField = new boolean[GAME_HEIGHT][GAME_WIDTH];

    /**
     * The players participating in the game.
     */
    @Getter
    private final Player[] players;

    /**
     * Statistics instance that tracks players' scores and status.
     */
    private final Statistics statistics;

    /**
     * Flag to indicate if the next round should start.
     */
    private volatile boolean isNextRound = false;

    /**
     * Flag to indicate if the game has just started.
     */
    private volatile boolean isGameStarted = true;

    /**
     * Flag to control the main game loop.
     */
    private volatile boolean isGameRunning = true;

    /**
     * Flag to indicate if the game has ended.
     */
    private volatile boolean isGameEnded = false;

    /**
     * The speed of the game, which determines how fast the game loop runs.
     */
    private final int gameSpeed;

    /**
     * The maximum score a player can reach before the game ends.
     */
    private final int maxScore;

    /**
     * The JFrame that displays the game.
     */
    private final JFrame gameFrame;

    /**
     * The original display mode before the game changes the screen resolution.
     */
    private final DisplayMode originalDisplayMode;

    /**
     * Constructor for the ZatackaEngine. It initializes the game settings, players,
     * and sets up the JFrame for rendering the game.
     *
     * @param players The array of players participating in the game.
     * @param speed   The speed of the game (1-5, with 1 being the slowest).
     */
    public ZatackaEngine(Player[] players, int speed) {
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        setBackground(Color.black);

        gameFrame = new JFrame();
        gameFrame.setUndecorated(true);
        JPanel panel = (JPanel) gameFrame.getContentPane();
        panel.setLayout(null);
        panel.add(this);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(gameFrame);

        originalDisplayMode = device.getDisplayMode();
        DisplayMode newMode = new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, originalDisplayMode.getBitDepth(), originalDisplayMode.getRefreshRate());
        if (device.isDisplayChangeSupported()) {
            device.setDisplayMode(newMode);
        }

        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        setCursor(getToolkit().createCustomCursor(cursorImg, new Point(0, 0), ""));

        gameFrame.setVisible(true);
        gameFrame.addKeyListener(new GameInputHandler(this));

        this.players = players;
        this.statistics = new Statistics(players.length);
        this.gameSpeed = 10 * (6 - speed);
        this.maxScore = (players.length - 1) * 10;

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Quits the game by stopping the game loop and resetting the screen resolution
     * to its original state.
     */
    public void quitGame() {
        isGameRunning = false;
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(originalDisplayMode);
        gameFrame.dispose();
    }

    /**
     * Starts the next round by resetting the game field and repositioning all
     * players randomly.
     */
    public void startNextRound() {
        gameField = new boolean[GAME_HEIGHT][GAME_WIDTH];

        for (Player player : players) {
            player.setCurve(new Curve(
                    (int) (Math.random() * ZatackaEngine.SCREEN_WIDTH) + 100,
                    (int) (Math.random() * ZatackaEngine.SCREEN_HEIGHT) + 100,
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
    public boolean checkCollision(Curve curve) {
        int x = curve.getXPosition();
        int y = curve.getYPosition();
        int boundary = 4;

        if (x >= GAME_WIDTH - boundary) curve.setXPosition(boundary);
        if (x <= boundary) curve.setXPosition(GAME_WIDTH - boundary);
        if (y >= GAME_HEIGHT - boundary) curve.setYPosition(boundary);
        if (y <= boundary) curve.setYPosition(GAME_HEIGHT - boundary);

        return detectCurveCollision(curve);
    }

    /**
     * Detects if a player's curve collides with another curve by checking the
     * 2D game field.
     *
     * @param curve The player's curve to check.
     * @return True if the curve collides with another curve, false otherwise.
     */
    public boolean detectCurveCollision(Curve curve) {
        int x = curve.getXPosition();
        int y = curve.getYPosition();
        int size = 4;

        if (x < 0 || x + size >= GAME_WIDTH || y < 0 || y + size >= GAME_HEIGHT) {
            return true;
        }

        for (int i = y; i < y + size; i++) {
            for (int j = x; j < x + size; j++) {
                if (gameField[i][j]) {
                    return true;
                }
                gameField[i][j] = true;
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
        while (isGameRunning || isGameEnded) {
            try {
                Thread.sleep(gameSpeed);
            } catch (InterruptedException ignored) {
            }

            for (Player player : players) {
                player.getCurve().move();

                if (player.isLeftKeyPressed()) {
                    player.getCurve().turnLeft();
                } else if (player.isRightKeyPressed()) {
                    player.getCurve().turnRight();
                }
            }

            paint(getGraphics());
        }
    }

    /**
     * Draws the game field, which includes the game boundary and the area where
     * the curves will be drawn.
     *
     * @param g The Graphics context to draw on.
     */
    public void drawGameField(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g.setColor(Color.black);
        g.fillRect(3, 3, GAME_WIDTH, GAME_HEIGHT);
    }

    /**
     * Draws the curves of all players on the game field. It checks for collisions
     * and updates the game state accordingly.
     *
     * @param g The Graphics context to draw on.
     */
    public void drawCurves(Graphics g) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Curve curve = player.getCurve();
            if (curve.isAlive() && !curve.isGeneratingGap()) {
                g.setColor(player.getColor());

                if (checkCollision(curve)) {
                    curve.markAsDead();
                    statistics.setPlayerDead(i);
                    statistics.increasePoints(players);
                    drawScores(g);
                } else {
                    int x = curve.getXPosition();
                    int y = curve.getYPosition();
                    g.fillRect(x, y, 4, 4);
                }
            }
        }
    }

    /**
     * Draws the score panel on the side of the game screen, showing the current
     * points of each player.
     *
     * @param g The Graphics context to draw on.
     */
    public void drawScores(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(682, 3, 115, GAME_HEIGHT);

        int[] scores = statistics.getScores();

        for (int i = 0; i < players.length; i++) {
            g.setColor(players[i].getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 16));
            g.drawString(players[i].getPlayerName(), 690, 30 + (i * 50));
            g.drawString(scores[i] + " pts", 690, 50 + (i * 50));

            if (scores[i] >= maxScore) {
                isGameEnded = true;
                isGameRunning = false;
            }
        }

        if (statistics.getAlivePlayerCount() == 1) {
            isNextRound = true;
        }
    }

    /**
     * Draws the final scores of all players when the game ends.
     *
     * @param g The Graphics context to draw on.
     */
    public void drawFinalStatistics(Graphics g) {
        int[] finalScores = statistics.getScores();
        g.setColor(Color.black);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g.setColor(Color.white);
        g.setFont(new Font("SANS_SERIF", Font.BOLD, 72));
        g.drawString("Final Scores", 200, 100);

        for (int i = 0; i < players.length; i++) {
            g.setColor(players[i].getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 36));
            g.drawString(players[i].getPlayerName(), 200, 175 + (i * 75));
            g.drawString(finalScores[i] + " pts", 500, 175 + (i * 75));
        }
    }

    /**
     * Renders the game depending on the current state of the game (e.g., starting
     * a new round, running the game, or displaying the final statistics).
     *
     * @param g The Graphics context to draw on.
     */
    @Override
    public void paint(Graphics g) {
        if (isNextRound) {
            startNextRound();
            isNextRound = false;
            isGameStarted = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } else if (isGameEnded) {
            drawFinalStatistics(g);
            isGameEnded = false;
        } else if (isGameStarted) {
            drawGameField(g);
            drawScores(g);
            isGameStarted = false;
            isGameRunning = true;
        } else if (isGameRunning) {
            drawCurves(g);
        }
    }
}
