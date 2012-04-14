package de.hsaugsburg.games.boardgames.scrabble;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import de.hsaugsburg.games.boardgames.GridPoint;
import de.hsaugsburg.games.boardgames.PlayerMode;
import de.hsaugsburg.games.boardgames.exceptions.GameException;
import de.hsaugsburg.games.boardgames.exceptions.IllegalPieceOperationException;
import de.hsaugsburg.games.boardgames.exceptions.InvalidStateException;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;
import de.hsaugsburg.games.boardgames.exceptions.UnknownCommandException;
import de.hsaugsburg.games.boardgames.scrabble.strategy.GreedyScrabbleBot;
import de.hsaugsburg.games.boardgames.scrabble.strategy.GreedyScrabbleTrieBot;

public class ScrabbleEngine implements IScrabbleEngine {
	
	private static final long serialVersionUID = 8578243619510263660L;
	private RandomPool<LetterPiece> pool;
	private WordManager manager;
	private CircularList<ScrabblePlayer> players;
	private ScrabbleBoard board;
	private State currentState;
	private PlayerMode mode;
	
	public ScrabbleEngine(ScrabbleBoard board, RandomPool<LetterPiece> pool) {
		players = new CircularList<ScrabblePlayer>();
		this.pool = pool;
		manager = new WordManager(board);
		this.board = board;
		this.mode = PlayerMode.SINGLE;
	}
	
	public ScrabbleBoard getBoard() {
		return board;
	}
	
	public Object next() throws GameException {
		if (players.getAll().isEmpty()) {
			return new CommandScanner(Command.values()).next();
		}
		return players.current().next();
	}
	
	public void engageState(State newState) throws InvalidStateException {	
		if (!board.isInitialized() && newState != State.INITIAL && newState != State.PLAYER) {
			throw new InvalidStateException("Start a new game first.");
		}
		if (isAgreeing() && (newState != State.AGREEING )) {
			throw new InvalidStateException("Please agree or reject...");
		}
		if (!isAgreeing() && (newState == State.AGREEING )) {
			throw new InvalidStateException("Can't agree before commit...");
		}
		this.currentState = newState;
	}
	
	public void reset() {
		manager.getLetterList().clear();
		pool.getCollection().clear();
		for (ScrabblePlayer player : players.getAll()) {
			player.getPieces().clear();
		}
		board.reset();
	}
	
	public CircularList<ScrabblePlayer> getList() {
		return players;
	}

	public boolean isFirst() {
		return !board.getDetails(new GridPoint(7, 7)).isFixed();
	}

	public boolean isAgreeing() {
		return !manager.getProducedWords().isEmpty();
	}
	
	public WordManager getManager() {
		return manager;
	}
	
	public Integer getTerminalId() {
		if (players.current().isTerminal()) {
			return players.current().getId(); 
		}
		return null;
	}
	
	public List<Integer> getTerminalIds() {
		List<Integer> ids = new Vector<Integer>(4);
		for (ScrabblePlayer player : players.getAll()) {
			if (player.isTerminal()) {
				ids.add(player.getId());
			}
		}
		return ids;
	}
	
	public void addPlayer(String name) {	
		players.add(new ScrabblePlayer(name, new CommandScanner(Command.values())));
	}
	
	public void setMode(String mode) throws UnknownCommandException {
		if ("SP".equals(mode)) {
			this.mode = PlayerMode.SINGLE;
		} else if ("MP".equals(mode)) {
			this.mode = PlayerMode.MULTI;
		} else if ("CP".equals(mode)) {
			this.mode = PlayerMode.COMP;
		} else {
			if (mode != null) {throw new UnknownCommandException("Invalid param: " + mode);}
		}
	}
	
	/**
	 * Adds players (Player1... Player#) by default if player <code>list</code> is empty at game start.
	 * @param number integer value defines number of players to be added.
	 */
	public void addDefaultPlayers(int number) {
		if (currentState == State.INITIAL && players.getAll().isEmpty()) {
			for (int i = 1; i <= number; i++) {
				if (i == number && mode == PlayerMode.SINGLE) {
					players.add(new ScrabblePlayer("GreedyScrabbleBot", new GreedyScrabbleBot(this, board)));
				} else if (mode == PlayerMode.COMP){
					if (i == 1) {
						players.add(new ScrabblePlayer("GreedyScrabbleBot" + i, new GreedyScrabbleBot(this, board)));
					} else {
						players.add(new ScrabblePlayer("GreedyScrabbleTrieBot" + i, new GreedyScrabbleTrieBot(this, board)));
					}
				} else {
					players.add(new ScrabblePlayer("Player" + i, new CommandScanner(Command.values())));
				}
			}
		} 
	}
	
	public void fillPool() {
		LetterPiece[] pieces = LetterPiece.values();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].getCount(); j++) {
				pool.put(pieces[i]);
			}
		}
	}

	public void givePieces() {
		for (int i = manager.getPlayer().getPieces().size(); i < 7 && !pool.empty(); i++) {
			manager.getPlayer().receive(pool.take());
		}	
	}
	
	public void addPiece(LetterPiece piece, GridPoint gp) throws IllegalPieceOperationException, OutsideBoardException {
		addPiece(piece, gp.getRow(), gp.getColumn());
	}

	public void addPiece(LetterPiece piece, int row, int column) throws IllegalPieceOperationException, OutsideBoardException {
		manager.addPiece(piece, new GridPoint(row, column));
	}
	
	public void removePiece(GridPoint gp) throws IllegalPieceOperationException, OutsideBoardException {
		removePiece(gp.getRow(), gp.getColumn());
	}
	
	public void removePiece(int row, int column) throws IllegalPieceOperationException, OutsideBoardException {
		manager.removePiece(new GridPoint(row, column));
	}
	
	public void save() throws GameException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(new File("scrabble.sav")));
			out.writeObject(this);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new GameException("Error while saving: " + e.getCause());
		}
	}
	
	public IScrabbleEngine load() throws GameException {
		IScrabbleEngine engine;
		try {
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(new File("scrabble.sav")));
			engine = (IScrabbleEngine) in.readObject();
			in.close();
			return engine;
		} catch (ClassNotFoundException e) {
			throw new GameException("Error while loading: " + e.getCause());
		} catch (FileNotFoundException e) {
			throw new GameException("Error while loading: " + e.getCause());
		} catch (IOException e) {
			throw new GameException("Error while loading: " + e.getCause());
		}
	}
	
	public enum State implements Serializable {
		INITIAL, PLAYER, DROPPING, REMOVING, COMMITED, REJECTING, AGREEING, PASSING;
	}
	
}
