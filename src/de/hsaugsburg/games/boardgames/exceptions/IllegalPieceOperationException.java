package de.hsaugsburg.games.boardgames.exceptions;

@SuppressWarnings("serial")
public class IllegalPieceOperationException extends GameException {
	
	public IllegalPieceOperationException() {
		super();
	}
	
	public IllegalPieceOperationException(String s) {
		super(s);
	}

}
