package de.hsaugsburg.games.boardgames.scrabble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.hsaugsburg.games.boardgames.GridPoint;
import de.hsaugsburg.games.boardgames.ManhattanDirection;
import de.hsaugsburg.games.boardgames.exceptions.IllegalPieceOperationException;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

public class WordManager implements Serializable {
	
	private static final long serialVersionUID = -4967337405541678383L;
	private transient Logger logger;
	private ScrabbleBoard board;
	private ScrabblePlayer currentPlayer;
	private List<String> wordList = new ArrayList<String>();
	private Set<GridPoint> letterList = new TreeSet<GridPoint>();
	private int newPoints;
	
	public WordManager(ScrabbleBoard board) {
		resetLogger();
		logger.setLevel(Level.OFF);
		this.board = board;
	}
	
	public void resetLogger() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	/**
	 * getLetterList() only for testing purposes.
	 * @return list of GridPoints
	 */
	public Set<GridPoint> getLetterList() {
		return letterList;
	}

	public int getPoints() {
		return newPoints;
	}
	
	public ScrabblePlayer getPlayer() {
		return currentPlayer;
	}

	public void setPlayer(ScrabblePlayer player) {
		this.currentPlayer = player;
	}
	
	public List<String> getProducedWords() {
		return wordList;
	}
	
	public void addPiece(LetterPiece piece, GridPoint gp) throws IllegalPieceOperationException, OutsideBoardException {
		if (piece == null) {
			throw new IllegalPieceOperationException("No piece selected!");
		}
		if (!currentPlayer.getPieces().contains(piece)) {
			throw new IllegalPieceOperationException("Letter is not in list!");
		}
		if (!board.isEmpty(gp)) {
			throw new IllegalPieceOperationException("Field is occupied!");
		}
		
		/* 1. Rule:
		 * Check if letters have the same orientation and the starred filed is set first.
		 */

		if (!gp.isManhattanColinearWith(letterList)) {
			throw new IllegalPieceOperationException("Letters have to lie in one direction!");
		}
		
		letterList.add(gp);
		currentPlayer.getPieces().remove(piece);
		board.putPiece(piece, gp);
		
		//Logging messages.
		logger.log(Level.FINEST, "Piece added: " + gp + " " + piece + ", new list size: " + letterList.size());
	}
	
	public void removePiece(GridPoint gp) throws IllegalPieceOperationException, OutsideBoardException {
		if (board.isEmpty(gp)) {
			throw new IllegalPieceOperationException("Field is empty!");
		}
		if (board.getDetails(gp).isFixed()) {
			throw new IllegalPieceOperationException("Piece is fixed!");
		}
		
		currentPlayer.getPieces().add(board.getPiece(gp));
		letterList.remove(gp);
		logger.log(Level.FINEST, "Piece removed: " + gp + " " + board.getPiece(gp) + ", new list size: " + letterList.size());
		board.putPiece(null, gp);
		
	}
	
	public void commitLetterSequence(boolean first) throws IllegalPieceOperationException, OutsideBoardException {
		
		if (first && board.isEmpty(new GridPoint(7, 7))) {
			throw new IllegalPieceOperationException("Starred field has to be set before first commit!");
		}
		if (first && (letterList.size() < 2)) {
			throw new IllegalPieceOperationException("First commit requires two pieces at least!");
		}
		if (letterList.isEmpty()) {
			throw new IllegalPieceOperationException("No new letters have been added!");
		}
		
		List<ManhattanDirection> directions = new ArrayList<ManhattanDirection>();
		List<GridPoint> startPoints = new ArrayList<GridPoint>();
		
		/* 2. Rule:
		 * Check word sequence for gaps/orientation and add letters to the letterList that are between the committed letters.
		 * Not necessary if only a single piece has been dropped.
		 */
		if (letterList.size() > 1) {
			GridPoint[] gpArr = letterList.toArray(new GridPoint[letterList.size()]);  
			directions.add(ManhattanDirection.calcDir(gpArr[0], gpArr[1]));
			startPoints.add((GridPoint)letterList.toArray()[0]);
			Iterator<GridPoint> it = board.iterator(startPoints.get(0), directions.get(0).gp, true);
			int iteratedLetters = 0;
			while (it.hasNext()) {
				GridPoint gp = it.next();
				if (board.getPiece(gp) != null) {
					if (board.getDetails(gp).isFixed()) {
						if (gp.isManhattanColinearWith(letterList) ) {
							letterList.add(gp);
						} else {
							break;
						}
					}
					iteratedLetters++;
				} else {
					logger.log(Level.FINEST, "iteratedLetters/letterList.size(): " + Integer.toString(iteratedLetters) + "/" + letterList.size());
					if (iteratedLetters != letterList.size()) {
						throw new IllegalPieceOperationException("A gap is in your dropped word!");
					}
					break;
				}
			}
		}
		
		/* 3. Rule:
		 * Check if committed letter sequence is connected to existing letter sequences if not first commit.
		 */
		if (!first) { 
			boolean h = true;
			ManhattanDirection rotateDir = ManhattanDirection.UP;
			Iterator<GridPoint> listIt = letterList.iterator();
			GridPoint gp;
			while (listIt.hasNext() && h) {
				gp = listIt.next();
				for (int i = 0; i < 4; i++) {
					if (board.isOnTheBoard(gp.plus(rotateDir.gp)) && 
						board.getPiece(gp.plus(rotateDir.gp)) != null && 
						board.getDetails(gp.plus(rotateDir.gp)).isFixed()) {
						h = false;
						break;
					}
					rotateDir = rotateDir.orthogonalDir();	
				}
			}
			if (h) {
				throw new IllegalPieceOperationException("Word is not connected to existing words!");
			}
		}
		
		/* 4. Rule
		 * Iterate over word and find all its extending connection points that are then used as startPoints.
		 * After that use the startPoints to get all new words.
		 */
		if (!first) {
			//Calculate direction where the startPoint is located.
			GridPoint[] gpArr = letterList.toArray(new GridPoint[letterList.size()]);
			if (letterList.size() < 2) {
				ManhattanDirection rotateDir = ManhattanDirection.UP;
				for (int i = 0; i < 4; i++) {
					if (board.isOnTheBoard(gpArr[0].plus(rotateDir.gp)) && 
						board.getPiece(gpArr[0].plus(rotateDir.gp)) != null && 
						!letterList.contains(gpArr[0].plus(rotateDir.gp))) {
						break;
					}
					rotateDir = rotateDir.orthogonalDir();	
				}
				//Set first (random) direction if only one letter was set.
				directions.add(ManhattanDirection.calcDir(gpArr[0], gpArr[0].plus(rotateDir.gp)));
			} else {
				//Set first direction for two or more letters.
				directions.set(0, ManhattanDirection.calcDir(gpArr[0], gpArr[1]));
			}
			
			//Iterate over dropped letters and add startPoint and correlating direction if letter is extension point.
			GridPoint gp = gpArr[0];
			if (letterList.size() < 2) {startPoints.add(gp);}
			Iterator<GridPoint> boardIt = board.iterator(gp, directions.get(0).gp, true);
			while (boardIt.hasNext()) {
				if (board.getPiece(gp = boardIt.next()) == null) {
					break;
				} else if (!board.getDetails(gp).isFixed()){
					ManhattanDirection rotateDir = directions.get(0).orthogonalDir();
					for (int i = 0; i < 2; i++) {
						if (board.isOnTheBoard(gp.plus(rotateDir.gp)) && 
							board.getPiece(gp.plus(rotateDir.gp)) != null && 
							!letterList.contains(gp.plus(rotateDir.gp))) {
							startPoints.add(gp);
							directions.add(rotateDir);
							break;
						}
						rotateDir = rotateDir.orthogonalDir().orthogonalDir();	
					}
				}
			}
			
			//Check if start points for all words are at the right position if not, move them.
			for (int i = 0; i < startPoints.size(); i++) {
				boardIt = board.iterator(startPoints.get(i), directions.get(i).gp.neg(), true);
				gp = startPoints.get(i);
				while (true) {
					if (board.getPiece(gp = boardIt.next()) == null) {
						break;
					}
					startPoints.set(i, gp);
					if (!boardIt.hasNext()) {
						break;
					}	
				}
				boardIt = board.iterator(startPoints.get(i), directions.get(i).gp, true);
				
				//Iterate over all new words each beginning at one collected startPoint and add all letters to letterList.
				while (boardIt.hasNext()) {
					GridPoint sp = gp;
					if (board.getPiece(gp = boardIt.next()) == null) {
						//If the word is not read in right direction the end is the new startPoint for string conversion.
						if (directions.get(i) == ManhattanDirection.LEFT || directions.get(i) == ManhattanDirection.UP) {
							startPoints.set(i, sp);	
							directions.set(i, directions.get(i).orthogonalDir().orthogonalDir());
						}
						break;
					}
					letterList.add(gp);
				}
			}
		}
		
		
		//Convert to strings
		{	
			logger.log(Level.FINE, "Letters to commit: " + letterList.toString());
			for (int i = 0; i < startPoints.size(); i++) {
				StringBuffer sb = new StringBuffer();
				logger.log(Level.FINEST,"Word" + Integer.toString(i) + " startPoint/direction :" + startPoints.get(i).toString() + "/" + directions.get(i).toString());
				Iterator<GridPoint> boardIt = board.iterator(startPoints.get(i), directions.get(i).gp, true);
				GridPoint gp;
				while (boardIt.hasNext()) {
					if (board.getPiece(gp = boardIt.next()) == null) {
						break;
					}
					sb.append(board.getPiece(gp));
				}
				wordList.add(sb.toString());
			}
		}
	}
	
	public void removePreliminaryPieces() {
		List<LetterPiece> recollected = new ArrayList<LetterPiece>(7);
		Iterator<GridPoint> it  = letterList.iterator();
		while (it.hasNext()) {
			GridPoint gp = it.next();
			if (!board.getDetails(gp).isFixed()) {
				recollected.add(board.removePiece(gp));
			}

		}
		currentPlayer.receiveAll(recollected);
		letterList.clear();
		wordList.clear();
		//Mit Liste "recollected", um sie gegebenenfalls auch returnen zu können.
	}
	
	public int changePreliminaryStatus() {
		int counter = 0;
		Iterator<GridPoint> it  = letterList.iterator();
		while (it.hasNext()) {
			board.getDetails(it.next()).setFixed();
			counter++;
		}
		letterList.clear();
		wordList.clear();
		return counter;
	}
	
	public int calcScore() {
		int points = 0;
		int multiplier = 1;
		Iterator<GridPoint> it  = letterList.iterator();
		while (it.hasNext()) {
			GridPoint gp = it.next();
			//should be ok now
			if (board.getDetails(gp).isFixed()) {
				points += board.getPiece(gp).getPoints();
			} else {
				points += board.getDetails(gp).getLetterMultiplier() * board.getPiece(gp).getPoints();
				multiplier *= board.getDetails(gp).getWordMultiplier();
			}
		}
		
		newPoints = points * multiplier;
		currentPlayer.addPoints(newPoints);
		return newPoints;
	}
	
}
