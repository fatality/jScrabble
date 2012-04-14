package de.hsaugsburg.games.boardgames.scrabble.test;

import de.hsaugsburg.games.boardgames.scrabble.RandomPool;

public class RandomPoolDummy<T> extends RandomPool<T>{
	
	private static final long serialVersionUID = 9136507612703344077L;

	public RandomPoolDummy() {
		random.setSeed(145533);
	}
	
}
