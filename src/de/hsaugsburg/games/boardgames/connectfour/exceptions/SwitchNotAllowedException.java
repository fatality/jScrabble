package de.hsaugsburg.games.boardgames.connectfour.exceptions;

import de.hsaugsburg.games.boardgames.exceptions.GameException;

@SuppressWarnings("serial")
public class SwitchNotAllowedException extends GameException {
	
	public SwitchNotAllowedException() {
		super();
	}
	
	public SwitchNotAllowedException(String s) {
		super(s);
	}
	
}
