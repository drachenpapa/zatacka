package de.drachenpapa.zatacka.engine;

import lombok.Getter;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * This class represents the main game engine for Zatacka.
 * It controls the display, logic, and the game loop.
 *
 * Author: Henning Steinberg (@drachenpapa)
 * Version: 1.0
 */
public class ZatackaEngine extends Canvas implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int G_WIDTH = 676;
    private static final int G_HEIGHT = 594;
    private static final int CURVE_SIZE = 4;

    private boolean[][] field = new boolean[G_HEIGHT][G_WIDTH];
    @Getter
    private final Player[] players;
    private final Statistics statistics;

    private boolean nextRound = false;
    private boolean started = true;
    private boolean running = true;
    private boolean ended = false;

    private final int speed;
    private final int maxScore;

    private final Thread gameThread;
    private JFrame frame;
    private DisplayMode oldDisplayMode;

    /**
     * Constructs a ZatackaEngine instance that controls the display and game logic.
     *
     * @param players An array of Player objects representing the players.
     * @param speed The speed of the game.
     */
    public ZatackaEngine(Player[] players, int speed) {
        setupFrame();
        this.players = players;
        this.statistics = new Statistics(players.length);
        this.speed = 10 * (6 - speed);
        this.maxScore = (players.length - 1) * 10;

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void setupFrame() {
        setBounds(0, 0, WIDTH, HEIGHT);
        setBackground(Color.black);
        frame = new JFrame();
        frame.setUndecorated(true);
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(null);
        panel.add(this);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(frame);
        oldDisplayMode = device.getDisplayMode();
        device.setDisplayMode(new DisplayMode(WIDTH, HEIGHT, oldDisplayMode.getBitDepth(), oldDisplayMode.getRefreshRate()));

        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        setCursor(getToolkit().createCustomCursor(cursorImage, new Point(0, 0), ""));

        frame.setVisible(true);
        frame.addKeyListener(new GameInputHandler(this));
    }

    /**
     * Restores the graphical settings and closes the application.
     */
    public void quit() {
        running = false;
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(oldDisplayMode);
        frame.dispose();
        System.exit(0);
    }

    /**
     * Starts a new round by resetting the game field and player curves.
     */
    public void nextRound() {
        field = new boolean[G_HEIGHT][G_WIDTH];

        for (Player player : players) {
            player.setCurve(new Curve(
                    Math.round(Math.random() * ZatackaEngine.WIDTH) + 100,
                    Math.round(Math.random() * ZatackaEngine.HEIGHT) + 100,
                    Math.random() * 360,
                    Math.round(Math.random() * 10) + 1
            ));
        }

        statistics.setAllAlive();
    }

    /**
     * Checks if the given curve has a collision with the game boundaries.
     *
     * @param curve The curve to check for collisions.
     * @return True if there is a collision, false otherwise.
     */
    public boolean isCollision(Curve curve) {
        double xPos = curve.getXPos();
        double yPos = curve.getYPos();

        if (xPos >= G_WIDTH - CURVE_SIZE) {
            curve.setXPos(CURVE_SIZE);
        } else if (xPos <= CURVE_SIZE) {
            curve.setXPos(G_WIDTH - CURVE_SIZE);
        }

        if (yPos >= G_HEIGHT - CURVE_SIZE) {
            curve.setYPos(CURVE_SIZE);
        } else if (yPos <= CURVE_SIZE) {
            curve.setYPos(G_HEIGHT - CURVE_SIZE);
        }

        return checkCol(curve);
    }

    /**
     * Checks whether the given curve collides with another curve.
     *
     * @param curve The curve to check for collisions.
     * @return True if there is a collision, false otherwise.
     */
    public boolean checkCol(Curve curve) {
        double xPos = curve.getXPos();
        double yPos = curve.getYPos();

        try {
            for (double y = yPos; y < (yPos + CURVE_SIZE); y++) {
                for (double x = xPos; x < (xPos + CURVE_SIZE); x++) {
                    if (field[(int)y][(int)x]) {
                        return true;
                    }
                    field[(int)y][(int)x] = true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle out-of-bounds gracefully
        }
        return false;
    }

    /**
     * Represents the game loop that runs continuously while the game is active.
     */
    public void run() {
        while (running || ended) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (Player player : players) {
                player.getCurve().move();

                if (player.isLeftPressed()) {
                    player.getCurve().moveLeft();
                } else if (player.isRightPressed()) {
                    player.getCurve().moveRight();
                }
            }

            repaint();
        }
    }

    /**
     * Draws the entire game interface, including the playing field and scores.
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    public void paint(Graphics g) {
        if (nextRound) {
            nextRound();
            nextRound = false;
            started = true;
            sleep();
        } else if (ended) {
            drawStatistics(g);
            ended = false;
        } else if (started) {
            drawField(g);
            drawScores(g);
            started = false;
            running = true;
        } else if (running) {
            drawCurves(g);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Draws the game field, including the frame and the play area.
     *
     * @param g The Graphics object to draw on.
     */
    public void drawField(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.black);
        g.fillRect(3, 3, G_WIDTH, G_HEIGHT);
    }

    /**
     * Draws all active curves on the game field.
     *
     * @param g The Graphics object to draw on.
     */
    public void drawCurves(Graphics g) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            g.setColor(player.getColor());

            if (!player.getCurve().isDead() && !player.getCurve().hasHole()) {
                if (isCollision(player.getCurve())) {
                    player.getCurve().kill();
                    statistics.setDead(i);
                    statistics.increasePoints(players);
                    drawScores(g);
                } else {
                    g.fillRect((int) player.getCurve().getXPos(), (int) player.getCurve().getYPos(), CURVE_SIZE, CURVE_SIZE);
                }
            }
        }
        g.setColor(Color.black);
    }

    /**
     * Draws the scores of all players on the game interface.
     *
     * @param g The Graphics object to draw on.
     */
    public void drawScores(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(682, 3, 115, G_HEIGHT);
        int[] points = statistics.getPoints();

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            g.setColor(player.getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 16));
            g.drawString(player.getName(), 690, 30 + (i * 50));
            g.drawString(points[i] + " pts", 690, 50 + (i * 50));

            if (points[i] >= maxScore) {
                ended = true;
                running = false;
            }
        }

        if (statistics.getPlayersAlive() == 1) {
            nextRound = true;
        }
    }

    /**
     * Draws the statistics at the end of the game.
     *
     * @param g The Graphics object to draw on.
     */
    public void drawStatistics(Graphics g) {
        int[] ranking = statistics.getPoints();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("SANS_SERIF", Font.BOLD, 72));
        g.drawString(" Scoreboard", 200, 100);

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            g.setColor(player.getColor());
            g.setFont(new Font("SANS_SERIF", Font.BOLD, 36));
            g.drawString(player.getName(), 200, 175 + (i * 75));
            g.drawString(ranking[i] + " pts", 500, 175 + (i * 75));
        }
    }
}
