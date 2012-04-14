package de.hsaugsburg.games.boardgames.connectfour.consoleui;

import de.hsaugsburg.games.boardgames.*;
import de.hsaugsburg.games.boardgames.connectfour.BinaryPiece;

/**
 * @author Marc Rochow, Anja Radtke
 */
public class BoardView {
	
	/**
	 * @param board			Board is set in the application launcher by BoardView's setBoard() method.
	 * @param buffer		A kind of cache for the entire board before it gets printed out to the console.
	 */
	private Board<BinaryPiece, Object> board;
	private StringBuffer buffer;
	
	public BoardView(Board<BinaryPiece, Object> board) {
		buffer = new StringBuffer();
		this.board = board;
	}
	
	public void render(BinaryPiece piece) {
		System.out.println("Player " + piece.name() + " won!");
	}
	
	public void render() {
		clearBuffer();
		printNumbers();
		printBoard();
		printNumbers();
		System.out.print(buffer);
	}
	
	/**
	 * @method printBoard()						Converts the board to chars and appends them one by one to the "buffer".
	 * @throws BoardNotInitializedException
	 */
	private void printBoard() {
		for (int row = 0; row < board.getHeight(); row++) {
			for (int column = 0; column < board.getWidth(); column++) {
				;
				if (board.getPiece(row, column) != null) {
					buffer.append(board.getPiece(row, column));
				} else buffer.append(".");
			}
			buffer.append("\n");
		}
	}
	
	private void printNumbers() {
		for (int column = 1; column <= board.getWidth(); column++) {
			buffer.append(column);
		}
		buffer.append("\n");
	}
	
	private void clearBuffer() {
		buffer = new StringBuffer();
	}
	
}