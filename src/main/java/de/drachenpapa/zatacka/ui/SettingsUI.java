package de.drachenpapa.zatacka.ui;

import de.drachenpapa.zatacka.game.GameEngine;
import de.drachenpapa.zatacka.game.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * ZatackaSettingsUI handles the graphical user interface for configuring the
 * game settings, such as player names, controls, colors, and speed.
 */
public class SettingsUI extends JFrame implements ActionListener {

	/** Maximum number of players allowed in the game */
	protected final int maxPlayers;

	/** Array of buttons to select player colors */
	protected JButton[] colorButtons, leftControlButtons, rightControlButtons;

	/** Start button and Load Defaults button */
	protected JButton startButton, loadDefaultsButton;

	/** Array of checkboxes for enabling/disabling players */
	protected JCheckBox[] playerCheckBoxes;

	/** Labels for UI components */
	protected JLabel allLabel, speedLabel, spacer1, spacer2;
	protected JLabel[] playerLabels;

	/** Spinner for setting the game speed */
	protected JSpinner speedSpinner;

	/** Text fields for entering player names */
	protected JTextField[] nameTextFields;

	/** Arrays for storing control key assignments */
	protected char[] leftControlKeys;
	protected char[] rightControlKeys;
	protected char[] defaultRightControlKeys;
	protected char[] defaultLeftControlKeys;

	/** Array of player objects representing each player in the game */
	protected Player[] players;

	/** Main panel for arranging UI components */
	private JPanel mainPanel;

	/** Properties for saving and loading game settings */
	private Properties options;

	/** Buffer for checking boolean values */
	private StringBuffer trueBuffer;

	/** Default colors for each player */
	private Color[] defaultColors;

	/**
	 * Constructor to initialize the game settings UI with default values.
	 */
	public SettingsUI() {
		maxPlayers = 6;
		setTitle("Zatacka");
		mainPanel = new JPanel();
		playerLabels = new JLabel[maxPlayers];
		colorButtons = new JButton[maxPlayers];
		nameTextFields = new JTextField[maxPlayers];
		leftControlButtons = new JButton[maxPlayers];
		rightControlButtons = new JButton[maxPlayers];
		loadDefaultsButton = new JButton("Load Defaults");

		defaultColors = new Color[] {
				new Color(255, 0, 0), new Color(0, 255, 0),
				new Color(0, 0, 255), new Color(255, 0, 255),
				new Color(0, 255, 255), new Color(255, 255, 0)
		};
		defaultLeftControlKeys = new char[] {'1', 'y', 'b', ',', 'o', '2'};
		defaultRightControlKeys = new char[] {'q', 'x', 'n', '.', '0', '3'};
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

		loadOptions();
		for (int i = 0; i < maxPlayers; i++) {
			playerCheckBoxes[i].addActionListener(this);
			colorButtons[i].addActionListener(this);
			leftControlButtons[i].addActionListener(this);
			rightControlButtons[i].addActionListener(this);
		}

		playerCheckBoxes[maxPlayers].addActionListener(this);
		loadDefaultsButton.addActionListener(this);

		speedSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
		speedSpinner.setPreferredSize(new Dimension(40, 25));
		speedLabel = new JLabel("Speed Level:");
		speedLabel.setPreferredSize(new Dimension(90, 25));

		mainPanel.add(speedLabel);
		mainPanel.add(speedSpinner);

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
		playerCheckBoxes[1].setEnabled(true);
		playerCheckBoxes[1].setSelected(true);
		enableRow(1);

		add(mainPanel);
		setVisible(true);
	}

	/**
	 * Saves the current configuration to a properties file.
	 */
	public void saveOptions() {
		options = new Properties();
		for (int i = 0; i < maxPlayers; i++) {
			options.setProperty("check" + i, "" + playerCheckBoxes[i].isSelected());
			options.setProperty("name" + i, nameTextFields[i].getText());
			Color color = colorButtons[i].getBackground();
			options.setProperty("color" + i, color.getRed() + "," + color.getGreen() + "," + color.getBlue());
			options.setProperty("leftControl" + i, leftControlButtons[i].getText());
			options.setProperty("rightControl" + i, rightControlButtons[i].getText());
		}
		try {
			options.store(new FileOutputStream("options.ini"), "zatacka");
		} catch (IOException ioe) {
			// Handle IOException
		}
	}

	/**
	 * Loads the configuration from the properties file. If the file is not found,
	 * default options are loaded.
	 */
	public void loadOptions() {
		try {
			options = new Properties();
			options.load(new FileInputStream("options.ini"));
			trueBuffer = new StringBuffer("true");
			for (int i = 0; i < maxPlayers; i++) {
				playerCheckBoxes[i].setSelected(options.getProperty("check" + i).contentEquals(trueBuffer));
				if (playerCheckBoxes[i].isSelected()) {
					enableRow(i);
				} else {
					disableRow(i);
				}
				nameTextFields[i].setText(options.getProperty("name" + i));
				String[] colorValues = options.getProperty("color" + i).split(",");
				colorButtons[i].setBackground(new Color(
						Integer.parseInt(colorValues[0]),
						Integer.parseInt(colorValues[1]),
						Integer.parseInt(colorValues[2])));
				leftControlButtons[i].setText(options.getProperty("leftControl" + i));
				rightControlButtons[i].setText(options.getProperty("rightControl" + i));
				leftControlKeys[i] = options.getProperty("leftControl" + i).charAt(0);
				rightControlKeys[i] = options.getProperty("rightControl" + i).charAt(0);
			}
		} catch (Exception e) {
			loadDefaultOptions();
		}
	}

	/**
	 * Loads the default settings for all players.
	 */
	public void loadDefaultOptions() {
		for (int i = 0; i < maxPlayers; i++) {
			leftControlButtons[i].setText(String.valueOf(defaultLeftControlKeys[i]));
			rightControlButtons[i].setText(String.valueOf(defaultRightControlKeys[i]));
			colorButtons[i].setBackground(defaultColors[i]);
			nameTextFields[i].setText("Player-" + (i + 1));
		}
	}

	/**
	 * Enables all input fields for a specific player row.
	 *
	 * @param row The row index to enable
	 */
	public void enableRow(int row) {
		playerLabels[row].setEnabled(true);
		nameTextFields[row].setEditable(true);
		colorButtons[row].setEnabled(true);
		leftControlButtons[row].setEnabled(true);
		rightControlButtons[row].setEnabled(true);
	}

	/**
	 * Disables all input fields for a specific player row.
	 *
	 * @param row The row index to disable
	 */
	public void disableRow(int row) {
		playerLabels[row].setEnabled(false);
		nameTextFields[row].setEditable(false);
		colorButtons[row].setEnabled(false);
		leftControlButtons[row].setEnabled(false);
		rightControlButtons[row].setEnabled(false);
	}

	/**
	 * Counts the number of selected player checkboxes.
	 *
	 * @param checkBoxes The array of player checkboxes
	 * @param players    The total number of players
	 * @return The number of selected players
	 */
	public int getSelectedPlayerCount(JCheckBox[] checkBoxes, int players) {
		int count = 0;
		for (int i = 0; i < players; i++) {
			if (checkBoxes[i].isSelected()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Generates Player objects based on the selected player configuration.
	 *
	 * @param colorButtons Array of color selection buttons
	 */
	public void generatePlayers(JButton[] colorButtons) {
		players = new Player[getSelectedPlayerCount(playerCheckBoxes, maxPlayers)];
		int index = 0;
		for (int i = 0; i < maxPlayers; i++) {
			if (playerCheckBoxes[i].isSelected()) {
				players[index] = new Player(
						nameTextFields[i].getText(),
						colorButtons[i].getBackground(),
						leftControlKeys[i],
						rightControlKeys[i]);
				index++;
			}
		}
	}

	/**
	 * Handles the actions performed on the various buttons and checkboxes in the UI.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			generatePlayers(colorButtons);
			saveOptions();
			new GameEngine(players, (int) speedSpinner.getValue());
			dispose();
		}

		if (e.getSource() == loadDefaultsButton) {
			loadDefaultOptions();
		}

		for (int i = 0; i < maxPlayers; i++) {
			if (e.getSource() == playerCheckBoxes[i]) {
				if (playerCheckBoxes[i].isSelected()) {
					enableRow(i);
				} else {
					disableRow(i);
				}
			}
			if (e.getSource() == colorButtons[i]) {
				colorButtons[i].setBackground(JColorChooser.showDialog(this, "Choose Player Color", colorButtons[i].getBackground()));
			}
			if (e.getSource() == leftControlButtons[i]) {
				String result = JOptionPane.showInputDialog("Enter left control key:");
				if (result != null) {
					leftControlButtons[i].setText(result);
					leftControlKeys[i] = result.charAt(0);
				}
			}
			if (e.getSource() == rightControlButtons[i]) {
				String result = JOptionPane.showInputDialog("Enter right control key:");
				if (result != null) {
					rightControlButtons[i].setText(result);
					rightControlKeys[i] = result.charAt(0);
				}
			}
		}

		if (e.getSource() == playerCheckBoxes[maxPlayers]) {
			boolean selected = playerCheckBoxes[maxPlayers].isSelected();
			for (int i = 0; i < maxPlayers; i++) {
				playerCheckBoxes[i].setSelected(selected);
				if (selected) {
					enableRow(i);
				} else {
					disableRow(i);
				}
			}
		}
	}
}
