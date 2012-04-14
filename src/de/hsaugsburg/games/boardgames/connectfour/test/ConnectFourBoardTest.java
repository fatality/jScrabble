package de.hsaugsburg.games.boardgames.connectfour.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import de.hsaugsburg.games.boardgames.connectfour.BinaryPiece;
import de.hsaugsburg.games.boardgames.connectfour.ConnectFourBoard;
import de.hsaugsburg.games.boardgames.connectfour.consoleui.BoardView;

public class ConnectFourBoardTest {
	
	ConnectFourBoard board;
	BoardView view;
	
	@Before
	public void setUp() throws Exception {
		board = new ConnectFourBoard();
		board.reset();
		view = new BoardView(board);
		board.putPiece(BinaryPiece.X, 5, 5);
		board.putPiece(BinaryPiece.X, 5, 2);
		board.putPiece(BinaryPiece.X, 5, 3);
		board.putPiece(BinaryPiece.X, 5, 4);
		board.putPiece(BinaryPiece.X, 5, 0);
		board.putPiece(BinaryPiece.O, 2, 1);
		board.putPiece(BinaryPiece.O, 3, 1);
		board.putPiece(BinaryPiece.O, 4, 1);
		board.putPiece(BinaryPiece.O, 5, 1);
		view.render();
	}

	@Test
	public void testCountEqualPiecesInOneDirection() {
		board.setRow(5);
		board.setColumn(5);
		board.setPiece(BinaryPiece.X);
		assertTrue(board.countEqualPiecesInOneDirection(BinaryPiece.X, 0, -1) == 3);
		board.setRow(2);
		board.setColumn(1);
		board.setPiece(BinaryPiece.O);
		assertTrue(board.countEqualPiecesInOneDirection(BinaryPiece.O, 1, 0) == 3);
		board.setRow(5);
		board.setColumn(0);
		board.setPiece(BinaryPiece.X);
		assertTrue(!(board.countEqualPiecesInOneDirection(BinaryPiece.X, 0, 1) == 1));
	}

	@Test
	public void testCountEqualPiecesInOffDirections() {
		board.setRow(5);
		board.setColumn(3);
		board.setPiece(BinaryPiece.X);
		assertTrue(board.countEqualPiecesInOffDirections(BinaryPiece.X, 0, -1));
		board.setRow(4);
		board.setColumn(1);
		board.setPiece(BinaryPiece.O);
		assertTrue(board.countEqualPiecesInOffDirections(BinaryPiece.O, 1, 0));
	}

	@Test
	public void testNextDropAllowed() {
		board.setRow(5);
		board.setColumn(5);
		board.setPiece(BinaryPiece.X);
		assertTrue(!board.nextDropAllowed(BinaryPiece.X));
		
		board.setRow(2);
		board.setColumn(1);
		board.setPiece(BinaryPiece.O);
		assertTrue(!board.nextDropAllowed(BinaryPiece.O));
	}

}
