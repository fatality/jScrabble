package de.hsaugsburg.games.boardgames.scrabble.strategy;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
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
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine.State;

public class GreedyScrabbleBot implements ICommandScanner, IRobot {
	
	private static final long serialVersionUID = -2630144236809665044L;
	private IScrabbleEngine engine;
	private ScrabbleBoard board;
	private static transient List<String> wordList = new LinkedList<String>();
	private transient Logger logger;
	private int dropCnt;
	
	public GreedyScrabbleBot(IScrabbleEngine engine, ScrabbleBoard board) {
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
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public List<String> getWordList() {
		return wordList;
	}
	
	public boolean isTerminal() {
		return false;
	}
	
	public Object next() {
		return next(-1); // Dummy value.
	}

	public Object next(int id) {
		if (logger == null) {resetLogger();}
		long t1  = System.nanoTime();
		boolean commited  = false;
		for (int row = 0; row < board.getHeight() && !commited; row++) {
			for (int column = 0; column < board.getWidth() && !commited; column++) {
				if (commited = tryWords(ManhattanDirection.RIGHT, row, column)) {
					break;
				}
				if (commited = tryWords(ManhattanDirection.DOWN, row, column)) {
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
	
	private boolean tryWords(ManhattanDirection direction, int row, int column) {
		Iterator<GridPoint> lineCheck = board.iterator(new GridPoint(row, column), direction.gp, true);
		ManhattanDirection rotateDir = direction;
		boolean h = false;
		int minlen = 0;
		while (!engine.isFirst()) {
			if (!lineCheck.hasNext()) {return false;}
			GridPoint gp = lineCheck.next();
			for (int i = 0; i < 4; i++) {
				if (board.isOnTheBoard(gp.plus(rotateDir.gp)) && 
					board.getPiece(gp.plus(rotateDir.gp)) != null && 
					board.getDetails(gp.plus(rotateDir.gp)).isFixed()) {
					h = true;
					break;
				}
				rotateDir = rotateDir.orthogonalDir();	
			}
			if (h) {
				break;
			}
			minlen++;
		}
		
		for (String str : wordList) {
			if (str.length() < minlen) {
				continue;
			}
			Iterator<GridPoint> it = board.iterator(new GridPoint(row, column), direction.gp, true);
			for (int i = 0; i < str.length() && it.hasNext(); i++) {
				GridPoint gp = it.next();
				try {
					if (board.isEmpty(gp)){
						dropCnt++;
						engine.engageState(State.DROPPING);
						engine.addPiece(LetterPiece.valueOf(Character.toString(str.charAt(i))), gp);
					} else if(!LetterPiece.valueOf(Character.toString(str.charAt(i))).equals(board.getPiece(gp))) {	
						if (i < str.length() - 1) {
							break;
						}
					}
					if (i == str.length() - 1) {
						engine.engageState(State.COMMITED);
						engine.getManager().commitLetterSequence(engine.isFirst());
						if (engine.getManager().getProducedWords().contains(str.toUpperCase())
								&& wordList.containsAll(engine.getManager().getProducedWords())) {
							return true;
						} else {
							engine.engageState(State.REJECTING);
							engine.getManager().removePreliminaryPieces();
							break;
						}
					}
				} catch (GameException e) {
					engine.getManager().removePreliminaryPieces();
					break;
				}
			}	
		}
		engine.getManager().removePreliminaryPieces();
		return false;
	}
	
}
