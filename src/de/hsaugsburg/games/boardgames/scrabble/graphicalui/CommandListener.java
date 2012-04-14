package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandListener implements ActionListener {
	protected Controller controller;
	
	public CommandListener(Controller controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("commit")) {
			controller.commit();
		} else if(e.getActionCommand().equals("reject")) {
			controller.reject();
		} else if(e.getActionCommand().equals("agree")) {
			controller.agree();
		} else if(e.getActionCommand().equals("pass")) {
			controller.pass();
		}
	}
	
}
