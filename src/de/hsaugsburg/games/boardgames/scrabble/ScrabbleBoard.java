package de.hsaugsburg.games.boardgames.scrabble;

import de.hsaugsburg.games.boardgames.Board;

public class ScrabbleBoard extends Board<LetterPiece, ScrabbleSquareDetails> {
	
	private static final long serialVersionUID = -4236101529894246793L;
	private boolean initialized = false;
	
	/**
	 *Calls super class {@link Board} with a standard size of 15 x 15.
     */
	public ScrabbleBoard() {
		super(15, 15);
	}
	
	public void reset() {
		super.reset();
		for (int column = 0; column < getHeight(); column++) {
			for (int row = 0; row < getWidth(); row++) {
				putDetails(new ScrabbleSquareDetails(), row, column);
				getDetails(row, column).setMultiplier(SquareMultiplier.STANDARDMULTIPLIER);
			}
		}
		
		getDetails(0, 0).setMultiplier(SquareMultiplier.WORDMULTIPLIER3);
		getDetails(0, 3).setMultiplier(SquareMultiplier.LETTERMULTIPLIER2);
		getDetails(0, 7).setMultiplier(SquareMultiplier.WORDMULTIPLIER3);
		getDetails(1, 1).setMultiplier(SquareMultiplier.WORDMULTIPLIER2);
		getDetails(1, 5).setMultiplier(SquareMultiplier.LETTERMULTIPLIER3);
		getDetails(2, 2).setMultiplier(SquareMultiplier.WORDMULTIPLIER2);
		getDetails(2, 6).setMultiplier(SquareMultiplier.LETTERMULTIPLIER2);
		getDetails(3, 3).setMultiplier(SquareMultiplier.WORDMULTIPLIER2);
		getDetails(3, 7).setMultiplier(SquareMultiplier.LETTERMULTIPLIER2);
		getDetails(4, 4).setMultiplier(SquareMultiplier.WORDMULTIPLIER2);
		getDetails(5, 5).setMultiplier(SquareMultiplier.LETTERMULTIPLIER3);
		getDetails(6, 6).setMultiplier(SquareMultiplier.LETTERMULTIPLIER2);
		getDetails(7, 7).setMultiplier(SquareMultiplier.WORDMULTIPLIER2);
		
		intitializeScrabbleBoard(0, 0);
		intitializeScrabbleBoard(14, 0);
		intitializeScrabbleBoard(0, 14);
		intitializeScrabbleBoard(14, 14);
		
		initialized = true;
	}
	
	private void intitializeScrabbleBoard(int a, int b) {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				getDetails(Math.abs(a-column), Math.abs(b-row)).setMultiplier(getDetails(row, column).getMultiplier());
			}
		}
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(10000);
		printNumbers(buffer);
		for (int row = 0; row < getWidth(); row++) {
			buffer.append((char)(row + 'A'));
			for (int column = 0; column < getHeight(); column++) {
				if (getPiece(row, column) != null) {
					if (getDetails(row, column).isFixed()) {
						buffer.append("[" + getPiece(row, column) + "]");
					} else {
						buffer.append(" " + getPiece(row, column)+ " ");
					}
				} else if (row == 7 && column == 7) {
					buffer.append(" * ");
				} else if (getDetails(row, column).getWordMultiplier() == 2) {
					buffer.append(" __");
				} else if(getDetails(row, column).getWordMultiplier() == 3) {
					buffer.append("___");
				} else if(getDetails(row, column).getLetterMultiplier() == 1) {
					buffer.append(" . ");
				} else if(getDetails(row, column).getLetterMultiplier() == 2) {
					buffer.append(" ..");
				} else if(getDetails(row, column).getLetterMultiplier() == 3) {
					buffer.append("...");
				}
			}
			buffer.append((char)(row + 'A'));
			buffer.append("\n");
		}
		printNumbers(buffer);
		return buffer.toString();
	}
	
	private void printNumbers(StringBuffer buffer) {
		buffer.append(" ");
		for (int row = 1; row < 10; row++) {
			buffer.append(" " + row + " ");
		}
		for (int row = 10; row <= getWidth(); row++) {
			buffer.append(row + " ");
		}
		buffer.append("\n");
	}
	
}
