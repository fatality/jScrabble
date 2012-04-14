package de.hsaugsburg.games.boardgames.scrabble.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.hsaugsburg.games.boardgames.GridPoint;
import de.hsaugsburg.games.boardgames.ICommandScanner;
import de.hsaugsburg.games.boardgames.IRobot;
import de.hsaugsburg.games.boardgames.ManhattanDirection;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.scrabble.Command;
import de.hsaugsburg.games.boardgames.scrabble.IScrabbleEngine;
import de.hsaugsburg.games.boardgames.scrabble.LetterPiece;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleBoard;

public class GreedyScrabbleTrieBot implements ICommandScanner, IRobot {
	
	private static final long serialVersionUID = -2630144236809665045L;
	private IScrabbleEngine engine;
	private ScrabbleBoard board;
	private List<LetterPiece> myLetters;
	private static transient Trie wordList = new Trie();
	private transient Logger logger;
	private int dropCnt;
	
	
	public GreedyScrabbleTrieBot(IScrabbleEngine engine, ScrabbleBoard board) {
		resetLogger();
		logger.setLevel(Level.ALL);
		this.engine = engine;
		this.board = board;
		loadList();
	}
	
	public void resetLogger() {
		this.logger = Logger.getLogger(this.getClass().getName());
	}
	
	public static void loadList() {
		try {	
			File list = new File ("wordlist.txt");
			Scanner scanner = new Scanner(list).useDelimiter("[^A-Za-z\u00e4\u00f6\u00fc\u00c4\u00d6\u00dc]+");
			while (scanner.hasNext()) {
				wordList.add(scanner.next().toUpperCase());
			}
			scanner.close();
		}
		catch(Exception e){System.err.println(e.getMessage());}
	}
	
	public List<String> getWordList() {
		return wordList.getWords("");
	}
	
	public boolean isTerminal() {
		return false;
	}
	
	public Object next() {
		return next(-1); // Dummy value.
	}

	public Object next(int id) {
		this.myLetters = engine.getList().current().getPieces();
		if (logger == null) {
			resetLogger();
		}
		long t1  = System.nanoTime();
		boolean commited  = false;
		for (int row = 0; row < board.getHeight() && !commited; row++) {
			for (int column = 0; column < board.getWidth() && !commited; column++) {
				if (commited = tryWords(ManhattanDirection.RIGHT, new GridPoint(row, column))) {
					break;
				}
				if (commited = tryWords(ManhattanDirection.DOWN, new GridPoint(row, column))) {
					break;
				}	
			}
		}
		long t2 = System.nanoTime();
		logger.log(Level.INFO, dropCnt + " letters tryed in " + (double) (t2 - t1) / 1000000000.0d + "s");
		if (!commited) {
			return Command.PASS;
		}
		return Command.AGREE;
	}
	
	private boolean tryWords(ManhattanDirection direction, GridPoint gridPoint) {
		engine.getManager().removePreliminaryPieces();
		Iterator<GridPoint> boardWalk = board.iterator(gridPoint, direction.gp, true);
		try {
			//walk the board
			while (boardWalk.hasNext()){
			
				GridPoint currentPos = boardWalk.next();
			
				//clone letters
				List<LetterPiece> letterPool = new ArrayList<LetterPiece>(myLetters);  

			
				//empty square, place letter
				if (board.getPiece(currentPos) == null){
					for (LetterPiece letter : letterPool){
						dropCnt++;
						engine.addPiece(letter, currentPos);
						//prefix not in our wordList?
						try {
						engine.getManager().commitLetterSequence(engine.isFirst());
						} catch (GameException e) {
							
						}
						List<String> newWords = engine.getManager().getProducedWords();
						if (!wordList.containsPrefix(newWords)){
						engine.removePiece(currentPos);
						} else {
							//continue to next position on board, valid prefix
							break;
						}
					}
					//already a letter on this square?
				} else {
				//just continue to next square
				}
			
				//all produced words in our wordlist?
				if (wordList.contains(engine.getManager().getProducedWords(), wordList)) {
					logger.info("trying to commit words: (" + gridPoint.toString() + ") " + engine.getManager().getProducedWords());
					return true;
				} else {
					//	return false;
				}
			}
		
		} catch (Exception e) {
			//something went wrong, unable to find word
			return false;
		}
		return false;
	}

}
