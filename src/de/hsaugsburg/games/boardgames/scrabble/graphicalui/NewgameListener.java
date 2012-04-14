package de.hsaugsburg.games.boardgames.scrabble.graphicalui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewgameListener implements ActionListener {
	Controller controller;
	
	public NewgameListener(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.newgame();
	}
	
}
