package de.hsaugsburg.games.boardgames.exceptions;

@SuppressWarnings("serial")
public class UnknownCommandException extends GameException {
	
	public UnknownCommandException() {
		super();
	}
	
	public UnknownCommandException(String s) {
		super(s);
	}
	
}
