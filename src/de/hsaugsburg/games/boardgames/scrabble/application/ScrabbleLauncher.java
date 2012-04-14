package de.hsaugsburg.games.boardgames.scrabble.application;

import java.lang.reflect.Proxy;
import de.hsaugsburg.games.boardgames.scrabble.EventLogger;
import de.hsaugsburg.games.boardgames.scrabble.IScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.RandomPool;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.consoleui.BoardView;
import de.hsaugsburg.games.boardgames.scrabble.consoleui.CommandProcessor;

public class ScrabbleLauncher {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScrabbleBoard board = new ScrabbleBoard();
		BoardView view = new BoardView(board);
		IScrabbleEngine engine = new ScrabbleEngine(board, new RandomPool<LetterPiece>());
		IScrabbleEngine proxy = (IScrabbleEngine) Proxy.newProxyInstance(IScrabbleEngine.class.getClassLoader(),new Class[] { IScrabbleEngine.class },new EventLogger(engine));
		CommandProcessor processor = new CommandProcessor(proxy, view);
		view.printHelp();
		processor.process();
	}
	
}
