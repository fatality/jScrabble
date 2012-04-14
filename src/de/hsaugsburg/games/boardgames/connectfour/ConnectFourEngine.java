package de.hsaugsburg.games.boardgames.connectfour;

import de.hsaugsburg.games.boardgames.IRobot;
import de.hsaugsburg.games.boardgames.PlayerMode;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.ColumnFullException;
import de.hsaugsburg.games.boardgames.connectfour.exceptions.SwitchNotAllowedException;
import de.hsaugsburg.games.boardgames.connectfour.strategy.RandomRobot;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

/**
 * @author Marc Rochow, Anja Radtke
 */
public class ConnectFourEngine {

	private PlayerMode playerMode;
	private IRobot robot;

	public ConnectFourEngine(ConnectFourBoard board, RandomRobot robot) {
		this.robot = robot;
		this.board = board;
	}
	
	private ConnectFourBoard board;
	
	public void setPlayerMode(PlayerMode playerMode) {
		this.playerMode = playerMode;
	}
	
	public ConnectFourBoard getConnectFourBoard() {
		return board;
	}
	
	public void reset() {
		board.reset();
		board.setPiece(BinaryPiece.X);
	}
	
	public void drop(int column) throws OutsideBoardException, ColumnFullException {	
		board.drop(column);
	}
	
	public int getChoice() {
		return (Integer)robot.next();
	}
	
	public boolean singlePlayer() {
		return (playerMode == PlayerMode.SINGLE && board.piece == BinaryPiece.O);
	}
	
	public boolean nextDropAllowed(int column) throws OutsideBoardException, ColumnFullException{
		int row = board.getRowForColumn(column);
		return board.nextDropAllowed(board.getPiece(row, column));
	}

	public void switchPiece() throws SwitchNotAllowedException {
		board.switchPiece();
	}

	public BinaryPiece getPiece(int column) {
		return board.getPiece(board.getRow(), column);
	}
	
}
