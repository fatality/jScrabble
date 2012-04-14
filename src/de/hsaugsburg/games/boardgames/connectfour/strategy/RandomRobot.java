package de.hsaugsburg.games.boardgames.connectfour.strategy;

import java.util.*;
import de.hsaugsburg.games.boardgames.IRobot;
import de.hsaugsburg.games.boardgames.connectfour.BinaryPiece;
import de.hsaugsburg.games.boardgames.connectfour.ConnectFourBoard;
import de.hsaugsburg.games.boardgames.connectfour.ConnectFourEngine;
import de.hsaugsburg.games.boardgames.exceptions.GameException;

public class RandomRobot implements IRobot {
	
	private Random random = new Random();
	private ConnectFourBoard board;
	private ConnectFourEngine engine;
	
	public RandomRobot(ConnectFourBoard board) {
		this.board = board;
	}
	
	public void setEngine(ConnectFourEngine engine) {
		this.engine = engine;
	}

	public Object next() {
		try {
			int column;
			if ((column = couldWinAtColumn(BinaryPiece.O, 0)) > -1) return column;
			if ((column = couldWinAtColumn(BinaryPiece.X, 0)) > -1) return column;
	
			if( board.countEqualPiecesInOneDirection(BinaryPiece.X, 1, 0) == 2 &&
				board.isOnTheBoard(board.getRow()-1, board.getColumn())) {
				return board.getColumn();
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 0, 1) > 0 &&
				board.isOnTheBoard(0, board.getColumn()-1)) {
				return board.getColumn()-1;
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 0, -1) > 0 &&
				board.isOnTheBoard(0, board.getColumn()+1)) {
				return board.getColumn()+1;
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 1, -1) > 1 &&
				!board.isEmpty(board.getRow(), board.getColumn()+1) &&
				board.isOnTheBoard(0, board.getColumn()+1)) {
				return board.getColumn()+1;
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 1, 1) > 1 &&
				!board.isEmpty(board.getRow(), board.getColumn()-1) &&
				board.isOnTheBoard(0, board.getColumn()-1)) {
				return board.getColumn()-1;
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 1, -1) > 1 &&
				board.isEmpty(board.getRow(), board.getColumn()+1)) {
				while((column = Math.abs(random.nextInt()%board.getWidth())) == board.getColumn()+1);
				return column;
			}
			
			if (board.countEqualPiecesInOneDirection(BinaryPiece.X, 1, 1) > 1 &&
				board.isEmpty(board.getRow(), board.getColumn()+1)) {
				while((column = Math.abs(random.nextInt()%board.getWidth())) == board.getColumn()-1);
				return column;
			}
		} catch (Exception gex) {
			return Math.abs(random.nextInt()%board.getWidth());
		}
		return Math.abs(random.nextInt()%board.getWidth());
	}
	
	public int couldWinAtColumn(BinaryPiece piece, int column) throws GameException {
		boolean win = false;
		int oldColumn = board.getColumn();
		int oldRow = board.getRow();
		BinaryPiece oldPiece = board.getPiece();
		board.setPiece(piece);
		for (column = 0; column < board.getWidth(); column++) {
			board.drop(column);
			if (!engine.nextDropAllowed(column)) {
				win = true;
			}
			board.putPiece(null, board.getRow(), board.getColumn());
			board.setRow(oldRow);
			board.setColumn(oldColumn);
			board.setPiece(oldPiece);
			if (win) return column;
		}
		return -1;
	}
	
}
