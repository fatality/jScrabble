package de.hsaugsburg.games.boardgames.scrabble;

import java.io.Serializable;
import java.util.List;
import de.hsaugsburg.games.boardgames.GridPoint;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.exceptions.IllegalPieceOperationException;
import de.hsaugsburg.games.boardgames.exceptions.InvalidStateException;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;
import de.hsaugsburg.games.boardgames.exceptions.UnknownCommandException;
import de.hsaugsburg.games.boardgames.scrabble.ScrabbleEngine.State;

public interface IScrabbleEngine extends Serializable {

	public abstract void engageState(State newState)
			throws InvalidStateException;
	
	public abstract void reset();
	
	public abstract ScrabbleBoard getBoard();
	
	public abstract Object next() throws GameException;
	
	public abstract void setMode(String mode) throws UnknownCommandException;
	
	public abstract CircularList<ScrabblePlayer> getList();

	public abstract boolean isFirst() ;

	public abstract boolean isAgreeing();

	public abstract WordManager getManager();

	public abstract void addPlayer(String name);

	public abstract Integer getTerminalId();
	
	public abstract List<Integer> getTerminalIds();
	
	/**
	 * Adds players (Player1... Player#) by default if player <code>list</code> is empty at game start.
	 * @param number integer value defines number of players to be added.
	 */
	public abstract void addDefaultPlayers(int number);

	public abstract void givePieces();
	
	public abstract void fillPool();
	
	public abstract void addPiece(LetterPiece piece, GridPoint gp)
			throws IllegalPieceOperationException, OutsideBoardException;
	
	public abstract void addPiece(LetterPiece piece, int row, int column)
			throws IllegalPieceOperationException, OutsideBoardException;

	public abstract void removePiece(GridPoint gp)
			throws IllegalPieceOperationException, OutsideBoardException;
	
	public abstract void removePiece(int row, int column)
			throws IllegalPieceOperationException, OutsideBoardException;
	
	public abstract void save() throws GameException;
	
	public abstract IScrabbleEngine load() throws GameException;
	
}
