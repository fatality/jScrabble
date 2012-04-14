package de.hsaugsburg.games.boardgames.connectfour.exceptions;

import de.hsaugsburg.games.boardgames.exceptions.GameException;

@SuppressWarnings("serial")
public class NextDropNotAllowedException extends GameException {
	
	public NextDropNotAllowedException() {
		super();
	}
	
	public NextDropNotAllowedException(String s) {
		super(s);
	}
	
}
