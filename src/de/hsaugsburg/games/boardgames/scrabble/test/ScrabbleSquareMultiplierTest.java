package de.hsaugsburg.games.boardgames.scrabble.test;

import de.hsaugsburg.games.boardgames.scrabble.ScrabbleSquareDetails;
import de.hsaugsburg.games.boardgames.scrabble.SquareMultiplier;

public class ScrabbleSquareMultiplierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScrabbleSquareDetails sqm1 = new ScrabbleSquareDetails();
		sqm1.setMultiplier(SquareMultiplier.STANDARDMULTIPLIER);
		ScrabbleSquareDetails sqm2 = new ScrabbleSquareDetails();
		sqm2.setMultiplier(SquareMultiplier.STANDARDMULTIPLIER);
		System.out.println(sqm2.equals(sqm1));
		System.out.println(sqm1.equals(sqm2));
	}

}
