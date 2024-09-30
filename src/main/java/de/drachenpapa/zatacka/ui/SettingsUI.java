package de.drachenpapa.zatacka.ui;

import de.drachenpapa.zatacka.game.GameEngine;
import de.drachenpapa.zatacka.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code SettingsUI} class provides a graphical user interface for configuring game settings
 * in the Zatacka game. This includes selecting player colors, control keys, and speed settings.
 * The UI allows for up to 6 players to be configured.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class SettingsUI extends JFrame implements ActionListener {

    /** The maximum number of players allowed in the game. */
    protected final int maxPlayers = 6;

    /** Array of buttons for selecting player colors and control buttons. */
    protected JButton[] colorButtons, leftControlButtons, rightControlButtons;

    /** Button to start the game and button to load default settings. */
    protected JButton startButton, loadDefaultsButton;

    /** Checkboxes to select players. */
    protected JCheckBox[] playerCheckBoxes;

    /** Labels and spinner for speed control. */
    protected JLabel allLabel, speedLabel, spacer1, spacer2;
    protected JLabel[] playerLabels;

    /** Spinner for selecting the speed of the game. */
    protected JSpinner speedSpinner;

    /** Text fields for entering player names. */
    protected JTextField[] nameTextFields;

    /** Arrays to store control keys for players. */
    protected char[] leftControlKeys;
    protected char[] rightControlKeys;
    protected char[] defaultRightControlKeys;
    protected char[] defaultLeftControlKeys;

    /** Array to hold the players created based on the settings. */
    protected Player[] players;

    /** Array of default colors for players. */
    final Color[] defaultColors;

    /**
     * Constructs a new {@code SettingsUI} and initializes the UI components.
     * Sets up the layout and default values for player settings.
     */
    public SettingsUI() {
        setTitle("Zatacka");
        JPanel mainPanel = new JPanel();
        playerLabels = new JLabel[maxPlayers];
        colorButtons = new JButton[maxPlayers];
        nameTextFields = new JTextField[maxPlayers];
        leftControlButtons = new JButton[maxPlayers];
        rightControlButtons = new JButton[maxPlayers];
        loadDefaultsButton = new JButton("Load Defaults");

        defaultColors = new Color[]{
                new Color(255, 0, 0), new Color(0, 255, 0),
                new Color(0, 0, 255), new Color(255, 0, 255),
                new Color(0, 255, 255), new Color(255, 255, 0)
        };
        defaultLeftControlKeys = new char[]{'1', 'y', 'b', ',', 'o', '2'};
        defaultRightControlKeys = new char[]{'q', 'x', 'n', '.', '0', '3'};
        leftControlKeys = new char[6];
        rightControlKeys = new char[6];
        playerCheckBoxes = new JCheckBox[maxPlayers + 1];

        setSize(600, 400);
        setResizable(false);
        setLocation(300, 200);
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
        mainPanel.setBackground(new Color(205, 205, 205));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < maxPlayers; i++) {
            playerCheckBoxes[i] = new JCheckBox();
            playerLabels[i] = new JLabel("Player " + (i + 1) + ":");
            playerLabels[i].setPreferredSize(new Dimension(100, 25));
            playerLabels[i].setEnabled(false);

            nameTextFields[i] = new JTextField();
            nameTextFields[i].setPreferredSize(new Dimension(150, 25));
            nameTextFields[i].setEditable(false);

            colorButtons[i] = new JButton("Color");
            colorButtons[i].setPreferredSize(new Dimension(70, 25));
            colorButtons[i].setEnabled(false);

            leftControlButtons[i] = new JButton(String.valueOf(leftControlKeys[i]));
            leftControlButtons[i].setPreferredSize(new Dimension(100, 25));
            leftControlButtons[i].setEnabled(false);

            rightControlButtons[i] = new JButton(String.valueOf(rightControlKeys[i]));
            rightControlButtons[i].setPreferredSize(new Dimension(100, 25));
            rightControlButtons[i].setEnabled(false);

            mainPanel.add(playerCheckBoxes[i]);
            mainPanel.add(playerLabels[i]);
            mainPanel.add(nameTextFields[i]);
            mainPanel.add(colorButtons[i]);
            mainPanel.add(leftControlButtons[i]);
            mainPanel.add(rightControlButtons[i]);
        }

        playerCheckBoxes[maxPlayers] = new JCheckBox();
        allLabel = new JLabel("All");
        allLabel.setPreferredSize(new Dimension(412, 25));
        loadDefaultsButton.setPreferredSize(new Dimension(120, 25));

        mainPanel.add(playerCheckBoxes[maxPlayers]);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(allLabel);
        mainPanel.add(loadDefaultsButton);

        loadDefaultPlayerSettings();
        for (int i = 0; i < maxPlayers; i++) {
            playerCheckBoxes[i].addActionListener(this);
            colorButtons[i].addActionListener(this);
            leftControlButtons[i].addActionListener(this);
            rightControlButtons[i].addActionListener(this);
        }

        playerCheckBoxes[maxPlayers].addActionListener(this);
        loadDefaultsButton.addActionListener(this);

        JPanel speedPanel = createSpeedControl();
        mainPanel.add(speedPanel);

        spacer1 = new JLabel("");
        spacer1.setPreferredSize(new Dimension(1000, 25));
        mainPanel.add(spacer1);

        spacer2 = new JLabel("");
        spacer2.setPreferredSize(new Dimension(220, 25));
        mainPanel.add(spacer2);

        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(120, 25));

        mainPanel.add(startButton);
        playerCheckBoxes[0].setEnabled(true);
        playerCheckBoxes[0].setSelected(true);
        enableRow(0);
        playerCheckBoxes[1].setEnabled(true);
        playerCheckBoxes[1].setSelected(true);
        enableRow(1);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates and returns a panel for controlling the speed of the game.
     *
     * @return A JPanel containing the speed control components.
     */
    private JPanel createSpeedControl() {
        JPanel speedPanel = new JPanel();
        speedLabel = new JLabel("Speed Level:");
        speedLabel.setPreferredSize(new Dimension(90, 25));

        speedSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        speedSpinner.setPreferredSize(new Dimension(40, 25));

        speedPanel.add(speedLabel);
        speedPanel.add(speedSpinner);
        return speedPanel;
    }

    /**
     * Loads the default settings for the players, including names, colors, and control keys.
     * Disables all players initially except for the first two.
     */
    public void loadDefaultPlayerSettings() {
        for (int i = 0; i < maxPlayers; i++) {
            playerCheckBoxes[i].setSelected(false);
            nameTextFields[i].setText("Player " + (i + 1));
            colorButtons[i].setBackground(defaultColors[i]);
            leftControlButtons[i].setText(String.valueOf(defaultLeftControlKeys[i]));
            rightControlButtons[i].setText(String.valueOf(defaultRightControlKeys[i]));
            leftControlKeys[i] = defaultLeftControlKeys[i];
            rightControlKeys[i] = defaultRightControlKeys[i];
            disableRow(i);
        }
        playerCheckBoxes[0].setEnabled(true);
        playerCheckBoxes[0].setSelected(true);
        enableRow(0);
        playerCheckBoxes[1].setEnabled(true);
        playerCheckBoxes[1].setSelected(true);
        enableRow(1);
        playerCheckBoxes[maxPlayers].setSelected(false);
        playerCheckBoxes[maxPlayers].setEnabled(false);
    }

    /**
     * Enables the UI components for a specific player, allowing them to be configured.
     *
     * @param playerIndex The index of the player whose UI components should be enabled.
     */
    public void enableRow(int playerIndex) {
        playerLabels[playerIndex].setEnabled(true);
        nameTextFields[playerIndex].setEditable(true);
        colorButtons[playerIndex].setEnabled(true);
        leftControlButtons[playerIndex].setEnabled(true);
        rightControlButtons[playerIndex].setEnabled(true);
    }

    /**
     * Disables the UI components for a specific player, preventing them from being configured.
     *
     * @param playerIndex The index of the player whose UI components should be disabled.
     */
    public void disableRow(int playerIndex) {
        playerLabels[playerIndex].setEnabled(false);
        nameTextFields[playerIndex].setEditable(false);
        colorButtons[playerIndex].setEnabled(false);
        leftControlButtons[playerIndex].setEnabled(false);
        rightControlButtons[playerIndex].setEnabled(false);
    }

    /**
     * Generates an array of {@code Player} objects based on the selected settings in the UI.
     * This method reads the player names, colors, and control keys and creates {@code Player} instances.
     */
    public void generatePlayers() {
        int playerCount = 0;
        for (int i = 0; i < maxPlayers; i++) {
            if (playerCheckBoxes[i].isSelected()) {
                playerCount++;
            }
        }
        players = new Player[playerCount];
        int index = 0;
        for (int i = 0; i < maxPlayers; i++) {
            if (playerCheckBoxes[i].isSelected()) {
                players[index] = new Player(
                        nameTextFields[i].getText(),
                        colorButtons[i].getBackground(),
                        leftControlKeys[i],
                        rightControlKeys[i]
                );
                index++;
            }
        }
    }

    /**
     * Handles action events triggered by UI components.
     * This includes starting the game, loading default settings, and responding to player configuration changes.
     *
     * @param e The action event triggered by a user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Game")) {
            generatePlayers();

            int speedLevel = (int) speedSpinner.getValue();
            new GameEngine(players, speedLevel);
        } else if (e.getActionCommand().equals("Load Defaults")) {
            loadDefaultPlayerSettings();
        } else {
            handlePlayerActions(e);
        }
    }

    /**
     * Handles player-specific actions triggered by UI components, such as changing colors or control keys.
     *
     * @param e The action event triggered by a user interaction.
     */
    private void handlePlayerActions(ActionEvent e) {
        for (int i = 0; i < maxPlayers; i++) {
            if (e.getSource() == colorButtons[i]) {
                Color selectedColor = JColorChooser.showDialog(this, "Choose Player Color", colorButtons[i].getBackground());
                if (selectedColor != null) {
                    colorButtons[i].setBackground(selectedColor);
                }
            } else if (e.getSource() == leftControlButtons[i]) {
                String newKey = JOptionPane.showInputDialog(this, "Enter new left control key for " + nameTextFields[i].getText(), leftControlKeys[i]);
                if (newKey != null && !newKey.isEmpty()) {
                    leftControlKeys[i] = newKey.charAt(0);
                    leftControlButtons[i].setText(newKey);
                }
            } else if (e.getSource() == rightControlButtons[i]) {
                String newKey = JOptionPane.showInputDialog(this, "Enter new right control key for " + nameTextFields[i].getText(), rightControlKeys[i]);
                if (newKey != null && !newKey.isEmpty()) {
                    rightControlKeys[i] = newKey.charAt(0);
                    rightControlButtons[i].setText(newKey);
                }
            } else if (e.getSource() == playerCheckBoxes[i]) {
                if (playerCheckBoxes[i].isSelected()) {
                    enableRow(i);
                } else {
                    disableRow(i);
                }
            }
        }

        if (e.getSource() == playerCheckBoxes[maxPlayers]) {
            boolean allSelected = playerCheckBoxes[maxPlayers].isSelected();
            for (int i = 0; i < maxPlayers; i++) {
                playerCheckBoxes[i].setSelected(allSelected);
                if (allSelected) {
                    enableRow(i);
                } else {
                    disableRow(i);
                }
            }
        }
    }
}
