package de.hsaugsburg.games.boardgames.connectfour.exceptions;

import de.hsaugsburg.games.boardgames.exceptions.GameException;

@SuppressWarnings("serial")
public class ColumnFullException extends GameException {
	
	public ColumnFullException() {
		super();
	}
	
	public ColumnFullException(String s) {
		super(s);
	}

}
