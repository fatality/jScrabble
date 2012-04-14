package de.hsaugsburg.games.boardgames.exceptions;

@SuppressWarnings("serial")
public class OutsideBoardException extends GameException {
	
	public OutsideBoardException() {
		super();
	}
	
	public OutsideBoardException(String s) {
		super(s);
	}
	
}
