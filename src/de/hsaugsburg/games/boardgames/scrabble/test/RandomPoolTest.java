package de.hsaugsburg.games.boardgames.scrabble.test;

import de.hsaugsburg.games.boardgames.scrabble.Command;
import de.hsaugsburg.games.boardgames.scrabble.CommandScanner;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.RandomPool;
import de.hsaugsburg.games.boardgames.scrabble.ScrabblePlayer;

public class RandomPoolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RandomPool<LetterPiece> pool = new RandomPool<LetterPiece>();
		LetterPiece[] pieces = LetterPiece.values();
		ScrabblePlayer player = new ScrabblePlayer("player", new CommandScanner(Command.values()));
		
		
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].getCount(); j++) {
				pool.put(pieces[i]);
			}
		}
		
		System.out.println(pool);
		System.out.println(pool.getCollection().size());
		
		for (int i = player.getPieces().size(); i < 7 && !pool.empty(); i++) {
			player.receive(pool.take());
		}

		System.out.println(player.getPieces());
		System.out.println(pool);
	}

}
