package de.drachenpapa.zatacka.dialogs;

import de.drachenpapa.zatacka.engine.Player;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.*;

/**
 * Superclass which contains all the main elements for the GUIs.
 * All classes with GUI in name inherit from it.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class GameSettingsPanel extends JFrame {
    protected final int maxPlayers;
    protected JButton[] colors, leftButtons, rightButtons;
    protected JButton start, loadDefaults;
    protected JCheckBox[] checks;
    protected JLabel[] playerLabels;
    protected JLabel all;
    protected JSpinner speed;
    protected JTextField[] textFields;
    protected char[] leftButtonKeys, rightButtonKeys;
    protected StringBuffer sbufferTrue;
    protected final Color[] defaultColors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW
    };
    protected final char[] defaultLeftButtonKeys = {'1', 'y', 'b', ',', 'o', '2'};
    protected final char[] defaultRightButtonKeys = {'q', 'x', 'n', '.', '0', '3'};
    protected Player[] players;


    /**
     * Constructor: Constructs the panel which is returned by calling the method drawRaw().
     */
    public GameSettingsPanel(int maxPlayers) {
        super("Zatacka");

        this.maxPlayers = maxPlayers;
        initializeComponents();
        loadOptions();
    }

    private void initializeComponents() {
        colors = new JButton[maxPlayers];
        textFields = new JTextField[maxPlayers];
        leftButtons = new JButton[maxPlayers];
        rightButtons = new JButton[maxPlayers];
        checks = new JCheckBox[maxPlayers + 1];
        playerLabels = new JLabel[maxPlayers];

        for (int i = 0; i < maxPlayers; i++) {
            initializePlayerComponents(i);
        }

        // Initialize the "All" checkbox and load defaults button
        checks[maxPlayers] = new JCheckBox();
        all = new JLabel("All");
        loadDefaults = new JButton("Load Defaults");

        // Layout configurations can be set here
    }

    private void initializePlayerComponents(int index) {
        checks[index] = new JCheckBox();
        playerLabels[index] = new JLabel("Name Player " + (index + 1) + ":");
        playerLabels[index].setPreferredSize(new Dimension(100, 25));

        textFields[index] = new JTextField();
        textFields[index].setPreferredSize(new Dimension(150, 25));
        textFields[index].setEditable(false);

        colors[index] = new JButton("Color");
        colors[index].setPreferredSize(new Dimension(70, 25));
        colors[index].setEnabled(false);

        leftButtons[index] = createButton(String.valueOf(defaultLeftButtonKeys[index]), index);
        rightButtons[index] = createButton(String.valueOf(defaultRightButtonKeys[index]), index);

        // Enable first player settings by default
        if (index == 0) {
            enableRow(index);
            checks[0].setSelected(true);
        }
    }

    private JButton createButton(String key, int index) {
        JButton button = new JButton(key);
        button.setPreferredSize(new Dimension(100, 25));
        button.setEnabled(false);
        return button;
    }

    public JPanel drawRaw() {
        JPanel panel = new JPanel();
        for (int i = 0; i < maxPlayers; i++) {
            panel.add(checks[i]);
            panel.add(playerLabels[i]);
            panel.add(textFields[i]);
            panel.add(colors[i]);
            panel.add(leftButtons[i]);
            panel.add(rightButtons[i]);
        }

        panel.add(checks[maxPlayers]);
        panel.add(Box.createVerticalStrut(50));
        panel.add(all);
        panel.add(loadDefaults);
        return panel;
    }

    public void saveOptions() {
        Properties options = new Properties();
        for (int i = 0; i < maxPlayers; i++) {
            options.setProperty("checks" + i, String.valueOf(checks[i].isSelected()));
            options.setProperty("name" + i, textFields[i].getText());
            options.setProperty("colors" + i, colorToString(colors[i].getBackground()));
            options.setProperty("lbutton" + i, leftButtons[i].getText());
            options.setProperty("rbutton" + i, rightButtons[i].getText());
        }
        savePropertiesToFile(options, "options.ini");
    }

    private void savePropertiesToFile(Properties options, String fileName) {
        try (OutputStream output = Files.newOutputStream(Paths.get(fileName))) {
            options.store(output, "zatacka");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String colorToString(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }

    public void loadOptions() {
        Properties options = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("options.ini"))) {
            options.load(input);
            for (int i = 0; i < maxPlayers; i++) {
                checks[i].setSelected(Boolean.parseBoolean(options.getProperty("checks" + i)));
                if (checks[i].isSelected()) {
                    enableRow(i);
                } else {
                    disableRow(i);
                }
                textFields[i].setText(options.getProperty("name" + i));
                colors[i].setBackground(parseColor(options.getProperty("colors" + i)));
                leftButtons[i].setText(options.getProperty("lbutton" + i));
                rightButtons[i].setText(options.getProperty("rbutton" + i));
            }
        } catch (IOException e) {
            loadDefaultOptions();
        }
    }

    private Color parseColor(String colorString) {
        String[] rgb = colorString.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    public void loadDefaultOptions() {
        for (int i = 0; i < maxPlayers; i++) {
            leftButtons[i].setText(String.valueOf(defaultLeftButtonKeys[i]));
            rightButtons[i].setText(String.valueOf(defaultRightButtonKeys[i]));
            colors[i].setBackground(defaultColors[i]);
            textFields[i].setText("Player-" + (i + 1));
        }
    }

    public void enableRow(int row) {
        setRowEnabled(row, true);
    }

    public void disableRow(int row) {
        setRowEnabled(row, false);
    }

    private void setRowEnabled(int row, boolean enabled) {
        playerLabels[row].setEnabled(enabled);
        textFields[row].setEditable(enabled);
        colors[row].setEnabled(enabled);
        leftButtons[row].setEnabled(enabled);
        rightButtons[row].setEnabled(enabled);
    }

    public int getChecks(JCheckBox[] checks, int players) {
        return (int) Arrays.stream(checks, 0, players).filter(JCheckBox::isSelected).count();
    }

    public JCheckBox[] getCheckArray() {
        return checks;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void generatePlayers(JButton[] colors) {
        int checkedCount = getChecks(checks, maxPlayers);
        players = new Player[checkedCount];
        int y = 0;

        for (int i = 0; i < maxPlayers; i++) {
            if (checks[i].isSelected()) {
                players[y++] = new Player(
                        textFields[i].getText(),
                        colors[i].getBackground(),
                        leftButtonKeys[i],
                        rightButtonKeys[i]
                );
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
