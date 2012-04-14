package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * handles all events which should force the application to close<br>
 */
class ExitListener extends WindowAdapter implements ActionListener {
    private Controller controller;

    public ExitListener(Controller controller) {
        this.controller = controller;
    }

    private void closeApplication() {
        controller.exit();
    }

    public void windowClosing(WindowEvent e) {
        closeApplication();
    }

    public void actionPerformed(ActionEvent e) {
        closeApplication();
    }
    
}
