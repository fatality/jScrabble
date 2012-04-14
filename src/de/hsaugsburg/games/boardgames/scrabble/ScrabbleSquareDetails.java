package de.hsaugsburg.games.boardgames.scrabble;

import java.io.Serializable;

public class ScrabbleSquareDetails implements Serializable {

	private static final long serialVersionUID = -3371678220363897922L;
	private SquareMultiplier multiplier;
	private boolean pieceFixed;
	
	public ScrabbleSquareDetails() {
		this.pieceFixed = false;
	}
	
	public void setMultiplier(SquareMultiplier multiplier) {
		this.multiplier = multiplier;
	}
	
	public SquareMultiplier getMultiplier() {
		return multiplier;
	}
	
	public int getWordMultiplier() {
		return multiplier.getWordMultiplier();
	}
	
	public int getLetterMultiplier() {
		return multiplier.getLetterMultiplier();
	}
	
	public void setFixed() {
		this.pieceFixed = true;
	}
	
	public boolean isFixed() {
		return this.pieceFixed;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ScrabbleSquareDetails) {
			ScrabbleSquareDetails details = (ScrabbleSquareDetails) obj;
			if (this.multiplier == null) {
				return this.pieceFixed == details.pieceFixed && this.multiplier == details.multiplier;
			}
			return this.pieceFixed == details.pieceFixed && this.multiplier.equals(details.multiplier);
		}
		return false;
	}
	
}
