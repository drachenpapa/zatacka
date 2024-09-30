package de.drachenpapa.zatacka.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SettingsUITest {

    private SettingsUI settingsUI;

    @BeforeEach
    public void setUp() {
        settingsUI = new SettingsUI();
        settingsUI.setVisible(false);
    }

    @Test
    public void testLoadDefaultPlayerSettings() {
        settingsUI.loadDefaultPlayerSettings();

        for (int i = 0; i < settingsUI.maxPlayers; i++) {
            assertThat("Player name should match default", settingsUI.nameTextFields[i].getText(), is("Player " + (i + 1)));
            assertThat("Color should match default", settingsUI.colorButtons[i].getBackground(), is(settingsUI.defaultColors[i]));
            assertThat("Left control key should match default", settingsUI.leftControlKeys[i], is(settingsUI.defaultLeftControlKeys[i]));
            assertThat("Right control key should match default", settingsUI.rightControlKeys[i], is(settingsUI.defaultRightControlKeys[i]));
        }
    }

    @Test
    public void testEnableRow() {
        int playerIndex = 0;
        settingsUI.enableRow(playerIndex);

        assertThat("Player label should be enabled", settingsUI.playerLabels[playerIndex].isEnabled(), is(true));
        assertThat("Name text field should be editable", settingsUI.nameTextFields[playerIndex].isEditable(), is(true));
        assertThat("Color button should be enabled", settingsUI.colorButtons[playerIndex].isEnabled(), is(true));
        assertThat("Left control button should be enabled", settingsUI.leftControlButtons[playerIndex].isEnabled(), is(true));
        assertThat("Right control button should be enabled", settingsUI.rightControlButtons[playerIndex].isEnabled(), is(true));
    }

    @Test
    public void testDisableRow() {
        int playerIndex = 0;
        settingsUI.disableRow(playerIndex);

        assertThat("Player label should be disabled", settingsUI.playerLabels[playerIndex].isEnabled(), is(false));
        assertThat("Name text field should not be editable", settingsUI.nameTextFields[playerIndex].isEditable(), is(false));
        assertThat("Color button should be disabled", settingsUI.colorButtons[playerIndex].isEnabled(), is(false));
        assertThat("Left control button should be disabled", settingsUI.leftControlButtons[playerIndex].isEnabled(), is(false));
        assertThat("Right control button should be disabled", settingsUI.rightControlButtons[playerIndex].isEnabled(), is(false));
    }

    @Test
    public void testGeneratePlayers() {
        settingsUI.playerCheckBoxes[0].setSelected(true);
        settingsUI.nameTextFields[0].setText("Test Player 1");
        settingsUI.colorButtons[0].setBackground(Color.RED);
        settingsUI.leftControlKeys[0] = 'A';
        settingsUI.rightControlKeys[0] = 'D';

        settingsUI.generatePlayers();

        assertNotNull(settingsUI.players, "Players array should not be null");
        assertThat("First player's name should match", settingsUI.players[0].getPlayerName(), is("Test Player 1"));
        assertThat("First player's color should match", settingsUI.players[0].getColor(), is(Color.RED));
        assertThat("First player's left control key should match", settingsUI.players[0].getLeftKey(), is('A'));
        assertThat("First player's right control key should match", settingsUI.players[0].getRightKey(), is('D'));
    }
}
