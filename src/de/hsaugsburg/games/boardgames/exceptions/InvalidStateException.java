package de.hsaugsburg.games.boardgames.exceptions;

@SuppressWarnings("serial")
public class InvalidStateException extends GameException {
	
	public InvalidStateException() {
		super();
	}
	
	public InvalidStateException(String s) {
		super(s);
	}
	
}
