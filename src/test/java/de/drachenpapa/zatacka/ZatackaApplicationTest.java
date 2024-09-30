package de.drachenpapa.zatacka;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ZatackaApplicationTest {

    @Test
    void applicationShouldStartWithoutThrowingExceptions() {
        assertDoesNotThrow(() -> ZatackaApplication.main(new String[]{}),
                "The application should start without throwing an exception");
    }

    @Test
    void swingApplicationShouldStartOnEventDispatchThread() {
        assertDoesNotThrow(() -> SwingUtilities.invokeAndWait(() -> ZatackaApplication.main(new String[]{})),
                "The application should start on the Event Dispatch Thread without throwing an exception");
    }
}
