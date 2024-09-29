package de.drachenpapa.zatacka.dialogs;

import de.drachenpapa.zatacka.engine.ZatackaEngine;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * This class represents the game configuration panel for local gameplay.
 * It allows players to configure their settings before starting the game.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class GameConfigurationPanelLocal extends GameSettingsPanel implements ActionListener {

    private static final int MAX_PLAYERS = 6;
    private JPanel panel;
    private JSpinner speed;
    private JLabel textSpeed;
    private JButton start, loadDefaults;

    /**
     * Initializes the GameConfigurationPanelLocal and sets up the GUI components.
     */
    public GameConfigurationPanelLocal() {
        super(MAX_PLAYERS);
        initializePanel();
        setupFrame();
        setupActionListeners();
        setupSpeedSelector();
        initializePlayerOptions();
        add(panel);
        setVisible(true);
    }

    /**
     * Initializes the main panel for the configuration settings.
     */
    private void initializePanel() {
        panel = drawRaw();
        panel.setPreferredSize(new Dimension(600, 400));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
        panel.setBackground(new Color(205, 205, 205));
    }

    /**
     * Sets up the main application frame properties.
     */
    private void setupFrame() {
        setSize(600, 400);
        setResizable(false);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets up the action listeners for buttons and checkboxes.
     */
    private void setupActionListeners() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            checks[i].addActionListener(this);
            colors[i].addActionListener(this);
            leftButtons[i].addActionListener(this);
            rightButtons[i].addActionListener(this);
        }
        checks[MAX_PLAYERS].addActionListener(this);
        super.loadDefaults.addActionListener(this);
    }

    /**
     * Initializes the speed selector component.
     */
    private void setupSpeedSelector() {
        speed = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        speed.setPreferredSize(new Dimension(40, 25));
        textSpeed = new JLabel("Speed-Level:");
        textSpeed.setPreferredSize(new Dimension(90, 25));

        panel.add(textSpeed);
        panel.add(speed);

        JLabel label1 = new JLabel();
        label1.setPreferredSize(new Dimension(1000, 25));
        panel.add(label1);

        JLabel label2 = new JLabel();
        label2.setPreferredSize(new Dimension(220, 25));
        panel.add(label2);

        start = new JButton("Start Game!");
        start.setPreferredSize(new Dimension(120, 25));
        panel.add(start);
    }

    /**
     * Initializes player options, enabling the default player checkbox.
     */
    private void initializePlayerOptions() {
        checks[1].setEnabled(true);
        checks[1].setSelected(true);
        enableRow(1);
    }

    /**
     * Handles action events triggered by buttons and checkboxes.
     *
     * @param e the action event triggered
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            saveOptions();
            generatePlayers(colors);
            new ZatackaEngine(players, (int) speed.getValue());
        } else if (e.getSource() == loadDefaults) {
            loadDefaultOptions();
        }

        handleColorSelection(e);
        handleButtonKeySetting(e);
        handleCheckboxSelection(e);
    }

    /**
     * Handles color selection for player colors.
     *
     * @param e the action event triggered
     */
    private void handleColorSelection(ActionEvent e) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (e.getSource() == colors[i]) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose a color", colors[i].getBackground());
                colors[i].setBackground(selectedColor != null ? selectedColor : new Color(215, 215, 235));
            }
        }
    }

    /**
     * Handles key setting for left and right buttons.
     *
     * @param e the action event triggered
     */
    private void handleButtonKeySetting(ActionEvent e) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (e.getSource() == leftButtons[i] || e.getSource() == rightButtons[i]) {
                JButton button = (JButton) e.getSource();
                button.requestFocus();
                final int index = i;
                button.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        char keyChar = e.getKeyChar();
                        button.setText(KeyEvent.getKeyText(e.getKeyCode()));
                        if (button == leftButtons[index]) {
                            leftButtonKeys[index] = keyChar;
                        } else {
                            rightButtonKeys[index] = keyChar;
                        }
                        button.removeKeyListener(this);
                    }
                });
            }
        }
    }

    /**
     * Handles checkbox selections for player options.
     *
     * @param e the action event triggered
     */
    private void handleCheckboxSelection(ActionEvent e) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (e.getSource() == checks[i]) {
                if (checks[i].isSelected()) {
                    enableRow(i);
                } else {
                    disableRow(i);
                    checks[MAX_PLAYERS].setSelected(false);
                }
                updateStartButton();
            }
        }

        if (e.getSource() == checks[MAX_PLAYERS]) {
            for (int i = 0; i < MAX_PLAYERS; i++) {
                checks[i].setSelected(checks[MAX_PLAYERS].isSelected());
                if (checks[MAX_PLAYERS].isSelected()) {
                    enableRow(i);
                } else {
                    disableRow(i);
                }
            }
            updateStartButton();
        }
    }

    /**
     * Updates the state of the start button based on player selections.
     */
    private void updateStartButton() {
        start.setEnabled(getChecks(checks, MAX_PLAYERS) > 1);
    }
}
