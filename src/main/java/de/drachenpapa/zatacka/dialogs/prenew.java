package de.drachenpapa.zatacka.dialogs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Pre-Dialog for the game Zatacka.
 * This class provides the initial user interface for selecting game options.
 *
 * @author Henning Steinberg (@drachenpapa)
 * @version 1.0
 */
public class prenew extends JFrame implements ActionListener {
    private JButton local, server, join;
    private JPanel panel;

    /**
     * Constructs the pre-dialog for Zatacka.
     * Initializes the GUI components and sets up the frame.
     */
    public prenew() {
        super("Zatacka");
        initializeUI();
        setupFrame();
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
        panel.setBackground(new Color(205, 205, 205));

        addLabels();
        addButtons();
        add(panel);
    }

    /**
     * Sets up the JFrame properties.
     */
    private void setupFrame() {
        setSize(300, 200);
        setResizable(false);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Adds labels to the panel.
     */
    private void addLabels() {
        String[] labelTexts = {
                "Welcome to Zatacka!",
                "Please choose,",
                "which type of game you want to start"
        };

        for (String text : labelTexts) {
            JLabel label = new JLabel(text);
            label.setPreferredSize(new Dimension(300, 12));
            panel.add(label);
        }

        // Adding an empty space
        JLabel emptyField = new JLabel("");
        emptyField.setPreferredSize(new Dimension(300, 40));
        panel.add(emptyField);
    }

    /**
     * Adds buttons to the panel and sets their action listeners.
     */
    private void addButtons() {
        local = createButton("Local");
        server = createButton("Host");
        server.setEnabled(false);
        join = createButton("Client");
        join.setEnabled(false);
    }

    /**
     * Creates a button with the specified text and action listener.
     *
     * @param text The text to display on the button.
     * @return The created JButton.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    /**
     * Shows the correct graphical user interface after a button is pressed.
     *
     * @param e ActionEvent triggered by button clicks.
     */
    public void actionPerformed(ActionEvent e) {
        local.setEnabled(false);
        join.setEnabled(false);
        server.setEnabled(false);

        if (e.getSource() == local) {
            new GameConfigurationPanelLocal();
        } else if (e.getSource() == server) {
//            new GUI_server();
        } else if (e.getSource() == join) {
//            new GUI_join();
        }
    }
}
