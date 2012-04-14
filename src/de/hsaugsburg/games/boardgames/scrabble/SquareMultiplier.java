package de.hsaugsburg.games.boardgames.scrabble;

public enum SquareMultiplier {

	STANDARDMULTIPLIER(1, 1),
	WORDMULTIPLIER2(2, 1),
	WORDMULTIPLIER3(3, 1),
	LETTERMULTIPLIER2(1, 2),
	LETTERMULTIPLIER3(1, 3);
	
	private int wordMultiplier;
	private int letterMultiplier;
	
	private SquareMultiplier(int wordMultiplier, int letterMultiplier){
		this.wordMultiplier = wordMultiplier;
		this.letterMultiplier = letterMultiplier;
	}
	
	public int getWordMultiplier(){
		return wordMultiplier;
	}
	
	public int getLetterMultiplier(){
		return letterMultiplier;
	}
	
}
