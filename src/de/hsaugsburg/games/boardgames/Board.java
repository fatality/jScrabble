package de.hsaugsburg.games.boardgames;

import java.io.Serializable;
import java.util.Iterator;
import de.hsaugsburg.games.boardgames.exceptions.OutsideBoardException;

/**
 * @author Marc Rochow, Anja Radtke
 */
public class Board <P extends IPiece, D> implements Serializable {
	
	private static final long serialVersionUID = 8812638036800160501L;
	private Square<P, D>[][] squares;
	
	@SuppressWarnings("unchecked")
	public Board(int rows, int columns) {
		squares = new Square[rows][columns];
		reset();
	}
	
	public void reset() {
		for (int row = 0; row < getHeight(); row++) {
			for (int column = 0; column < getWidth(); column++) {
				squares[row][column] = new Square<P, D>();
			}
		}
	}
	
	public void putPiece(P piece, GridPoint gp) {
		putPiece(piece, gp.getRow(), gp.getColumn());
	}
	
	public void putPiece(P piece, int row, int column) {
		squares[row][column].putPiece(piece);
	}
	
	public P getPiece(GridPoint gp) {
		return getPiece(gp.getRow(), gp.getColumn());
	}
	
	public P getPiece(int row, int column) {
		return squares[row][column].getPiece();
	}
	
	public void putDetails(D details, GridPoint gp) {
		putDetails(details, gp.getRow(), gp.getColumn());
	}
	
	public void putDetails(D details, int row, int column) {
		squares[row][column].putDetails(details);
	}
	
	public D getDetails(GridPoint gp) {
		return getDetails(gp.getRow(), gp.getColumn());
	}
	
	public D getDetails(int row, int column) {
		return squares[row][column].getDetails();
	}
	
	public void movePiece(GridPoint fromGP, GridPoint toGP) {
		movePiece(fromGP.getRow(), fromGP.getColumn(), toGP.getRow(), toGP.getColumn());
	}
	
	public void movePiece(int fromRow, int fromColumn, int toRow, int toColumn) {
		squares[toRow][toColumn].putPiece(squares[fromRow][fromColumn].removePiece());
	}
	
	public P removePiece(GridPoint gp) {
		return removePiece(gp.getRow(), gp.getColumn());
	}
	
	public P removePiece(int row, int column) {
		return squares[row][column].removePiece();
	}
	
	public boolean isOnTheBoard(GridPoint gp) {
		return isOnTheBoard(gp.getRow(), gp.getColumn());
	}
	
	public boolean isOnTheBoard(int row, int column) {
		return (row >= 0 && row < getHeight() && column >=0 && column < getWidth());
	}
	
	public boolean isEmpty(GridPoint gp) throws OutsideBoardException {
		return isEmpty(gp.getRow(), gp.getColumn());
	}
	
	public boolean isEmpty(int row, int column) throws OutsideBoardException {
		if (!isOnTheBoard(row, column)) {
			if (!isOnTheBoard(0, column) && !isOnTheBoard(row, 0)) {
				throw new OutsideBoardException("Specified row and column are both not in range: " + (char)(row + 'A') + " " + Integer.toString(column+1));
			} else if (!isOnTheBoard(0, column)) {
				throw new OutsideBoardException("Specified column is not in range: " + Integer.toString(column+1));
			} else {
				throw new OutsideBoardException("Specified row is not in range: " + (char)(row + 'A'));
			}
		}
		return (squares[row][column].getPiece() == null);
	}
	
	public int getHeight() {
		return squares.length;
	}
	
	public int getWidth() {
		return squares[0].length;
	}
	
	public Iterator<GridPoint> iterator(final GridPoint startPoint,final GridPoint direction, final boolean startPointInclusive) {
	
		return new Iterator<GridPoint>() {
			
			private GridPoint currentPoint = startPoint;
			private GridPoint currentDir = direction;
			private boolean start = startPointInclusive;
			
			public boolean hasNext() {
				if (start) {
					return isOnTheBoard(currentPoint.getRow(), currentPoint.getColumn());
				}
				return isOnTheBoard(currentPoint.plus(currentDir).getRow(), currentPoint.plus(currentDir).getColumn());
			}

			public GridPoint next() {
				if (start) {
					start = false;
					return currentPoint;
				}
				return currentPoint = currentPoint.plus(currentDir);
			}

			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {	
		boolean h = true;
		if (obj instanceof Board) {	
			for (int row = 0; row < getHeight() && h; row++) {
				for (int column = 0; column < getWidth() && h; column++) {
					h = this.squares[row][column].equals(((Board<?, ?>)obj).squares[row][column]);
				}
			}
		}
		return h;
	}
	
}
